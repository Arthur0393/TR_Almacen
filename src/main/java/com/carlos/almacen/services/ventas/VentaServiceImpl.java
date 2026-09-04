package com.carlos.almacen.services.ventas;

import com.carlos.almacen.dto.ventas.DetalleVentaRequest;
import com.carlos.almacen.dto.ventas.VentaRequest;
import com.carlos.almacen.dto.ventas.VentaResponse;
import com.carlos.almacen.entities.DetalleVentas;
import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.entities.Sucursal;
import com.carlos.almacen.entities.Venta;
import com.carlos.almacen.enums.EstadoVenta;
import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.mappers.VentaMapper;
import com.carlos.almacen.repositories.ProductoRepository;
import com.carlos.almacen.repositories.SucursalRepository;
import com.carlos.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional(readOnly = true) // solo lectura: evita overhead de dirty checking innecesario
    public List<VentaResponse> listar() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse ObtenerPorIdActiva(Long id) {
        Venta venta = ventaRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una venta activa con el id " + id));
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    @Transactional // atómico: si algo falla, se revierte TODO (venta + stock)
    public VentaResponse registrar(VentaRequest ventaRequest) {

        // 1. Validar que la sucursal exista
        Sucursal sucursal = sucursalRepository.findById(ventaRequest.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una sucursal con el id " + ventaRequest.idSucursal()));

        // 2. Crear el encabezado de la venta (aún sin detalles)
        Venta venta = Venta.builder()
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .sucursal(sucursal)
                .build();

        // 3. Procesar cada línea del pedido
        for (DetalleVentaRequest detalleRequest : ventaRequest.productos()) {

            // 3.1 Buscar el producto real (fuente de verdad del precio y stock)
            Producto producto = productoRepository.findById(detalleRequest.idProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No existe un producto con el id " + detalleRequest.idProducto()));

            int cantidadSolicitada = Math.toIntExact(detalleRequest.cantidadProducto());

            // 3.2 Validación de existencias con mensaje descriptivo
            //     (la hacemos aquí ANTES de descontar para dar un mensaje claro con nombre de producto;
            //      Producto.descontarCantidad también valida, como segunda barrera de seguridad)
            if (cantidadSolicitada > producto.getCantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto '" + producto.getNombre() +
                                "' (id " + producto.getId() + "). Disponible: " + producto.getCantidad() +
                                ", solicitado: " + cantidadSolicitada);
            }

            // 3.3 Control de inventario: descuenta directamente sobre la entidad gestionada por JPA
            producto.descontarCantidad(cantidadSolicitada);

            // 3.4 Fotografía del precio: se toma producto.getPrecio() AHORA, no lo que mandó el cliente
            DetalleVentas detalle = DetalleVentas.builder()
                    .producto(producto)
                    .cantidadProducto(cantidadSolicitada)
                    .precioProducto(producto.getPrecio())
                    .build();

            // 3.5 Enlaza detalle <-> venta (bidireccional, usando el método de la entidad)
            venta.agregarDetalle(detalle);
        }

        // 4. Guardar. Gracias al cascade = ALL en Venta.detalleVentas, esto guarda también los detalles.
        //    El cambio en Producto.cantidad se persiste solo por "dirty checking" al hacer commit
        //    de la transacción (Producto sigue siendo una entidad gestionada por el EntityManager).
        Venta ventaGuardada = ventaRepository.save(venta);

        // 5. El total se calcula en el mapper sumando subtotales, no se guarda como columna
        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    @Override
    @Transactional
    public VentaResponse cancelar(Long id) {

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una venta con el id " + id));

        // Lanza IllegalStateException si ya estaba cancelada (regla de negocio en la entidad)
        venta.cancelar();

        // Revertir stock de cada producto vendido
        venta.getDetalleVentas().forEach(detalle ->
                detalle.getProducto().aumentarCantidad(detalle.getCantidadProducto()));

        // No hace falta ventaRepository.save(venta) explícito: al estar dentro de la transacción,
        // JPA detecta los cambios (estado de la venta y cantidad de cada producto) y los persiste al commit.
        return ventaMapper.entidadAResponse(venta);
    }
}
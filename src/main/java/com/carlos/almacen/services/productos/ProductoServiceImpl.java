package com.carlos.almacen.services.productos;

import com.carlos.almacen.dto.productos.ProductoRequest;
import com.carlos.almacen.dto.productos.ProductoResponse;
import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.enums.Categoria;
import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.mappers.ProductoMapper;
import com.carlos.almacen.repositories.ProductoRepository;
import com.carlos.almacen.specifications.ProductoSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoServices {

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre,
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax) {

        log.info(
                "Listando productos con filtros -> nombre: {}, categoria: {}, precioMin: {}, precioMax: {}",
                nombre,
                categoria,
                precioMin,
                precioMax
        );

        if (precioMin != null
                && precioMax != null
                && precioMin.compareTo(precioMax) > 0) {

            throw new IllegalArgumentException(
                    "El precio mínimo no puede ser mayor al precio máximo"
            );
        }

        return productoRepository
                .findAll(filtroAvanzado(nombre, categoria, precioMin, precioMax))
                .stream()
                .map(productoMapper::entidadResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse buscarPorId(Long id) {

        log.info("Buscando producto con id: {}", id);

        Producto producto = obtenerProductooException(id);

        return productoMapper.entidadResponse(producto);
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAEntidad(
                request,
                Categoria.obtenerCategoriaPorDescripcion(request.categoria()));

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadResponse(producto);

    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductooException(id);

        log.info("Actualizando  producto con id {} ", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(
                        request.categoria()),
                request.precio(),
                request.cantidad());

        //productoRepository.save(producto); NO NECESARIO POR DIRTY CHECKING

        log.info("Producto con id {} actualizado", id);

        return productoMapper.entidadResponse(producto);

    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductooException(id);

        log.info("Eliminando producto con id {} ", id);

        productoRepository.delete(producto);

        log.info("Producto con id {} eliminado", id);

    }

    private Producto obtenerProductooException(Long id) {

        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));

    }

    private Specification<Producto> filtroAvanzado(
            String nombre,
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax) {

        return Specification.where(ProductoSpecifications.conNombre(nombre))
                .and(ProductoSpecifications.conPrecioMin(precioMin))
                .and(ProductoSpecifications.conCategoria(categoria))
                .and(ProductoSpecifications.conPrecioMax(precioMax));
    }

}
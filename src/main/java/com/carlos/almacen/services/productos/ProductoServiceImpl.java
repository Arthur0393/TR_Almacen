package com.carlos.almacen.services.productos;

import com.carlos.almacen.dto.productos.ProductoRequest;
import com.carlos.almacen.dto.productos.ProductoResponse;
import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.enums.Categoria;
import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.mappers.ProductoMapper;
import com.carlos.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoServices{

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {

        log.info("Listando todos los productos");

        return productoRepository.findAll().stream()
                .map(productoMapper::entidadResponse).toList();
    }

    @Override
    public ProductoResponse buscarPorId(Long id) {
        return null;
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

        //productoRepository.save(producto); NO NECESARIO POR DIRY CHECKING

        log.info("Producto con id {} ", id);

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
}
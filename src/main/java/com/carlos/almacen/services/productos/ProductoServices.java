package com.carlos.almacen.services.productos;

import com.carlos.almacen.dto.productos.ProductoRequest;
import com.carlos.almacen.dto.productos.ProductoResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoServices {

    List<ProductoResponse> listar(
            String nombre,
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax);

    ProductoResponse buscarPorId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}
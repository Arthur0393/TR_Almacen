package com.carlos.almacen.mappers;

import com.carlos.almacen.dto.productos.ProductoRequest;
import com.carlos.almacen.dto.productos.ProductoResponse;
import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {

        if (request == null) return null;

        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();
    }

    public ProductoResponse entidadResponse(Producto producto) {

        if (producto == null) return null;

        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria().getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad());
    }

}
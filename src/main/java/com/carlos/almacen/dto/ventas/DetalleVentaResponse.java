package com.carlos.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentaResponse(

        @Schema(description =  "ID del producto", example = "1")
        Long idProducto,

        @Schema(description = "Nombre del producto", example = "Laptop Gamer")
        String nombreProducto,

        @Schema(description = "Cantidad de unidades vendidas", example = "18")
        Integer cantidadProducto,

        @Schema(description = "Precio unitario del producto", example = "1500.00")
        BigDecimal precioProducto,   // <-- cambiado de Integer a BigDecimal

        @Schema(description = "Subtotal del producto", example = "1500.00")
        BigDecimal subtotal
) {}
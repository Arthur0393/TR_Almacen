package com.carlos.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Informacion de un producto")
public record ProductoResponse(
        @Schema(
                description = "Identificador del Producto",
                example = "1"
        )
        Long id,
        @Schema(
                description = "Nombre del Producto",
                example = "Laptop Gamer"
        )
        String nombre,
        @Schema(
                description = "Categoria del Producto",
                example = "Electronica"
        )
        String categoria,
        @Schema(
                description = "Precio del producto",
                example = "15999.99"
        )
        BigDecimal precio,
        @Schema(
                description = "Cantidad disponible del producto",
                example = "300"
        )
        Integer cantidad
) {
}

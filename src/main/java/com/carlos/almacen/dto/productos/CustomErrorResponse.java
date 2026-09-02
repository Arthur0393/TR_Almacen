package com.carlos.almacen.dto.productos;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}

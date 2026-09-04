package com.carlos.almacen.dto.reportes;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reporte agregado de rendimiento economico por sucursal")
public record ReporteVentasSucursalResponse(

        @Schema(description = "ID de la sucursal", example = "1")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal Central")
        String nombreSucursal,

        @Schema(description = "Total facturado en ventas activas", example = "15250.00")
        BigDecimal totalFacturado,

        @Schema(description = "Cantidad total de productos vendidos", example = "340")
        Long cantidadProductosVendidos
) {}
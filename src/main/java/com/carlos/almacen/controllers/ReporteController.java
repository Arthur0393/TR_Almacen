package com.carlos.almacen.controllers;

import com.carlos.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.carlos.almacen.services.reportes.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
@Tag(name = "Reportes", description = "Endpoints de reportes agregados del negocio")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-por-sucursal")
    @Operation(
            summary = "Reporte de rendimiento economico por sucursal",
            description = "Devuelve, por cada sucursal, el total facturado y la cantidad de " +
                    "productos vendidos, considerando unicamente ventas activas (no canceladas)",
            tags = {"Reportes"}
    )
    public ResponseEntity<List<ReporteVentasSucursalResponse>> ventasPorSucursal() {
        return ResponseEntity.ok(reporteService.generarReportePorSucursal());
    }
}
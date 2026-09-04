package com.carlos.almacen.services.reportes;

import com.carlos.almacen.dto.reportes.ReporteVentasSucursalResponse;

import java.util.List;

public interface ReporteService {
    List<ReporteVentasSucursalResponse> generarReportePorSucursal();
}
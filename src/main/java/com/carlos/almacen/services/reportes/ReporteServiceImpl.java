package com.carlos.almacen.services.reportes;

import com.carlos.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.carlos.almacen.enums.EstadoVenta;
import com.carlos.almacen.repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> generarReportePorSucursal() {
        // Toda la agregación (SUM, GROUP BY) ocurre en la base de datos.
        // Aquí solo pedimos el resultado ya calculado, filtrando ventas activas.
        return ventaRepository.generarReportePorSucursal(EstadoVenta.REGISTRADA);
    }
}
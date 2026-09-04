package com.carlos.almacen.mappers;

import com.carlos.almacen.dto.ventas.DetalleVentaResponse;
import com.carlos.almacen.dto.ventas.VentaResponse;
import com.carlos.almacen.entities.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VentaMapper {

    private final SucursalMapper sucursalMapper;
    private final DetalleVentaMapper detalleVentaMapper;

    public VentaResponse entidadAResponse(Venta venta) {
        if (venta == null) return null;

        List<DetalleVentaResponse> detalles = venta.getDetalleVentas().stream()
                .map(detalleVentaMapper::entidadAResponse)
                .toList();

        BigDecimal total = detalles.stream()
                .map(DetalleVentaResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                venta.getEstadoVenta().getDescripcion(),
                sucursalMapper.entidadResponse(venta.getSucursal()),
                detalles,
                total
        );
    }
}
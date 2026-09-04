package com.carlos.almacen.mappers;

import com.carlos.almacen.dto.ventas.DetalleVentaResponse;
import com.carlos.almacen.entities.DetalleVentas;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {

    // Solo convertimos de entidad -> response.
    // La conversión Request -> entidad se hace en el servicio,
    // porque requiere ir a buscar el Producto real (no confiamos en lo que envía el cliente).
    public DetalleVentaResponse entidadAResponse(DetalleVentas detalle) {
        if (detalle == null) return null;

        // Subtotal = cantidad * precio "fotografiado" en el detalle (no el precio actual del producto)
        BigDecimal subtotal = detalle.getPrecioProducto()
                .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()));

        return new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                subtotal
        );
    }
}
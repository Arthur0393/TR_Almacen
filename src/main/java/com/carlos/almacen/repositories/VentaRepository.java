package com.carlos.almacen.repositories;

import com.carlos.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.carlos.almacen.entities.Venta;
import com.carlos.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    /*
     * Requerimiento 3: reporte agregado por sucursal.
     *
     * Usamos una JPQL con "constructor expression" (new ...DTO(...)) para que
     * la base de datos haga el SUM y el GROUP BY, y Hibernate arme directamente
     * los DTOs de respuesta. Esto es mucho más eficiente que traer todas las
     * ventas/detalles a Java y sumarlas con Streams, porque:
     *   - Solo viaja por red el resultado ya agregado (pocas filas: 1 por sucursal).
     *   - No se cargan en memoria entidades completas (Venta, DetalleVentas, Producto).
     *
     * Solo se consideran ventas con estado = REGISTRADA (activas)
     */
    @Query("""
            SELECT new com.carlos.almacen.dto.reportes.ReporteVentasSucursalResponse(
                s.id,
                s.nombre,
                SUM(d.cantidadProducto * d.precioProducto),
                SUM(d.cantidadProducto)
            )
            FROM Venta v
            JOIN v.sucursal s
            JOIN v.detalleVentas d
            WHERE v.estadoVenta = :estado
            GROUP BY s.id, s.nombre
            """)
    List<ReporteVentasSucursalResponse> generarReportePorSucursal(@Param("estado") EstadoVenta estado);
}
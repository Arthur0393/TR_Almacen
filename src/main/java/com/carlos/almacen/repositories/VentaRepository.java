package com.carlos.almacen.repositories;

import com.carlos.almacen.entities.Venta;
import com.carlos.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Spring Data genera la query automáticamente a partir del nombre del método
    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);
}
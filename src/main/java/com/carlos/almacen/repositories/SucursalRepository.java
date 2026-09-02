package com.carlos.almacen.repositories;

import com.carlos.almacen.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    //GET
    Boolean existsByNombreIgnoreCase(String nombre);

    //INSERT
    Boolean existsByNombreIgnoreCaseAndIdNot(String nombre,Long id);
}

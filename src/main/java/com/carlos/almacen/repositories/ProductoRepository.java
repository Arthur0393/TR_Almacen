package com.carlos.almacen.repositories;

import com.carlos.almacen.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
/**
 * JpaRepository no  filtra, da metodos CRUD basicos como
 *  findAll()
 *  findById()
 *  save()
 *  delete()
 * Se añade JpaSpecificationExecutor, ya que nos permite ejecutar busquedas dinamicas mediante
 *  Specification
 */
public interface ProductoRepository extends JpaRepository<Producto,Long>,
        JpaSpecificationExecutor<Producto>  {

}

package com.carlos.almacen.services.sucursales;

import com.carlos.almacen.dto.sucursales.SucursalRequest;
import com.carlos.almacen.dto.sucursales.SucursalResponse;

import java.util.List;

public interface SucursalServices {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar (Long id);
}

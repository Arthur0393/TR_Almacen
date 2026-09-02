package com.carlos.almacen.mappers;

import com.carlos.almacen.dto.sucursales.SucursalRequest;
import com.carlos.almacen.dto.sucursales.SucursalResponse;
import com.carlos.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal requestAEntidad(SucursalRequest request) {

        if (request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .build();
    }

    public SucursalResponse entidadResponse(Sucursal sucursal){

        if(sucursal == null) return null;

        return new SucursalResponse(
                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion());
    }
}

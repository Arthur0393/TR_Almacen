package com.carlos.almacen.services.sucursales;

import com.carlos.almacen.dto.sucursales.SucursalRequest;
import com.carlos.almacen.dto.sucursales.SucursalResponse;
import com.carlos.almacen.entities.Sucursal;
import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.mappers.SucursalMapper;
import com.carlos.almacen.repositories.SucursalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalServices {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    @Override
    public List<SucursalResponse> listar() {

        log.info("Listando todas las sucursales");

        return sucursalRepository.findAll()
                .stream()
                .map(sucursalMapper::entidadResponse)
                .toList();
    }

    @Override
    public SucursalResponse obtenerPorId(Long id) {

        log.info("Buscando sucursal con id: {}", id);

        Sucursal sucursal = obtenerSucursalOException(id);

        return sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        log.info("Registrando nueva sucursal...");

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        validarDatosUnicos(request);

        sucursalRepository.save(sucursal);

        log.info("Nueva sucursal registrada: {} ", sucursal.getNombre());

        return  sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Actualizando sucursal con id: {}", id);

        validarCambiosUnicos(request, id);

        sucursal.actualizar(request.nombre(), request.direccion());

        log.info("Sucursal con id {} actualizada", id);

        return sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {

        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Eliminando sucursal con id: {}", id);

        sucursalRepository.delete(sucursal);

        log.info("Sucursal con id {} eliminada", id);
    }


    private Sucursal obtenerSucursalOException(Long id) {

        return sucursalRepository.findById(id)
                .orElseThrow(
                        () -> new RecursoNoEncontradoException(
                                "Sucursal no encontrada con id: " + id
                        )
                );
    }

    private void validarDatosUnicos(SucursalRequest request){

        log.info("Validando nombre unico...");

        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
        throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " +  request.nombre());
    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){

        log.info("Validando cambio en nombre unico...");

        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " +  request.nombre());
    }
}
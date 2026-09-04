package com.carlos.almacen.services.ventas;

import com.carlos.almacen.dto.ventas.VentaRequest;
import com.carlos.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {

    List<VentaResponse> listar(); // ahora solo REGISTRADAS

    List<VentaResponse> listarHistoricoCanceladas(); // nuevo

    VentaResponse ObtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest ventaRequest);

    VentaResponse cancelar(Long id);
}
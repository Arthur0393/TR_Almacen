package com.carlos.almacen.enums;

import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.utils.StringCustomUtils;
import com.carlos.almacen.utils.ValoresNumericosUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoVenta obtenerVentaPorDescripcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizado = StringCustomUtils.quitarAcentos(descripcion);

        for(EstadoVenta categoria : values()){
            if(StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizado))
                return categoria;
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con esa descripcion" + descripcion);

    }

    public static EstadoVenta obtenerVentaPorCodigo(Long codigo) {

        ValoresNumericosUtils.validarNumeroRequerido(codigo);

        for(EstadoVenta estadoVenta : values()){
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;


        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con el codigo" + codigo);

    }
}

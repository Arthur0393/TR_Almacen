package com.carlos.almacen.enums;


import com.carlos.almacen.exceptions.RecursoNoEncontradoException;
import com.carlos.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //investigar que es un constructor :D
@Getter //investigar
public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA ("Electroníca"),
    ROPA ("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia"),;

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripcion es requerida");

        String descripcionNormalizado = StringCustomUtils.quitarAcentos(descripcion);

        for(Categoria categoria : values()){
            if(StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizado))
                return categoria;
        }
        throw new RecursoNoEncontradoException("No existe el categoria con ese descripcion" + descripcion);

    }
}

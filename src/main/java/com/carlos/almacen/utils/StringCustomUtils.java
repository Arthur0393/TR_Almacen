package com.carlos.almacen.utils;

import java.util.Locale;

public class StringCustomUtils {

    public static void validarNoVacio(String texto, String mensaje){
        if(texto == null || texto.isBlank()){
            throw new IllegalArgumentException(mensaje);
        }
    }

    public static void validarTamanio(String texto, String mensaje, Integer min, Integer max){
        validarNoVacio(texto, mensaje);
        if(texto.length() < min || texto.length() > max)
            throw new IllegalArgumentException(mensaje);
    }

    public static String quitarAcentos(String texto){
        return texto.toLowerCase()
                .replace("á", "a")
                .replace("é","e")
                .replace("í","i")
                .replace("ó","o")
                .replace("ú","u")
                .replace("ü","u");
    }
}
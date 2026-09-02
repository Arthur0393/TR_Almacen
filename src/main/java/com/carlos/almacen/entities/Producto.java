package com.carlos.almacen.entities;

import com.carlos.almacen.enums.Categoria;
import com.carlos.almacen.utils.StringCustomUtils;
import com.carlos.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
@NoArgsConstructor //investigar
@AllArgsConstructor // investigar
@Builder @Getter //investigar
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(Integer cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(
                cantidad,"La cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(Integer cantidad) {
        ValoresNumericosUtils.validarEnteroPositivo(
                cantidad,"La cantidad debe ser positiva");
        if(cantidad > this.cantidad)
            throw new IllegalArgumentException(
                    "La cantidad debe ser menor o igual a la cantidad actual"
            );
        this.cantidad -= cantidad;
    }

    public void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        StringCustomUtils.validarTamanio(nombre, "El nombre es requerido y debe tener entre 5 y 30 caracteres", 5, 30);
        if (categoria == null)
            throw new IllegalArgumentException("La categoria es requerida");

        ValoresNumericosUtils.validarBigDecimalPositivo(precio,
                "El precio es requerido y debe ser positivo");

        ValoresNumericosUtils.validarEnteroPositivo(cantidad,
                "La cantidad es requerida y debe ser positiva");

    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {

        validarDatos (nombre,categoria,precio,cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}

package com.carlos.almacen.entities;

import com.carlos.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // Le decimos a JPA/Hibernate: "Esta clase es una entidad mapeala a una tabla"
@Table(name = "SUCURSALES") // Especifica explicitamente el nombre de la tabla de la base de datos
@NoArgsConstructor //Crea un constructor vacio
@AllArgsConstructor
@Builder //Objeto encargado en guardar los datos
@Getter
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, unique = true, nullable = false)
    private String nombre;

    @Column(name ="DIRECCION", length = 150, nullable = false)
    private String direccion;

    public void validarDatos(String nombre, String direccion){

        StringCustomUtils.validarTamanio(nombre, "El nombre es requerido y debe tener entre 5 y 50 caracteres",
                5, 50);

        StringCustomUtils.validarTamanio(nombre, "La direccion es requerida y debe tener entre 10 y 150 caracteres",
                10, 150);
    }

    public void actualizar (String nombre, String direccion){
        validarDatos(nombre, direccion);

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }
}

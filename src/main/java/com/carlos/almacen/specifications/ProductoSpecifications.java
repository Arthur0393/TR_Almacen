package com.carlos.almacen.specifications;

import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductoSpecifications{

    public static Specification<Producto> conNombre (String nombre) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (nombre == null || nombre.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("nombre"))
                        , "%" + nombre.trim().toLowerCase() + "%"
                        );
            }
        };
    }

    public static Specification<Producto> conCategoria (String categoria) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (categoria == null || categoria.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("categoria"), Categoria.obtenerCategoriaPorDescripcion(categoria));
            }
        };
    }

    public static Specification<Producto> conPrecioMin (BigDecimal precioMin) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (precioMin == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioMin);
            }
        };
    }

    public static Specification<Producto> conPrecioMax (BigDecimal precioMax) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (precioMax == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMax);
            }
        };
    }
}

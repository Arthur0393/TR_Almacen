package com.carlos.almacen.utils;

import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.enums.Categoria;
import com.carlos.almacen.repositories.ProductoRepository;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        if(productoRepository.count()==0){

            productoRepository.saveAll(List.of(

                    new Producto(null,"Laptop Gamer",
                    Categoria.ELECTRONICA,
                    BigDecimal.valueOf(1500),
                            10),

                    new Producto(null,"Mouse Inalambrico",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(25),
                            50),

                    new Producto(null,"Camiseta Deportiva",
                            Categoria.ROPA,
                            BigDecimal.valueOf(20),
                            100)


            ));

            log.info("Productos de pruebas cargados correctamente");
        }
    }
}

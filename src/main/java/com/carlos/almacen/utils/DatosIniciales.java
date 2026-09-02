package com.carlos.almacen.utils;

import com.carlos.almacen.entities.Producto;
import com.carlos.almacen.entities.Sucursal;
import com.carlos.almacen.enums.Categoria;
import com.carlos.almacen.repositories.ProductoRepository;
import com.carlos.almacen.repositories.SucursalRepository;
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
    private final SucursalRepository sucursalRepository;

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

        if(sucursalRepository.count() == 0){

            sucursalRepository.saveAll(List.of(

                    new Sucursal(null,
                                "Sucursal Central",
                                "Av.Principal 123"),
                    new Sucursal(null,
                            "Sucursal Norte",
                            "Calle Norte 243"),
                    new Sucursal(null,
                            "Sucursal Sur",
                            "Calle Sur 789")
            ));
        }
    }
}

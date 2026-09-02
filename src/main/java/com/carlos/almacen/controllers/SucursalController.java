    package com.carlos.almacen.controllers;

    import com.carlos.almacen.dto.productos.ProductoRequest;
    import com.carlos.almacen.dto.productos.ProductoResponse;
    import com.carlos.almacen.dto.sucursales.SucursalRequest;
    import com.carlos.almacen.dto.sucursales.SucursalResponse;
    import com.carlos.almacen.entities.Sucursal;
    import com.carlos.almacen.services.productos.ProductoServices;
    import com.carlos.almacen.services.sucursales.SucursalServices;
    import io.swagger.v3.oas.annotations.Operation;
    import jakarta.validation.Valid;
    import jakarta.validation.constraints.Positive;
    import lombok.AllArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/sucursales")
    @AllArgsConstructor
    @Validated
    public class SucursalController {

        private final SucursalServices sucursalService;

        @GetMapping
        @Operation(
                summary = "Listar sucursales",
                tags = {"Sucursales - Consultas"}
        )
        public ResponseEntity<List<SucursalResponse>>listar(){
            return ResponseEntity.ok(sucursalService.listar());
        }

        @GetMapping("/{id}")
        @Operation(
                summary = "Obtener sucursal por ID",
                tags = {"Sucursales - Consultas"}
        )
        public ResponseEntity<SucursalResponse> obtenerPorID(
                @PathVariable @Positive(message = "El ID debe ser positivo") Long id
        ){
            return ResponseEntity.ok(sucursalService.obtenerPorId(id));
        }

        @PostMapping
        @Operation(
                summary = "Registrar una nueva sucursal",
                tags = {"Sucursales - Gestion"}
        )
        public ResponseEntity<SucursalResponse> registrar(
                @Valid @RequestBody SucursalRequest request
        ){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(sucursalService.registrar(request));
        }

        @PutMapping("/{id}")
        @Operation(
                summary = "Actualizar una sucursal existente",
                tags = {"Sucursal - Gestion"}
        )
        public ResponseEntity<SucursalResponse> actualizar(
                @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
                @Valid @RequestBody SucursalRequest request
        ){
            return ResponseEntity.ok(sucursalService.actualizar(request, id));
        }

        @DeleteMapping("/{id}")
        @Operation(
                summary = "Eliminar una sucursal",
                tags = {"Sucursal - Gestion"}
        )
        public ResponseEntity<Void> eliminar(
                @PathVariable @Positive(message = "El ID debe ser positivo") Long id
        ){
            sucursalService.eliminar(id);
            return ResponseEntity.noContent().build();
        }

    }
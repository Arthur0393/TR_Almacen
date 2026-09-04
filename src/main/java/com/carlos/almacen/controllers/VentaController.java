package com.carlos.almacen.controllers;

import com.carlos.almacen.dto.ventas.VentaRequest;
import com.carlos.almacen.dto.ventas.VentaResponse;
import com.carlos.almacen.services.ventas.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
@Tag(name = "Ventas", description = "Endpoints para la gestion de ventas")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    @Operation(
            summary = "Listar todas las ventas",
            tags = {"Ventas - Consultas"}
    )
    public ResponseEntity<List<VentaResponse>> listar(){
        // 200 OK: aunque la lista venga vacía, la petición fue exitosa
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una venta activa (no cancelada) por su ID",
            tags = {"Ventas - Consultas"}
    )
    public ResponseEntity<VentaResponse> obtenerPorIdActiva(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        // Si no existe o está cancelada, el service lanza RecursoNoEncontradoException -> 404
        return ResponseEntity.ok(ventaService.ObtenerPorIdActiva(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una nueva venta",
            description = "Descuenta el stock de cada producto, fotografía el precio actual " +
                    "y calcula el total en base a los subtotales",
            tags = {"Ventas - Gestion"}
    )
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest ventaRequest
    ){
        // 201 CREATED: se está creando un nuevo recurso (la venta)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrar(ventaRequest));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(
            summary = "Cancelar una venta",
            description = "Revierte el stock de los productos vendidos. " +
                    "Si la venta ya estaba cancelada, responde 409 CONFLICT",
            tags = {"Ventas - Gestion"}
    )
    public ResponseEntity<VentaResponse> cancelar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ){
        return ResponseEntity.ok(ventaService.cancelar(id));
    }
}
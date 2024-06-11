package com.unir.products.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unir.products.model.pojo.Order;
import com.unir.products.model.request.CreateOrderRequest;
import com.unir.products.service.OrdersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Products Controller", description = "Microservicio encargado de exponer operaciones CRUD sobre productos alojados en una base de datos en memoria.")
public class OrderController {

    private final OrdersService service;

    @GetMapping("/orders/list")
    @Operation(
            operationId = "Obtener ordenes",
            description = "Operacion de lectura",
            summary = "Se devuelve una lista de todas las ordenes almacenadas en la base de datos.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    public ResponseEntity<List<Order>> getProducts(
            @RequestHeader Map<String, String> headers,
            @Parameter(name = "userName", description = "Nombre del usuario", example = "Marcos", required = false)
            @RequestParam(required = false) String userName,
            @Parameter(name = "status", description = "Status de la orden", example = "Pagado", required = false)
            @RequestParam(required = false) String status
            ) {

        log.info("headers: {}", headers);
        List<Order> orders = service.getOrders(userName, status);

        if (orders != null) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("/orders/{orderId}")
    @Operation(
            operationId = "Obtener una orden",
            description = "Operacion de lectura",
            summary = "Se devuelve una order a partir de su identificador.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(
            responseCode = "404",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "No se ha encontrado la orden con el identificador indicado.")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {

        log.info("Request received for order {}", orderId);
        Order order = service.getOrder(orderId);

        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/orders")
    @Operation(
            operationId = "Insertar una orden",
            description = "Operacion de escritura",
            summary = "Se crea una orden a partir de sus datos.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la orden a crear.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateOrderRequest.class))))
    @ApiResponse(
            responseCode = "201",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Datos incorrectos introducidos.")
    public ResponseEntity<Order> addOrder(@RequestBody CreateOrderRequest request) {

        Order createdProduct = service.createOrder(request);

        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PatchMapping("/orders/{orderId}")
    @Operation(
            operationId = "Modificar parcialmente una orden",
            description = "Operacion de escritura",
            summary = " Se modifica parcialmente una orden.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la orden a actualizar.",
                    required = true,
                    content = @Content(mediaType = "application/merge-patch+json", schema = @Schema(implementation = String.class))))
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto inválido o datos incorrectos introducidos.")
    public ResponseEntity<Order> patchOrder(@PathVariable String orderId, @RequestBody String patchBody) {

        Order patched = service.updateOrder(orderId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/orders/cancel/{orderId}")
    @Operation(
            operationId = "Cancelar una orden",
            description = "Operacion de escritura",
            summary = " Se modifica parcialmente una orden.")
    @ApiResponse(
            responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(
            responseCode = "400",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class)),
            description = "Producto inválido o datos incorrectos introducidos.")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {

        Order patched = service.cancelOrder(orderId);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}

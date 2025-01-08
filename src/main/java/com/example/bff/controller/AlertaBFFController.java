package com.example.bff.controller;

import com.example.bff.model.Alerta;
import com.example.bff.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bff/alertas")
public class AlertaBFFController {

    private final WebClient webClient;

    @Value("${backend.service.url}")
    private String backendServiceUrl;

    @Autowired
    public AlertaBFFController(WebClient webClient) {
        this.webClient = webClient;
    }

    // Obtener todas las alertas
    @GetMapping
    public Mono<ResponseEntity<Response>> getAllAlertas() {
        return webClient.get()
                .uri(backendServiceUrl + "/alertas")
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Obtener una alerta por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response>> getAlertaById(@PathVariable Long id) {
        return webClient.get()
                .uri(backendServiceUrl + "/alertas/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Crear una nueva alerta
    @PostMapping
    public Mono<ResponseEntity<Response>> createAlerta(@RequestBody Alerta alerta) {
        return webClient.post()
                .uri(backendServiceUrl + "/alertas")
                .bodyValue(alerta)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Actualizar una alerta existente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateAlerta(@PathVariable Long id, @RequestBody Alerta alerta) {
        return webClient.put()
                .uri(backendServiceUrl + "/alertas/" + id)
                .bodyValue(alerta)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Eliminar una alerta por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteAlerta(@PathVariable Long id) {
        return webClient.delete()
                .uri(backendServiceUrl + "/alertas/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Obtener alertas por ID de paciente
    @GetMapping("/paciente/{pacienteId}")
    public Mono<ResponseEntity<Response>> getAlertasByPacienteId(@PathVariable Long pacienteId) {
        return webClient.get()
                .uri(backendServiceUrl + "/alertas/paciente/" + pacienteId)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Método auxiliar para construir la respuesta HTTP
    private ResponseEntity<Response> buildResponseEntity(Response response) {
        if ("success".equals(response.getState())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}

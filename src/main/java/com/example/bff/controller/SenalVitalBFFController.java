package com.example.bff.controller;

import com.example.bff.model.Response;
import com.example.bff.model.SenalVital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bff/senales-vitales")
public class SenalVitalBFFController {

    private final WebClient webClient;

    @Value("${backend.service.url}")
    private String backendServiceUrl;

    @Autowired
    public SenalVitalBFFController(WebClient webClient) {
        this.webClient = webClient;
    }

    // Obtener todas las señales vitales
    @GetMapping
    public Mono<ResponseEntity<Response>> getAllSenales() {
        return webClient.get()
                .uri(backendServiceUrl + "/senales-vitales")
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Obtener una señal vital por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response>> getSenalById(@PathVariable Long id) {
        return webClient.get()
                .uri(backendServiceUrl + "/senales-vitales/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Crear una nueva señal vital
    @PostMapping
    public Mono<ResponseEntity<Response>> createSenal(@RequestBody SenalVital senalVital) {
        return webClient.post()
                .uri(backendServiceUrl + "/senales-vitales")
                .bodyValue(senalVital)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Actualizar una señal vital existente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updateSenal(@PathVariable Long id, @RequestBody SenalVital senalVital) {
        return webClient.put()
                .uri(backendServiceUrl + "/senales-vitales/" + id)
                .bodyValue(senalVital)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Eliminar una señal vital por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deleteSenal(@PathVariable Long id) {
        return webClient.delete()
                .uri(backendServiceUrl + "/senales-vitales/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Obtener señales vitales por ID de paciente
    @GetMapping("/paciente/{pacienteId}")
    public Mono<ResponseEntity<Response>> getSenalesByPacienteId(@PathVariable Long pacienteId) {
        return webClient.get()
                .uri(backendServiceUrl + "/senales-vitales/paciente/" + pacienteId)
                .retrieve()
                .bodyToMono(Response.class)
                .map(this::buildResponseEntity);
    }

    // Obtener las últimas 10 señales vitales de un paciente
    @GetMapping("/paciente/{pacienteId}/ultimas")
    public Mono<ResponseEntity<Response>> getLast10SenalesByPacienteId(@PathVariable Long pacienteId) {
        return webClient.get()
                .uri(backendServiceUrl + "/senales-vitales/paciente/" + pacienteId + "/ultimas")
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

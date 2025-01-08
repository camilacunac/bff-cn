package com.example.bff.controller;

import com.example.bff.model.Paciente;
import com.example.bff.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bff/pacientes")
public class PacienteBFFController {

    private final WebClient webClient;

    @Value("${backend.service.url}")
    private String backendServiceUrl;

    @Autowired
    public PacienteBFFController(WebClient webClient) {
        this.webClient = webClient;
    }

    // Obtener todos los pacientes
    @GetMapping
    public Mono<ResponseEntity<Response>> getAllPacientes() {
        return webClient.get()
                .uri(backendServiceUrl + "/pacientes")
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> buildResponseEntity(response));
    }

    // Obtener un paciente por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Response>> getPacienteById(@PathVariable Long id) {
        return webClient.get()
                .uri(backendServiceUrl + "/pacientes/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> buildResponseEntity(response));
    }

    // Crear un nuevo paciente
    @PostMapping
    public Mono<ResponseEntity<Response>> createPaciente(@RequestBody Paciente paciente) {
        return webClient.post()
                .uri(backendServiceUrl + "/pacientes")
                .bodyValue(paciente)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> buildResponseEntity(response));
    }

    // Actualizar un paciente existente
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Response>> updatePaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        return webClient.put()
                .uri(backendServiceUrl + "/pacientes/" + id)
                .bodyValue(paciente)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> buildResponseEntity(response));
    }

    // Eliminar un paciente por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Response>> deletePaciente(@PathVariable Long id) {
        return webClient.delete()
                .uri(backendServiceUrl + "/pacientes/" + id)
                .retrieve()
                .bodyToMono(Response.class)
                .map(response -> buildResponseEntity(response));
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

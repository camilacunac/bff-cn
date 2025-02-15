package com.example.bff.model;

import com.example.bff.config.RabbitMQConfigService2;
import com.example.bff.model.HistoricoSignosVitales;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ReportListener {

    private final ObjectMapper objectMapper;
    private static final String REPORTES_DIR = "reportes/";

    public ReportListener() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @RabbitListener(queues = RabbitMQConfigService2.QUEUE_NAME_2)
    public void listen(String message) {
        try {

            HistoricoSignosVitales reporte = objectMapper.readValue(message, HistoricoSignosVitales.class);
            System.out.println("📊 Reporte recibido desde RabbitMQ para paciente ID: " + reporte.getPacienteId());

            guardarReporteComoArchivo(reporte);

        } catch (Exception e) {
            System.err.println("❌ Error procesando el reporte: " + e.getMessage());
        }
    }

    private void guardarReporteComoArchivo(HistoricoSignosVitales reporte) {
        try {

            Files.createDirectories(Paths.get(REPORTES_DIR));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = REPORTES_DIR + "reporte_" + reporte.getPacienteId() + "_" + timestamp + ".json";

            objectMapper.writeValue(new File(filename), reporte);
            System.out.println("✅ Reporte guardado en: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar el reporte: " + e.getMessage());
        }
    }
}

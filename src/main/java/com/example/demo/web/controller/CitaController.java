package com.example.demo.web.controller;

import com.example.demo.domain.service.CitaService;
import com.example.demo.persistence.entity.Cita;
import com.example.demo.persistence.entity.Consultorio;
import com.example.demo.persistence.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    @Autowired
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<Cita>> getCitas(
            @RequestParam Long doctorId,
            @RequestParam Long consultorioId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {

        // Aquí puedes buscar al doctor y al consultorio por sus IDs antes de llamar al servicio.

        Doctor doctor = new Doctor(); // Reemplazar con la búsqueda real
        doctor.setDoctorId(doctorId);

        Consultorio consultorio = new Consultorio(); // Reemplazar con la búsqueda real
        consultorio.setConsultorioId(consultorioId);

        List<Cita> citas = citaService.getCitasPorDoctorConsultorioYFecha(doctor, consultorio, fechaInicio, fechaFin);
        return ResponseEntity.ok(citas);
    }

    // Endpoint para cancelar una cita
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarCita(@PathVariable Long id) {
        boolean resultado = citaService.cancelarCitaSiEsPendiente(id);
        if (resultado) {
            return ResponseEntity.ok("Cita cancelada con éxito");
        } else {
            return ResponseEntity.badRequest().body("No se pudo cancelar la cita");
        }
    }
}

package com.example.demo.domain.service;

import com.example.demo.persistence.CitaRepository;
import com.example.demo.persistence.crud.CitaCrudRepository;
import com.example.demo.persistence.entity.Cita;
import com.example.demo.persistence.entity.Consultorio;
import com.example.demo.persistence.entity.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaCrudRepository citaCrudRepository;

    @Autowired
    public CitaService(CitaCrudRepository citaCrudRepository) {
        this.citaCrudRepository = citaCrudRepository;
    }

    public List<Cita> getCitasPorDoctorConsultorioYFecha(Doctor doctor, Consultorio consultorio, Date fechaInicio, Date fechaFin) {
        return citaCrudRepository.findByDoctorAndConsultorioAndHorarioConsultaBetween(doctor, consultorio, fechaInicio, fechaFin);
    }

    @Transactional
    public boolean cancelarCitaSiEsPendiente(Long citaId) {
        Optional<Cita> citaOptional = citaCrudRepository.findById(citaId);

        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            // Verifica si la cita está pendiente y aún no se ha realizado
            if ("Pendiente".equals(cita.getEstadoCita()) && cita.getHorarioConsulta().after(new Date())) {
                cita.setEstadoCita("Cancelada");
                citaCrudRepository.save(cita);
                return true;
            }
        }
        return false;
    }
}

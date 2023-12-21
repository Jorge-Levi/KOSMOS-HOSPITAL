package com.example.demo.persistence;

import com.example.demo.persistence.crud.CitaCrudRepository;
import com.example.demo.persistence.entity.Cita;
import com.example.demo.persistence.entity.Consultorio;
import com.example.demo.persistence.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class CitaRepository {
    @Autowired
    private CitaCrudRepository citaCrudRepository;

    public List<Cita> getCitasPorDoctorConsultorioYFecha(
            Doctor doctor,
            Consultorio consultorio,
            Date fechaInicio,
            Date fechaFin
    ) {
        return citaCrudRepository.findByDoctorAndConsultorioAndHorarioConsultaBetween(
                doctor,
                consultorio,
                fechaInicio,
                fechaFin
        );
    }

    public boolean cancelarCitaSiEsPendiente(Long citaId) {
        Optional<Cita> citaOptional = citaCrudRepository.findById(citaId);

        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            // Verifica si la cita está pendiente y aún no se ha realizado
            if (cita.getEstadoCita().equals("Pendiente") && cita.getHorarioConsulta().after(new Date())) {
                cita.setEstadoCita("Cancelada");
                citaCrudRepository.save(cita);
                return true;
            }
        }
        return false;
    }
}

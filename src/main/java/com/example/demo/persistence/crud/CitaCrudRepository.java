package com.example.demo.persistence.crud;

import com.example.demo.persistence.entity.Cita;
import com.example.demo.persistence.entity.Consultorio;
import com.example.demo.persistence.entity.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CitaCrudRepository extends CrudRepository<Cita, Long> {
    List<Cita> findByDoctorAndConsultorioAndHorarioConsultaBetween(
            Doctor doctor,
            Consultorio consultorio,
            Date fechaInicio,
            Date fechaFin
    );

    Optional<Cita> findById(Long id);
}

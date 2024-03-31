package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Turno;

import java.util.List;
import java.util.Optional;

public interface TurnoService {
    List<Turno> listarTurnos();
    Optional<Turno> buscarTurno(Long id);
    Turno guardarTurno(Turno turno);
    Turno actualizarTurno(Turno turno);
    void eliminarTurno(Long id);

}

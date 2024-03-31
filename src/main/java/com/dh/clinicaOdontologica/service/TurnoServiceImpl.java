package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Turno;
import com.dh.clinicaOdontologica.respository.TurnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoServiceImpl implements TurnoService{

    @Autowired
    private TurnosRepository turnosRepository;

    @Override
    public List<Turno> listarTurnos() {
        return turnosRepository.findAll();
    }

    @Override
    public Optional<Turno> buscarTurno(Long id) {
        return turnosRepository.findById(id);
    }

    @Override
    public Turno guardarTurno(Turno turno) {
        return turnosRepository.save(turno);
    }

    @Override
    public Turno actualizarTurno(Turno turno) {
        return turnosRepository.save(turno);
    }

    @Override
    public void eliminarTurno(Long id) {
        turnosRepository.deleteById(id);
    }

}

package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Odontologo;

import java.util.List;
import java.util.Optional;

public interface OdontologoService {
    List<Odontologo> listarOdontologos();
    Optional<Odontologo> buscarOdontologo(Long id);
    Odontologo guardarOdontologo(Odontologo odontologo);
    Odontologo actualizarOdontologo(Odontologo odontologo);
    void eliminarOdontologo(Long id);
}

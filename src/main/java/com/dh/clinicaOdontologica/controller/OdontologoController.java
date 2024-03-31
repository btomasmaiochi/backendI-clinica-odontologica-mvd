package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.entities.Odontologo;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologos(){
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        if(odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            String respuestaRnfe = "El odontologo con ID: " + id + " no se encuentra registrado. Por favor, verifique si el ID ingresado es correcto.";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @PostMapping()
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(odontologo.getId());
        if(odontologoBuscado.isPresent()) {
            logger.info("Cuidado. Se modificó el odontólogo con ID= " + odontologo.getId());
            return ResponseEntity.ok(odontologoService.actualizarOdontologo(odontologo));
        } else {
            String respuestaRnfe = "El odontólogo con ID " + odontologo.getId() + " a actualizar no se encuentra registrado";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        if(odontologoService.buscarOdontologo(id).isPresent()){
            odontologoService.eliminarOdontologo(id);
            logger.info("Cuidado. Se eliminó al odontologo con ID: " + id);;
            return ResponseEntity.ok("Se eliminó el odontologo con ID: " + id);
        } else {
            String respuestaRnfe = "No se encontró al Odontólogo con ID: " + id +" para realizar la eliminación. Por favor ingrese nuevamente el ID";
            throw new ResourceNotFoundException(respuestaRnfe);
        }

    }
}

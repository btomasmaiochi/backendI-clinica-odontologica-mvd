package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.entities.Odontologo;
import com.dh.clinicaOdontologica.entities.Paciente;
import com.dh.clinicaOdontologica.entities.Turno;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.OdontologoService;
import com.dh.clinicaOdontologica.service.PacienteService;
import com.dh.clinicaOdontologica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            String respuestaRnfe = "El turno con ID: " + id + " no se encuentra registrado. Por favor verifique si ingresó el ID correctamente.";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @PostMapping
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(turno.getOdontologo().getId());

        if(pacienteBuscado.isPresent() && odontologoBuscado.isPresent()){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }  else {
            String respuestaBre;
            if (pacienteBuscado.isPresent()) {
               respuestaBre = "El odontologo con ID: " + turno.getOdontologo().getId() + " no existe";
                throw new BadRequestException(respuestaBre);
            } else if (odontologoBuscado.isPresent()) {
                respuestaBre = "El paciente con ID: " + turno.getPaciente().getId() + " no existe";
                throw new BadRequestException(respuestaBre);
            } else {
                respuestaBre = "El odontologo con ID: " + turno.getOdontologo().getId() + " y el paciente con ID: " + turno.getPaciente().getId() + " no existen";
                throw new BadRequestException(respuestaBre);
            }
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarTurno(@RequestBody Turno turno) throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(turno.getId());
        if(turnoBuscado.isPresent()){
            turnoService.actualizarTurno(turno);
            logger.info("Se modificó el turno con ID= " + turno.getId());
            return ResponseEntity.ok("El turno con ID: " + turno.getId() + " se modificó con éxito");
        } else {
            String respuestaRnfe = "El turno con ID " + turno.getId() + " a actualizar no existe";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            logger.info("Se eliminó el turno con ID: "+ id + " perteneciente al paciente con DNI: " + turnoBuscado.get().getPaciente().getDni());
            return ResponseEntity.ok("El turno con ID " + id + " se eliminó con éxito");
        } else {
            String respuestaRnfe = "El turno con ID " + id + " a eliminar no existe";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }
}

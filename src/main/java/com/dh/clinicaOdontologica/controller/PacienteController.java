package com.dh.clinicaOdontologica.controller;
import com.dh.clinicaOdontologica.entities.Paciente;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> traerPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> traerPacientePorId(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else{
            String respuestaRnfe = "El paciente con ID: " + id + " buscado no se encuentra registrado. Por favor verifique si ingresó el ID correctamente";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarNuevoPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        Optional<Paciente> pacienteParaActualizar=pacienteService.buscarPaciente(paciente.getId());
        if(pacienteParaActualizar.isPresent()){
            logger.info("Cuidado. Se modificó el paciente con ID= " + paciente.getId());
            return ResponseEntity.ok(pacienteService.actualizarPaciente(paciente));
        }
        else{
            String respuestaRnfe = "El paciente con ID: " + paciente.getId() + " a actualizar no existe";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteABorrar=pacienteService.buscarPaciente(id);
        if (pacienteABorrar.isPresent()){
            pacienteService.eliminarPaciente(id);
            logger.info("Cuidado. Se eliminó al paciente con ID: " + id);
            return ResponseEntity.ok("Se eliminó correctamente al paciente con ID: " + id);
        }
        else{
            String respuestaRnfe = "No se encontró al paciente con ID: " + id + " para realizar su eliminación. ¿Ingresó un ID correcto?.";
            throw new ResourceNotFoundException(respuestaRnfe);
        }
    }

}
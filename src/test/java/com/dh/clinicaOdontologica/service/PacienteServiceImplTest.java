package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Domicilio;
import com.dh.clinicaOdontologica.entities.Paciente;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceImplTest {
    @Autowired
    PacienteService pacienteService;

    @Test
    @Order(1)
    public void registrarPaciente(){
        Domicilio domicilioPaciente = new Domicilio("Calle 157", 125, "La Plata", "Buenos Aires");
        Paciente nuevoPaciente = new Paciente("Justel", "Nacho", "nachoj@gmail.com", 39874214, LocalDate.of(2022,05, 18), domicilioPaciente);

        pacienteService.guardarPaciente(nuevoPaciente);
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(1L);

        assertNotNull(pacienteBuscado);
        assertEquals("Justel", pacienteBuscado.get().getApellido());
    }
    @Test
    @Order(2)
    public void listarPacientesTest(){
        List<Paciente> listaPacientes=pacienteService.listarPacientes();
        assertTrue(listaPacientes.size()>0);
    }
    @Order(3)
    @Test
    public void buscarPacientePorIdTest(){
        Long id = 1L;
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(id);
        assertEquals("Justel", pacienteBuscado.get().getApellido());
    }
    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Optional<Paciente> pacienteAModificar = pacienteService.buscarPaciente(1L);
        pacienteAModificar.get().setApellido("Forrester");
        pacienteAModificar.get().setNombre("Juana");

        pacienteService.actualizarPaciente(pacienteAModificar.get());

        Optional<Paciente> pacienteModificado=pacienteService.buscarPaciente(1L);

        assertEquals("Forrester",pacienteModificado.get().getApellido());
        assertEquals("Juana",pacienteModificado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest(){
        Long idBuscadoABorrar=1L;
        pacienteService.eliminarPaciente(idBuscadoABorrar);
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(idBuscadoABorrar);
        assertTrue(pacienteBuscado.isEmpty());
    }


}
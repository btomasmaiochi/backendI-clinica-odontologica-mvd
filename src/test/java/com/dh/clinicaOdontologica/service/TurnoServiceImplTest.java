package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Domicilio;
import com.dh.clinicaOdontologica.entities.Odontologo;
import com.dh.clinicaOdontologica.entities.Paciente;
import com.dh.clinicaOdontologica.entities.Turno;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceImplTest {
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    public void registrarTurnoTest() {
        Domicilio domicilioPaciente = new Domicilio("Arenales", 125, "Pilar", "Buenos Aires");
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Forrester","Juana","juanaf@gmail.com", 38457814, LocalDate.of(2022, 05, 21), domicilioPaciente));
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("Justel", "Nacho", "NJ12"));
        Turno turno = new Turno(odontologo,paciente, LocalDate.of(2022, 05, 07));
        turnoService.guardarTurno(turno);

        Optional<Turno> turnoBuscado = turnoService.buscarTurno(1L);
        assertNotNull(turnoBuscado);
    }

    @Test
    @Order(2)
    public void listarTurnos(){
        List<Turno> listaTurnos = turnoService.listarTurnos();
        assertTrue(listaTurnos.size() > 0);
    }

    @Test
    @Order(3)
    public void buscarTurnoPorId(){
        Long id = 1L;
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);

        assertEquals(1L, turnoBuscado.get().getId());
    }

    @Test
    @Order(4)
    public void modificarTurno(){
        Long id = 1L;
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);
        turnoBuscado.get().setFecha(LocalDate.of(2022,06,17));

        assertEquals(LocalDate.of(2022,06,17), turnoBuscado.get().getFecha());
    }

    @Test
    @Order(5)
    public void eliminarTurno(){
        Long id = 1L;
        turnoService.eliminarTurno(id);
        Optional<Turno> turnoBuscado = turnoService.buscarTurno(id);

        assertTrue(turnoBuscado.isEmpty());
    }



}
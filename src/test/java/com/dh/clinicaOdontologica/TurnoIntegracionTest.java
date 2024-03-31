package com.dh.clinicaOdontologica;


import com.dh.clinicaOdontologica.entities.Domicilio;
import com.dh.clinicaOdontologica.entities.Odontologo;
import com.dh.clinicaOdontologica.entities.Paciente;
import com.dh.clinicaOdontologica.entities.Turno;
import com.dh.clinicaOdontologica.service.OdontologoService;
import com.dh.clinicaOdontologica.service.PacienteService;
import com.dh.clinicaOdontologica.service.TurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoIntegracionTest {
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;
    //cargar un turno
    @Test
    @Order(1)
    public void datosInicialTurnoTest() {
        Domicilio domicilioPaciente = new Domicilio("Arenales", 125, "Pilar", "Buenos Aires");
        Paciente paciente= pacienteService.guardarPaciente(new Paciente("Forrester","Juana","juanaf@gmail.com", 38457814, LocalDate.of(2022, 05, 21), domicilioPaciente));
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("Justel", "Nacho", "NJ12"));

        assertNotNull(turnoService.guardarTurno(new Turno( odontologo,paciente, LocalDate.of(2022, 05, 07))));

    }

    @Test
    @Order(2)
    public void listarTurnos() throws Exception {
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }

    //testear la busqueda de turno
    @Test
    @Order(3)
    public void buscarTurnoPorId() throws Exception {
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertTrue(respuesta.getResponse().getContentAsString().contains("38457814"));
    }

}

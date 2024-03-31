package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.entities.Odontologo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceImplTest {
    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    public void agregarOdontologoTest(){
        Odontologo odontologoParaAgregar=new Odontologo("Cepeda", "Carlos", "AAB123");
        odontologoService.guardarOdontologo(odontologoParaAgregar);
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(1L);
        assertNotNull(odontologoBuscado);
        assertEquals("Cepeda",odontologoBuscado.get().getApellido());
    }
    @Test
    @Order(2)
    public void buscarPorIdTest(){
        Long odontologoIdBuscado=1L;
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(odontologoIdBuscado);
        assertNotNull(odontologoBuscado);
    }
    @Test
    @Order(3)
    public void listarOdontologosTest(){
        List<Odontologo> listaPrueba=odontologoService.listarOdontologos();
        assertTrue(listaPrueba.size()>0);
    }
    @Test
    @Order(4)
    public void actualizarOdontologoTest(){
        Optional<Odontologo> odontologoParaActualizar= odontologoService.buscarOdontologo(1L);
        odontologoParaActualizar.get().setMatricula("CP125");
        odontologoService.actualizarOdontologo(odontologoParaActualizar.get());

        Optional<Odontologo> odontologoModificado=odontologoService.buscarOdontologo(1L);
        assertEquals("CP125", odontologoModificado.get().getMatricula());
    }
    @Test
    @Order(5)
    public void eliminarOdontologTest(){
        Long eliminar=1L;
        odontologoService.eliminarOdontologo(eliminar);
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(eliminar);
        assertTrue(odontologoBuscado.isEmpty());
    }

}
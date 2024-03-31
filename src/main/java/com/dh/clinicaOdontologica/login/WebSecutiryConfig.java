package com.dh.clinicaOdontologica.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecutiryConfig extends WebSecurityConfigurerAdapter {
    private UsuarioService usuarioService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecutiryConfig(UsuarioService usuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioService = usuarioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                //.antMatchers("/pacientes").hasRole("ADMIN")
                //.antMatchers("/odontologos").hasRole("ADMIN")
                //.antMatchers("/turnos").hasRole("USER")
                .antMatchers("/get_all_odontologos.html",
                                        "/post_odontologo.html",
                                        "/get_all_pacientes.html",
                                        "/post_paciente.html",
                                        "/get_all_turnos.html")
                .hasRole("ADMIN")
                .antMatchers("/post_turno.html").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized.html");
    }
}

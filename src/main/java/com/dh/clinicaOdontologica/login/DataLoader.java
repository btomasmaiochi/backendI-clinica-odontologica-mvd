package com.dh.clinicaOdontologica.login;

import com.dh.clinicaOdontologica.entities.Usuario;
import com.dh.clinicaOdontologica.entities.UsuarioRole;
import com.dh.clinicaOdontologica.respository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private UsuarioRepository usuarioRepository;

    @Autowired
    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashUsuario = passwordEncoder.encode("user");
        Usuario usuario = new Usuario("Bruna", "user", "bru@gmail.com", hashUsuario, UsuarioRole.ROLE_USER);

        String hashAdmin = passwordEncoder.encode("admin");
        Usuario usuarioAdmin = new Usuario("Sofia", "admin", "admin@admin.com", hashAdmin, UsuarioRole.ROLE_ADMIN);

        usuarioRepository.save(usuario);
        usuarioRepository.save(usuarioAdmin);
    }
}

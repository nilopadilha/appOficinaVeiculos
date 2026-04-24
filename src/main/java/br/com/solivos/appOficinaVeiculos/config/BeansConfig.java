package br.com.solivos.appOficinaVeiculos.config;

import br.com.solivos.appOficinaVeiculos.enumerated.Role;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@oficina.com";
            Usuario admin = repository.findByEmailIgnoreCase(email).orElse(new Usuario());
            
            admin.setNome("Administrador Sistema");
            admin.setEmail(email);
            
            if (admin.getId() == null) {
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
            }
            
            repository.save(admin);
            System.out.println(">>> USUÁRIO ADMIN VALIDADO/CRIADO: admin@oficina.com / admin123");
        };
    }
}

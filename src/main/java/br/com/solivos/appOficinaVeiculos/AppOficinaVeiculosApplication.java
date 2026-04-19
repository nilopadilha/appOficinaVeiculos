package br.com.solivos.appOficinaVeiculos;

import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import br.com.solivos.appOficinaVeiculos.enumerated.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppOficinaVeiculosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppOficinaVeiculosApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		return args -> {
			String email = "admin@oficina.com";
			Usuario admin = repository.findByEmailIgnoreCase(email).orElse(new Usuario());
			
			admin.setNome("Administrador Sistema");
			admin.setEmail(email);
			admin.setSenha(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ADMIN);
			
			repository.save(admin);
			System.out.println(">>> SENHA DO ADMIN ATUALIZADA/CRIADA COM SUCESSO NO BANCO: admin@oficina.com / admin123");
		};
	}
}

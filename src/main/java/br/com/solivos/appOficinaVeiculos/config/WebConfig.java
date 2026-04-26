package br.com.solivos.appOficinaVeiculos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS em todos os endpoints da API
                .allowedOrigins("http://localhost:5173") // A URL padrão do servidor de desenvolvimento do Vite/Vue
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Permite todos os headers (importante para o Authorization do JWT)
                .allowCredentials(true); // Permite envio de cookies ou credenciais se necessário
    }
}
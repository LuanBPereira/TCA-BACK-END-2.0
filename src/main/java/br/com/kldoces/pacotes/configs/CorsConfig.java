package br.com.kldoces.pacotes.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Adicione outras configurações de CORS conforme necessário
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500") // Permitir solicitações do frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowCredentials(true); // Permitir credenciais (por exemplo, cookies)
    }
}
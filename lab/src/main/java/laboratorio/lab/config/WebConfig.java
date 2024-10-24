package laboratorio.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite todas las rutas del backend
                .allowedOrigins("*")  // Origen permitido
                .allowedMethods("*")  // Métodos HTTP permitidos
                .allowedHeaders("*");  // Permite todos los encabezados  // Permite el uso de cookies y credenciales
    }
}
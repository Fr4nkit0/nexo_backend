package com.nexo.common.infraestructura.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class NexoApplicationConfig {

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return new ApplicationAuditorAware();
    }

}

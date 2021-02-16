package com.apsout.electronictestimony.api;

import com.apsout.electronictestimony.api.config.ResourceProperties;
import com.apsout.electronictestimony.api.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.apsout.electronictestimony.api.controller",
        "com.apsout.electronictestimony.api.serviceimpl",
        "com.apsout.electronictestimony.api.scheduler",
        "com.apsout.electronictestimony.api.config.security",
        "com.apsout.electronictestimony.api.util.security",})
@EnableJpaRepositories("com.apsout.electronictestimony.api.repository")
@EntityScan("com.apsout.electronictestimony.api.entity")
@EnableConfigurationProperties({ResourceProperties.class})
public class ElectronicTestimonyApiApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ElectronicTestimonyApiApplication.class);

    @Autowired
    private ApplicationService applicationService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(ElectronicTestimonyApiApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/applications/**").allowedOrigins("*");
                registry.addMapping("/api/v1/workflows/**").allowedOrigins("*");
                registry.addMapping("/api/v1/enterprises/**").allowedOrigins("*");
                registry.addMapping("/api/v1/people/**").allowedOrigins("*");
                registry.addMapping("/api/v1/operators/**").allowedOrigins("*");
                registry.addMapping("/api/v1/documents/**").allowedOrigins("*");
                registry.addMapping("/api/v1/public/**").allowedOrigins("*");
                registry.addMapping("/api/v1/jobs").allowedOrigins("*");
                registry.addMapping("/api/v1/users/**").allowedOrigins("*");
                registry.addMapping("/api/v1/workflows/**").allowedOrigins("*");
                registry.addMapping("/api/v1/participants/**").allowedOrigins("*");
                registry.addMapping("/api/v1/notifications/**").allowedOrigins("*");
                registry.addMapping("/api/v1/persontypes/**").allowedOrigins("*");
                registry.addMapping("/api/v1/identificationdocuments/**").allowedOrigins("*");
                registry.addMapping("/api/v1/sieemails/**").allowedOrigins("*");
                registry.addMapping("/api/v1/siecredential/**").allowedOrigins("*");
                registry.addMapping("/api/v1/authorities/**").allowedOrigins("*");
                registry.addMapping("/api/v1/outside/**").allowedOrigins("*");
            }
        };
    }

    @Override
    public void run(String... strings) {
        logger.info("Started the IOFESign");
        applicationService.reloadStaticData();
    }

}

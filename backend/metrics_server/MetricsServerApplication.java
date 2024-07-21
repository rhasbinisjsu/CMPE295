package com.cropsense.metrics_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;

@SpringBootApplication
@EnableScheduling
public class MetricsServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(MetricsServerApplication.class, args);
		ApplicationServerProfile server = context.getBean(ApplicationServerProfile.class);
		System.out.print(server + "\n");
	}

	// Global CORS configuration <-- Set to accept all traffic --> 
    @Bean
    WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowCredentials(false);
			}
		};
	}

}

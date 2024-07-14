package com.cropsense.metrics_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cropsense.metrics_server.Configuration.ApplicationServerProfile;

@SpringBootApplication
public class MetricsServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(MetricsServerApplication.class, args);
		ApplicationServerProfile server = context.getBean(ApplicationServerProfile.class);
		System.out.print(server + "\n");
	}

}

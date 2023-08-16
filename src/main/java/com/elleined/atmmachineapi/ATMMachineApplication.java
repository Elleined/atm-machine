package com.elleined.atmmachineapi;

import com.elleined.atmmachineapi.service.atm.CommandLineATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class ATMMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ATMMachineApplication.class, args);
	}


	@Autowired
	private CommandLineATMService commandLineATMService;
	@Bean
	CommandLineRunner run() {
		return args -> commandLineATMService.run();
	}

}

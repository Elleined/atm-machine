package com.elleined.atmmachineapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class MoneySenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

}

package com.elleined.atmmachineapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ATMMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ATMMachineApplication.class, args);
	}


	/* Uncomment these block of code if you want to use commandline instead of web service
	@Autowired
	private CommandLineATMService commandLineATMService;

	@Bean
	CommandLineRunner run() {
		return args -> commandLineATMService.run();
	}
	*/
}

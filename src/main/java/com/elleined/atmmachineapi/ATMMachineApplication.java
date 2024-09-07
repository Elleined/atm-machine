package com.elleined.atmmachineapi;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableTransactionManagement
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
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

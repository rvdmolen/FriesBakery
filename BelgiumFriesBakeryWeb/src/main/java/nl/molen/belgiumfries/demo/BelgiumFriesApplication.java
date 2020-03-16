package nl.molen.belgiumfries.demo;

import nl.molen.belgiumfries.demo.configuration.BelgiumFriesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import({BelgiumFriesConfiguration.class})
public class BelgiumFriesApplication {
	public static void main(String[] args) {
		SpringApplication.run(BelgiumFriesApplication.class, args);
	}
}

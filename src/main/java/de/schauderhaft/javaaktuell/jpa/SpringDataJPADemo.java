package de.schauderhaft.javaaktuell.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class SpringDataJPADemo {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJPADemo.class, args);
	}


}

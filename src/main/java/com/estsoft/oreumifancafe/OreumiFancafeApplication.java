package com.estsoft.oreumifancafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OreumiFancafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OreumiFancafeApplication.class, args);
	}

}
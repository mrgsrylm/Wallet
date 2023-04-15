package com.pollspolling.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {PollspollingBackendApplication.class, Jsr310JpaConverters.class})
public class PollspollingBackendApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PollspollingBackendApplication.class, args);
	}

}

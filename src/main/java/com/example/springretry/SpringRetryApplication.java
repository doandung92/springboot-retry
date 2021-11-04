package com.example.springretry;

import com.example.springretry.service.MyJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringRetryApplication implements CommandLineRunner {
	private final MyJob myJob;

	public static void main(String[] args) {
		SpringApplication.run(SpringRetryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Default
//		myJob.defaultRetry(true);

		// Custom
//		myJob.customRetry(true);

		var response = myJob.useRetryTemplate(true);
		log.info(response);
	}
}

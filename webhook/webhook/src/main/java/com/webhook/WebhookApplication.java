package com.webhook;

import com.webhook.model.entity.User;
import com.webhook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WebhookApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(WebhookApplication.class, args);
	}


	private final UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

	}
}

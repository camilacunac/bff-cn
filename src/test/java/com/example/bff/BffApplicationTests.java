package com.example.bff;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class BffApplicationTests {

	@Configuration
	static class TestConfig {
		@Bean
		public WebClient webClient() {
			return WebClient.builder().baseUrl("http://localhost:8080/api").build();
		}
	}

	@Test
	@Disabled
	void contextLoads() {
	}

}

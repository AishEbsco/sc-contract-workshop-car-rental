package com.example.carrental;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.assertj.core.api.BDDAssertions;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock (port = 8081)
@AutoConfigureStubRunner(workOffline = true, ids = "com.example:fraud-service:+:stubs:8089")
public class CarRentalApplicationTests {

	@Test
	public void test_should_return_all_frauds_dumbmock() {

		String json = "[\"marcin\",\"josh\"]";

		// add wiremock stub to simulate the fraud service
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/fraud"))
				.willReturn(WireMock.aResponse().withBody(json).withStatus(201)));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8081/fraud", String.class);

		BDDAssertions.then(entity.getStatusCode().value()).isEqualTo(201);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

	@Test
	public void test_should_return_all_frauds_stubrunner() {

		String json = "[\"marcin\",\"josh\"]";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8089/frauds", String.class);

		BDDAssertions.then(entity.getStatusCode().value()).isEqualTo(201);
		BDDAssertions.then(entity.getBody()).isEqualTo(json);
	}

}

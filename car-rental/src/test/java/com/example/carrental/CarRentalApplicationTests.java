package com.example.carrental;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.assertj.core.api.BDDAssertions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock (port = 8081)
@AutoConfigureStubRunner(workOffline = true, ids = "com.example:fraud-service:+:stubs:8089")
@AutoConfigureRestDocs(outputDir = "out/snippets")
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext
public class CarRentalApplicationTests {

	@Autowired private MockMvc mockMvc;

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

	@Test
	public void test_should_return_all_frauds_mockmvc() throws Exception {
		String json = "[\"marcin\",\"josh\"]";

		mockMvc.perform(MockMvcRequestBuilders.post("/frauds")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(WireMockRestDocs.verify()
						.contentType(MediaType.valueOf("application/json"))
						.stub("shouldReturnAListOfFrauds"))
//				.andExpect(status().isOk())
				.andDo(MockMvcRestDocumentation.document("shouldReturnAListOfFrauds",
						SpringCloudContractRestDocs.dslContract()));
	}

}

package com.waracle.cakemgr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.waracle.cakemgr.repository.Cake;
import com.waracle.cakemgr.repository.CakeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URL;

import static org.mockito.BDDMockito.given;

/**
 * Tests for {@link CakeController}
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CakeController.class)
public class CakeControllerTest {

    @MockBean
    public CakeService mockService;

    @Autowired
    private WebTestClient webClient;

    @Nested
    @DisplayName("When a GET request is sent to /cakes")
    public class GetAllCakesTest {


        @Test
        @DisplayName("Given cakes defined in the system"
            + "Then those cakes are returned")
        public void givenCakesInSystem_ReturnsAllCakes() throws Exception {
            // Given
            Cake cake = new Cake(1, "cake-name", "cake-desc", new URL("http://my-hosted-image"));
            Cake anotherCake = new Cake(2, "another-name", "another-desc", new URL("http://another-my-hosted-image"));
            given(mockService.getAllCakes())
                .willReturn(Flux.just(cake, anotherCake));


            // When & Then
            webClient.get()
                     .uri("/cakes")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .json("[" +
                         buildJsonForCake(cake) + ","
                         + buildJsonForCake(anotherCake)
                         + "]");
        }

        @Test
        @DisplayName("Given no cakes defined in the system"
            + "Then an empty array is returned")
        public void givenNoCakesInSystem_ReturnsEmpty() {
            // Given
            given(mockService.getAllCakes())
                .willReturn(Flux.<Cake>empty());


            // When & Then
            webClient.get()
                     .uri("/cakes")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .json("[]");
        }
    }

    @Nested
    @DisplayName("When a POST request is sent to /cakes")
    public class AddCakeTest {

        @Test
        @DisplayName("Given the cake is valid"
            + " Then the newly created cake is returned with created")
        public void givenCakeIsValid_ThenCakeIsReturned() throws Exception {
            // Given
            CakeDto cakeDto = new CakeDto("cake-name", "cake-desc", new URL("http://my-hosted-image"));
            Cake cake = new Cake(1, cakeDto.getName(), cakeDto.getDescription(), cakeDto.getImageUrl());

            given(mockService.addCake(ArgumentMatchers.eq(cakeDto)))
                .willReturn(Mono.just(cake));


            // When & Then
            webClient.post()
                     .uri("/cakes")
                     .contentType(MediaType.APPLICATION_JSON)
                     .bodyValue(cakeDto)
                     .exchange()
                     .expectStatus().isCreated()
                     .expectBody()
                     .json(buildJsonForCake(cake));
        }

        @ParameterizedTest
        @ValueSource(strings = {"name", "description", "imageUrl"})
        @DisplayName("Given the cake is invalid"
            + " Then a 400 is returned")
        public void givenCakeInvalid_400Returned(String fieldToRemove) throws Exception {
            // Given
            ObjectMapper mapper = new ObjectMapper();
            CakeDto cakeDto = new CakeDto("cake-name", "cake-desc", new URL("http://my-hosted-image"));

            ObjectNode cakeJson = (ObjectNode) mapper.readTree(buildRequestJsonForCake(cakeDto));
            cakeJson.remove(fieldToRemove);

            // When & Then
            webClient.post()
                     .uri("/cakes")
                     .contentType(MediaType.APPLICATION_JSON)
                     .bodyValue(cakeJson)
                     .exchange()
                     .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    private String buildRequestJsonForCake(CakeDto cake) {
        return "{ " +
            "    \"name\":\"" + cake.getName() + "\"," +
            "    \"description\":\"" + cake.getDescription() + "\"," +
            "    \"imageUrl\":\"" + cake.getImageUrl() + "\"" +
            "}";
    }

    private String buildJsonForCake(Cake cake) {
        return "{ " +
            "    \"id\":" + cake.getId() + "," +
            "    \"name\":\"" + cake.getName() + "\"," +
            "    \"description\":\"" + cake.getDescription() + "\"," +
            "    \"imageUrl\":\"" + cake.getImageUrl() + "\"" +
            "}";
    }
}

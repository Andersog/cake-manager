package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.CakeTestUtils;
import com.waracle.cakemgr.controllers.CakeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link CakeService}
 */
@DisplayName("Tests for CakeService")
public class CakeServiceTest {

    final static URL resourceUrl = CakeServiceTest.class.getClassLoader().getResource("init.json");
    final static URL invalidJsonUrl = CakeServiceTest.class.getClassLoader().getResource("invalid.json");

    @DisplayName("When init called")
    @Nested
    public class InitTests {
        @Test
        @DisplayName(
            "Then the database table is created")
        public void tableCreated_WhenUrlValid() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);
            given(mockRepo.createTable()).willReturn(Mono.empty());
            given(mockRepo.save(any())).willReturn(Mono.empty());

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            serviceUnderTest.init();

            // Then
            verify(mockRepo, times(1)).createTable();
        }

        @Test
        @DisplayName(
            "Given URL valid" +
                " Then cakes are loaded from URL")
        public void cakesLoadedFromURL_WhenUrlValid() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);
            given(mockRepo.createTable()).willReturn(Mono.empty());
            given(mockRepo.save(cakeCaptor.capture())).willReturn(Mono.empty());

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            serviceUnderTest.init();

            // Then
            verify(mockRepo, times(2)).save(any());

            List<Cake> capturedCakes = cakeCaptor.getAllValues();
            assertTrue(capturedCakes.contains(CakeTestUtils.Initialisation.CAKE_A));
            assertTrue(capturedCakes.contains(CakeTestUtils.Initialisation.CAKE_B));
        }

        @Test
        @DisplayName(
            "Given invalid URL" +
                " Then an error is thrown")
        public void cakesThrowsError_WhenUrlInvalid() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);
            given(mockRepo.createTable()).willReturn(Mono.empty());
            given(mockRepo.save(cakeCaptor.capture())).willReturn(Mono.empty());

            CakeService serviceUnderTest = new CakeService("file:///an-invalid-value", mockRepo);

            // When & Then
            assertThrows(InitilisationException.class, serviceUnderTest::init);
        }

        @Test
        @DisplayName(
            "Given invalid JSON" +
                " Then an error is thrown")
        public void cakesThrowsError_WhenInvalidJson() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);
            given(mockRepo.createTable()).willReturn(Mono.empty());
            given(mockRepo.save(cakeCaptor.capture())).willReturn(Mono.empty());

            CakeService serviceUnderTest = new CakeService("file:///an-invalid-value", mockRepo);

            // When & Then
            assertThrows(InitilisationException.class, serviceUnderTest::init);
        }
    }

    @DisplayName("When getAllCakes called")
    @Nested
    public class GetAllCakesTest {
        @Test
        @DisplayName("Given cakes exist in repo"
            + " Then all cakes returned")
        public void cakesExistInRepo_ThenReturned() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            List<Cake> cakes = Arrays.asList(
                new Cake("name", "description", new URL("http://url")),
                new Cake("other-name", "other-description", new URL("http://other-url"))
            );

            given(mockRepo.findAll()).willReturn(Flux.fromIterable(cakes));

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            Flux<Cake> result = serviceUnderTest.getAllCakes();

            // Then
            StepVerifier
                .create(result)
                .expectNext(cakes.get(0), cakes.get(1))
                .verifyComplete();
        }

        @Test
        @DisplayName("Given no cakes exist in repo"
            + " Then empty returned")
        public void noCakesExistInRepo_ThenEmptyReturned() {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);
            given(mockRepo.findAll()).willReturn(Flux.empty());

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            Flux<Cake> result = serviceUnderTest.getAllCakes();

            // Then
            StepVerifier
                .create(result)
                .verifyComplete();
        }
    }

    @DisplayName("When addCake called")
    @Nested
    public class AddCakeTest {
        @Test
        @DisplayName("Then the cake is inserted into the repo")
        public void cakeIsInsertedIntoRepo() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            CakeDto cakeToSave = new CakeDto("name", "description", new URL("http://url"));

            ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);
            given(mockRepo.save(cakeCaptor.capture())).willReturn(Mono.empty());

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            serviceUnderTest.addCake(cakeToSave).block();

            // Then
            verify(mockRepo, times(1)).save(any());
            assertEquals(
                new Cake(cakeToSave.getName(), cakeToSave.getDescription(), cakeToSave.getImageUrl()),
                cakeCaptor.getValue());
        }

        @Test
        @DisplayName("Then the created cake is returned")
        public void createdCakeReturned() throws Exception {
            // Given
            CakeRepository mockRepo = Mockito.mock(CakeRepository.class);

            Cake cakeToReturn = new Cake(1, "name", "description", new URL("http://url"));
            given(mockRepo.save(any())).willReturn(Mono.just(cakeToReturn));

            CakeService serviceUnderTest = new CakeService(resourceUrl.toString(), mockRepo);

            // When
            Mono<Cake> result = serviceUnderTest.addCake(new CakeDto("name", "description", new URL("http://test")));

            // Then
            StepVerifier
                .create(result)
                .expectNext(cakeToReturn)
                .verifyComplete();
        }
    }
}

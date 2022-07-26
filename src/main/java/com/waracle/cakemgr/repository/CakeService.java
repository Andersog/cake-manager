package com.waracle.cakemgr.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.controllers.CakeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Service layer for performing conversions between the controller and repository.
 */
@Service
public class CakeService {

    private final String initialisationUrl;

    /**
     * The repository to save cakes to.
     *
     * <p>
     * Injected by spring.
     * </p>
     */
    private final CakeRepository repository;

    @Autowired
    public CakeService(CakeRepository repo) {
        this(CakeService.class.getClassLoader().getResource("init.json").toString(), repo);
    }

    public CakeService(String initialisationUrl, CakeRepository repo) {
        this.initialisationUrl = initialisationUrl;
        this.repository = repo;
    }

    /**
     * Gets all cakes in the repo.
     *
     * @return All cakes, or empty.
     */
    public Flux<Cake> getAllCakes() {
        return this.repository.findAll();
    }

    /**
     * Adds a cake to the system.
     *
     * <p>
     * Auto assigns a new ID.
     * </p>
     *
     * @param cake The cake to add.
     * @return The newly created cake entity.
     */
    public Mono<Cake> addCake(CakeDto cake) {
        return Mono.just(cake).map(CakeService::fromDto).flatMap(this.repository::save);
    }

    /**
     * Gets a cake entity from a request DTO.
     *
     * <p>
     * Auto assigns a new, unique ID for the cake.
     * </p>
     *
     * @param dto The request DTO which to get a cake entity for.
     * @return The Cake entity.
     */
    private static Cake fromDto(CakeDto dto) {
        return new Cake(
            null,
            dto.getName(),
            dto.getDescription(),
            dto.getImageUrl()
        );
    }

    /**
     * Initialise the application.
     *
     * @throws InitilisationException Any error is thrown loading the initialisation context.
     */
    @PostConstruct
    public void init() throws InitilisationException {
        // Create our table
        this.repository.createTable().block();

        System.out.println("downloading cake json");
        try (InputStream inputStream = new URL(this.initialisationUrl).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }

            System.out.println("parsing cake json");
            JsonParser parser = new JsonFactory().createParser(buffer.toString());
            if (JsonToken.START_ARRAY != parser.nextToken()) {
                throw new Exception("bad token");
            }

            JsonToken nextToken = parser.nextToken();
            while (nextToken == JsonToken.START_OBJECT) {
                System.out.println("creating cake entity");

                System.out.println(parser.nextFieldName());
                String name = parser.nextTextValue();

                System.out.println(parser.nextFieldName());
                String description = parser.nextTextValue();

                System.out.println(parser.nextFieldName());
                String image = parser.nextTextValue();

                this.addCake(new CakeDto(name, description, new URL(image))).block();

                nextToken = parser.nextToken();
                System.out.println(nextToken);

                nextToken = parser.nextToken();
                System.out.println(nextToken);
            }

        } catch (Exception ex) {
            throw new InitilisationException("An error occurred initialising the database.", ex);
        }

        System.out.println("init finished");
    }
}

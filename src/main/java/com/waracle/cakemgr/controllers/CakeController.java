package com.waracle.cakemgr.controllers;

import com.waracle.cakemgr.repository.Cake;
import com.waracle.cakemgr.repository.CakeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Controller class for adding and retrieving cakes.
 */
@RestController
@Tag(name = "Main cake API", description = "Provides functionality for adding and retrieving cakes in the system.")
@RequestMapping("/cakes")
@RequiredArgsConstructor
public class CakeController {

    /**
     * Our service layer for saving and getting cakes.
     *
     * <p>
     * Injected by spring.
     * </p>
     */
    private final CakeService service;

    /**
     * Gets all cakes on the system.
     *
     * @return All cakes on the system, or empty if none exist.
     */
    @GetMapping
    @Operation(description = "Gets all the cakes currently stored in the system.")
    public Flux<Cake> getAllCakes() {
        return this.service.getAllCakes();
    }

    /**
     * Adds a cake to the system.
     *
     * @return The newly created cake.
     */
    @PostMapping
    @Operation(description = "Adds a new cake to the system")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cake> addCake(@RequestBody @Valid CakeDto cake) {
        return this.service.addCake(cake);
    }

}

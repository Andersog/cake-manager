package com.waracle.cakemgr.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Simple repository for storing our cakes.
 */
public interface CakeRepository extends ReactiveCrudRepository<Cake, Integer> {

    /**
     * Creates the empty table.
     *
     * @return A void mono.
     */
    @Query("CREATE TABLE IF NOT EXISTS CAKE " +
        "(" +
        "ID int NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
        "NAME VARCHAR(255), " +
        "DESCRIPTION VARCHAR(255)," +
        " IMAGE_URL VARCHAR(255)" +
        ");")
    Mono<Void> createTable();
}

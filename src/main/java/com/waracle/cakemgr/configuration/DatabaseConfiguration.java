package com.waracle.cakemgr.configuration;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.Collections;
import java.util.List;

/**
 * Configuration for our H2 database connection.
 */
@EnableR2dbcRepositories
@Configuration
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    /**
     * Creates the connection factory bean.
     *
     * @return The connection factory bean for our database.
     */
    @Bean
    public H2ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
            H2ConnectionConfiguration.builder()
                                     .url("mem:testdb;DB_CLOSE_DELAY=-1;")
                                     .username("sa")
                                     .build()
        );
    }

    @Override
    protected List<Object> getCustomConverters() {
        return Collections.singletonList(new UrlConverter());
    }
}
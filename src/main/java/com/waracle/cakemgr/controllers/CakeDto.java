package com.waracle.cakemgr.controllers;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * DTO for creating our cakes.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CakeDto {

    /**
     * The name of our cake.
     */
    @NotNull
    @NotBlank
    @Getter
    private String name;

    /**
     * The description of our cake.
     */
    @NotNull
    @NotBlank
    @Getter
    private String description;

    /**
     * The URL leading to an image of the cake..
     */
    @NotNull
    @Getter
    private URL imageUrl;
}

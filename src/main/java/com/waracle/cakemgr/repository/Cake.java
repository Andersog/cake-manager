package com.waracle.cakemgr.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.waracle.cakemgr.controllers.CakeDto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

import java.net.URL;

/**
 * A full cake entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cake extends CakeDto implements Persistable<Integer> {
    /**
     * The id of the cake.
     */
    @Getter
    @Id
    Integer id;

    /**
     * Creates a new instance.
     *
     * @param id The id of the cake.
     * @param name The name of the cake.
     * @param description The description of the cake.
     * @param imageUrl A url leading to an image of the cake.
     */
    public Cake(Integer id, String name, String description, URL imageUrl) {
        super(name, description, imageUrl);
        this.id = id;
    }

    public Cake(String name, String description, URL imageUrl) {
        this(null, name, description, imageUrl);
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }
}

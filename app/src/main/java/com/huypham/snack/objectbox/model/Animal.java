package com.huypham.snack.objectbox.model;

import java.util.UUID;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 01/02/2018.
 */

@Entity
public class Animal {
    @Id
    private long id;
    private String name;
    private String description;

    public Animal() {
    }

    public Animal(String name, String description) {
        this.id = Long.parseLong(UUID.randomUUID().toString());
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

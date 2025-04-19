package com.anavasynth.lab5http.model;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  Item
  version 1.0.0
  @since 19.04.2025 - 19.15
*/

public class Item {
    private final Long id;
    private final String name;
    private final String code;
    private final String description;

    // Constructor
    public Item(Long id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    // Getters only (no setters needed if fields are final)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

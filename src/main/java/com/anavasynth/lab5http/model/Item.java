package com.anavasynth.lab5http.model;

/* 
  @author  Anavasynth
  @project  IntelliJ IDEA
  @class  Item
  version 1.0.0
  @since 19.04.2025 - 19.15
*/

public class Item {
    private Long id;
    private String name;
    private String code;
    private String description;

    // Constructors
    public Item() {}

    public Item(Long id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.politechnika.visitservice.model;

import org.springframework.data.annotation.Id;



public class Medicine {

    @Id
    private String id;
    private String name;
    private String contraindications;
    private Integer price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}


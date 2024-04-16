package com.example.lures;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class LureRequest {

    private Integer id;

    @NotBlank
    private String product;

    @NotBlank
    private String company;

    @Positive
    private double size;

    @Positive
    private double weight;

    public LureRequest(Integer id, String product, String company, double size, double weight) {
        this.id = id;
        this.product = product;
        this.company = company;
        this.size = size;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public String getCompany() {
        return company;
    }

    public double getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }
}

package com.example.lures;

public class Lures {

    private Integer id;

    private String product;

    private String company;

    private double size;

    private double weight;

    public Lures(Integer id, String product, String company, double size, double weight) {
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

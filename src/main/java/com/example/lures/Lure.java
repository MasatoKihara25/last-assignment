package com.example.lures;

import java.util.Objects;

public class Lure {

    private Integer id;

    private String product;

    private String company;

    private double size;

    private double weight;

    public Lure(Integer id, String product, String company, double size, double weight) {
        this.id = id;
        this.product = product;
        this.company = company;
        this.size = size;
        this.weight = weight;
    }

    public Lure(String product, String company, double size, double weight) {
        this.id = null;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lure lure = (Lure) o;
        return Objects.equals(id, lure.id) &&
                Objects.equals(product, lure.product) &&
                Objects.equals(company, lure.company) &&
                Objects.equals(size, lure.size) &&
                Objects.equals(weight, lure.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, company, size, weight);
    }
}

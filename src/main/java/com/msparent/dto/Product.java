package com.msparent.dto;

public class Product {
    private int id;
    private double price;
    private boolean isSold;

    public Product(int id, double price) {
        this.id = id;
        this.price = price;
        this.isSold = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}

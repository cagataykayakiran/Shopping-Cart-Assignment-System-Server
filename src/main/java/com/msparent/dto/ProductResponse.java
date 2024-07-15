package com.msparent.dto;

public class ProductResponse {
    private int id;
    private boolean isSold;

    public ProductResponse(int id, boolean isSold) {
        this.id = id;
        this.isSold = isSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}

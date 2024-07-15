package com.msparent.dto;

import java.util.List;

public class CartRequest {
    private double cartLimit;
    private List<ProductRequest> products;

    public double getCartLimit() {
        return cartLimit;
    }

    public void setCartLimit(double cartLimit) {
        this.cartLimit = cartLimit;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }
}

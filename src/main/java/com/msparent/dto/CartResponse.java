package com.msparent.dto;

import java.util.List;

public class CartResponse {
    private List<ProductResponse> cart;

    public List<ProductResponse> getCart() {
        return cart;
    }

    public void setCart(List<ProductResponse> cart) {
        this.cart = cart;
    }
}

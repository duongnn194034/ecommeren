package com.example.ecommerce.Dto.Cart;

import com.example.ecommerce.Model.CartItem;
import com.example.ecommerce.Model.Product;
import com.sun.istack.NotNull;

public class CartItemDto {
    private long id;
    private @NotNull Integer quantity;
    private @NotNull Product product;

    public CartItemDto() {
    }

    public CartItemDto(CartItem cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.product = cart.getProduct();
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", name=" + product.getName() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

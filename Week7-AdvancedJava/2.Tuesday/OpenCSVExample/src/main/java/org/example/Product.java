package org.example;

import com.opencsv.bean.CsvBindByName;

import java.math.BigDecimal;

public class Product {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName(column = "quantity")
    private int quantity;

    public Product() {
    }

    public Product(String name, BigDecimal price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

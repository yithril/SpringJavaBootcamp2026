package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Product> products =
                ProductFileManager.getProducts();

        System.out.println("=== PRODUCTS FROM CSV ===");

        for(Product product : products) {
            System.out.println(
                    product.getName() +
                            " | $" + product.getPrice() +
                            " | Qty: " + product.getQuantity()
            );
        }

        // ADD NEW PRODUCT
        products.add(
                new Product(
                        "Headphones",
                        new BigDecimal("79.99"),
                        4
                )
        );

        // WRITE UPDATED LIST BACK TO CSV
        ProductFileManager.writeProducts(products);

        System.out.println();
        System.out.println("New product added and file updated.");
    }
}
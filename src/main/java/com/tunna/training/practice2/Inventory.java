package com.tunna.training.practice2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Inventory {

    private final List<Product> products = new ArrayList<>();

    public void add(Product product) {
        boolean duplicate = products.stream().anyMatch(p -> p.getCode().equals(product.getCode()));
        if (duplicate) {
            throw new IllegalArgumentException("duplicate product code: " + product.getCode());
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public int totalQuantity(Category category) {
        return products.stream()
                .filter(p -> p.getCategory() == category)
                .mapToInt(Product::getQuantity)
                .sum();
    }

    public double totalVat(Category category) {
        return products.stream()
                .filter(p -> p.getCategory() == category)
                .mapToDouble(Product::getVatAmount)
                .sum();
    }

    public Map<Category, Integer> quantityByCategory() {
        Map<Category, Integer> result = new EnumMap<>(Category.class);
        for (Category category : Category.values()) {
            result.put(category, totalQuantity(category));
        }
        return result;
    }

    public Map<Category, Double> vatByCategory() {
        Map<Category, Double> result = new EnumMap<>(Category.class);
        for (Category category : Category.values()) {
            result.put(category, totalVat(category));
        }
        return result;
    }
}

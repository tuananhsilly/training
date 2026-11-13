package com.tunna.training.practice2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

// Goods list management (DSHH), backed by an array that grows when full
public class Inventory {

    private static final int INITIAL_CAPACITY = 10;

    private Product[] products = new Product[INITIAL_CAPACITY];
    private int size;

    // Returns false (and adds nothing) when the product code already exists
    public boolean add(Product product) {
        if (product == null || findByCode(product.getCode()) != null) {
            return false;
        }
        if (size == products.length) {
            products = Arrays.copyOf(products, products.length * 2);
        }
        products[size++] = product;
        return true;
    }

    public Product findByCode(String code) {
        for (int i = 0; i < size; i++) {
            if (products[i].getCode().equalsIgnoreCase(code)) {
                return products[i];
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, size);
    }

    public int totalQuantity(Category category) {
        int total = 0;
        for (int i = 0; i < size; i++) {
            if (products[i].getCategory() == category) {
                total += products[i].getQuantity();
            }
        }
        return total;
    }

    public double totalVat(Category category) {
        double total = 0;
        for (int i = 0; i < size; i++) {
            if (products[i].getCategory() == category) {
                total += products[i].getVatAmount();
            }
        }
        return total;
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

    public Product[] findByConsumption(ConsumptionStatus status, LocalDate today) {
        Product[] matches = new Product[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (products[i].evaluateConsumption(today) == status) {
                matches[count++] = products[i];
            }
        }
        return Arrays.copyOf(matches, count);
    }
}

package com.tunna.training.practice2;

import java.time.LocalDate;

public class SupermarketApp {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.add(new Food("F01", "Fresh milk", 120, 1.5,
                LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 20), "Vinamilk"));
        inventory.add(new Food("F02", "Instant noodles", 300, 0.4,
                LocalDate.of(2026, 9, 15), LocalDate.of(2027, 3, 15), "Acecook"));
        inventory.add(new Electronics("E01", "Rice cooker", 15, 45.0, 12, 0.7));
        inventory.add(new Electronics("E02", "Air conditioner", 5, 420.0, 24, 2.6));
        inventory.add(new Crockery("C01", "Porcelain bowl", 200, 2.0,
                "Minh Long", LocalDate.of(2026, 10, 5)));

        System.out.println("== Products ==");
        // Polymorphism: toString() calls the subclass getCategory()/getDetails()
        for (Product product : inventory.getProducts()) {
            System.out.println(product);
        }

        System.out.println();
        System.out.println("== Manager report ==");
        for (Category category : Category.values()) {
            System.out.printf("%-12s quantity=%-5d VAT(%.0f%%)=%.2f%n",
                    category.getLabel(), inventory.totalQuantity(category),
                    category.getVatRate() * 100, inventory.totalVat(category));
        }
    }
}

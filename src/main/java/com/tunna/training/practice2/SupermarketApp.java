package com.tunna.training.practice2;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SupermarketApp {

    private final Scanner in;
    private final PrintStream out;
    private final Inventory inventory;
    private final LocalDate today;

    public SupermarketApp(Scanner in, PrintStream out, Inventory inventory, LocalDate today) {
        this.in = in;
        this.out = out;
        this.inventory = inventory;
        this.today = today;
    }

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        Inventory inventory = new Inventory();
        seed(inventory, today);
        new SupermarketApp(new Scanner(System.in), System.out, inventory, today).run();
    }

    // Sample data chosen so that every consumption status shows up
    static void seed(Inventory inventory, LocalDate today) {
        inventory.add(new Food("F01", "Fresh milk", 120, 1.5,
                today.minusDays(5), today.plusDays(10), "Vinamilk"));
        inventory.add(new Food("F02", "Yogurt", 30, 0.8,
                today.minusDays(20), today.minusDays(1), "TH True Milk"));
        inventory.add(new Electronics("E01", "Rice cooker", 15, 45.0, 12, 0.7));
        inventory.add(new Electronics("E02", "Air conditioner", 2, 420.0, 24, 2.6));
        inventory.add(new Crockery("C01", "Porcelain bowl", 200, 2.0, "Minh Long", today.minusDays(20)));
        inventory.add(new Crockery("C02", "Glass cup", 40, 1.2, "Luminarc", today.minusDays(30)));
    }

    public void run() {
        try {
            while (true) {
                out.println();
                out.println("===== SUPERMARKET INVENTORY =====");
                out.println("1. List goods");
                out.println("2. Add goods");
                out.println("3. Quantity & VAT report");
                out.println("4. Consumption evaluation");
                out.println("0. Exit");
                switch (readInt("Choose: ", 0, 4)) {
                    case 1 -> listProducts();
                    case 2 -> addProduct();
                    case 3 -> printReport();
                    case 4 -> printConsumption();
                    default -> {
                        out.println("Bye!");
                        return;
                    }
                }
            }
        } catch (NoSuchElementException endOfInput) {
            out.println("Input closed, exiting.");
        }
    }

    private void listProducts() {
        if (inventory.size() == 0) {
            out.println("No goods yet.");
            return;
        }
        for (Product product : inventory.getProducts()) {
            out.println(product);
        }
    }

    void addProduct() {
        out.println("Type of goods: 1. Food  2. Electronics  3. Crockery");
        int type = readInt("Choose type: ", 1, 3);

        String code = readText("Product code: ");
        if (inventory.findByCode(code) != null) {
            out.println("Failed: product code " + code + " already exists.");
            return;
        }
        String name = readText("Name: ");
        int quantity = readInt("Quantity (>= 0): ", 0, Integer.MAX_VALUE);
        double price = readDouble("Unit price (>= 0): ");

        try {
            Product product = switch (type) {
                case 1 -> new Food(code, name, quantity, price,
                        readDate("Manufacture date (yyyy-MM-dd): "),
                        readDate("Expiry date (yyyy-MM-dd): "),
                        readText("Supplier: "));
                case 2 -> new Electronics(code, name, quantity, price,
                        readInt("Warranty months (>= 0): ", 0, Integer.MAX_VALUE),
                        readDouble("Capacity KW (>= 0): "));
                default -> new Crockery(code, name, quantity, price,
                        readText("Manufacturer: "),
                        readDate("Arrival date (yyyy-MM-dd): "));
            };
            out.println(inventory.add(product) ? "Added: " + product : "Failed: duplicate product code.");
        } catch (IllegalArgumentException e) {
            out.println("Failed: " + e.getMessage());
        }
    }

    private void printReport() {
        for (Category category : Category.values()) {
            out.printf("%-12s quantity=%-5d VAT(%.0f%%)=%.2f%n",
                    category.getLabel(), inventory.totalQuantity(category),
                    category.getVatRate() * 100, inventory.totalVat(category));
        }
    }

    private void printConsumption() {
        out.println("Evaluated on " + today);
        // Polymorphism: each product applies the rule of its own class
        for (Product product : inventory.getProducts()) {
            out.printf("%-5s %-18s %-12s -> %s%n", product.getCode(), product.getName(),
                    product.getCategory().getLabel(), product.evaluateConsumption(today).getLabel());
        }
    }

    private String readText(String prompt) {
        while (true) {
            out.print(prompt);
            String line = in.nextLine().strip();
            if (!line.isEmpty()) {
                return line;
            }
            out.println("Value must not be empty.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            String line = readText(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            out.printf("Please enter a whole number from %d to %d.%n", min, max);
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            String line = readText(prompt);
            try {
                double value = Double.parseDouble(line);
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            out.println("Please enter a number >= 0.");
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            String line = readText(prompt);
            try {
                return LocalDate.parse(line);
            } catch (DateTimeParseException e) {
                out.println("Please use the format yyyy-MM-dd.");
            }
        }
    }
}

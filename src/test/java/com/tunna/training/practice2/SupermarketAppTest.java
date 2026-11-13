package com.tunna.training.practice2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class SupermarketAppTest {

    private static String run(Inventory inventory, String... lines) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Scanner in = new Scanner(String.join("\n", lines) + "\n");
        new SupermarketApp(in, new PrintStream(buffer), inventory, LocalDate.of(2026, 11, 13)).run();
        return buffer.toString();
    }

    @Test
    void userChoosesTypeAndAddsGoods() {
        Inventory inventory = new Inventory();
        String output = run(inventory,
                "2", "2", "E10", "Kettle", "4", "25", "12", "1.5",
                "2", "3", "C10", "Plate", "60", "1.5", "Minh Long", "2026-10-01",
                "0");

        assertEquals(2, inventory.size());
        assertInstanceOf(Electronics.class, inventory.findByCode("E10"));
        assertInstanceOf(Crockery.class, inventory.findByCode("C10"));
        assertTrue(output.contains("Added: [Electronics] E10 - Kettle"));
    }

    @Test
    void duplicateCodeIsRejected() {
        Inventory inventory = new Inventory();
        inventory.add(new Electronics("E10", "Kettle", 4, 25, 12, 1.5));
        String output = run(inventory, "2", "1", "E10", "0");

        assertEquals(1, inventory.size());
        assertTrue(output.contains("Failed: product code E10 already exists."));
    }

    @Test
    void invalidInputIsAskedAgain() {
        Inventory inventory = new Inventory();
        String output = run(inventory,
                "2", "1", "F10", "Bread", "-3", "5", "abc", "1",
                "2026-11-10", "2026-11-01", "Bakery", "0");

        assertEquals(0, inventory.size());
        assertTrue(output.contains("Please enter a whole number from 0 to"));
        assertTrue(output.contains("Please enter a number >= 0."));
        assertTrue(output.contains("Failed: expiry date must be on or after manufacture date"));
    }
}

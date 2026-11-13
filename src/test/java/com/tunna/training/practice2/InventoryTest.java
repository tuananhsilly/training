package com.tunna.training.practice2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InventoryTest {

    private static final double DELTA = 1e-9;
    private static final LocalDate DAY = LocalDate.of(2026, 11, 1);

    private static Food food(String code, int qty, double price) {
        return new Food(code, "Milk", qty, price, DAY, DAY.plusDays(10), "Supplier");
    }

    @Test
    void vatRateDependsOnCategory() {
        assertEquals(0.05, food("F1", 1, 1).getVatRate(), DELTA);
        assertEquals(0.10, new Electronics("E1", "Fan", 1, 1, 6, 0.1).getVatRate(), DELTA);
        assertEquals(0.10, new Crockery("C1", "Cup", 1, 1, "Maker", DAY).getVatRate(), DELTA);
    }

    @Test
    void vatAmountIsOnInventoryValue() {
        Product milk = food("F1", 10, 2.0);
        assertEquals(20.0, milk.getInventoryValue(), DELTA);
        assertEquals(1.0, milk.getVatAmount(), DELTA);
    }

    @Test
    void reportsQuantityAndVatPerCategory() {
        Inventory inventory = new Inventory();
        inventory.add(food("F1", 10, 2.0));
        inventory.add(food("F2", 5, 4.0));
        inventory.add(new Electronics("E1", "Fan", 3, 100.0, 12, 0.05));
        inventory.add(new Crockery("C1", "Bowl", 20, 1.0, "Maker", DAY));

        assertEquals(15, inventory.totalQuantity(Category.FOOD));
        assertEquals(3, inventory.totalQuantity(Category.ELECTRONICS));
        assertEquals(20, inventory.totalQuantity(Category.CROCKERY));

        assertEquals(2.0, inventory.totalVat(Category.FOOD), DELTA);
        assertEquals(30.0, inventory.totalVat(Category.ELECTRONICS), DELTA);
        assertEquals(2.0, inventory.totalVat(Category.CROCKERY), DELTA);
    }

    @Test
    void emptyCategoryHasZeroTotals() {
        Inventory inventory = new Inventory();
        assertEquals(0, inventory.quantityByCategory().get(Category.FOOD));
        assertEquals(0.0, inventory.vatByCategory().get(Category.CROCKERY), DELTA);
    }

    @Test
    void foodExpiryMayEqualManufactureDateButNotPrecedeIt() {
        Food sameDay = new Food("F1", "Bread", 1, 1, DAY, DAY, "Bakery");
        assertFalse(sameDay.isExpired(DAY));
        assertTrue(sameDay.isExpired(DAY.plusDays(1)));
        assertThrows(IllegalArgumentException.class,
                () -> new Food("F2", "Bread", 1, 1, DAY, DAY.minusDays(1), "Bakery"));
    }

    @Test
    void rejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> food("F1", -1, 1));
        assertThrows(IllegalArgumentException.class, () -> new Electronics("E1", "Fan", 1, 1, -1, 0.1));
        assertThrows(IllegalArgumentException.class, () -> new Electronics("E1", "Fan", 1, 1, 1, -0.1));
        assertThrows(IllegalArgumentException.class, () -> food("F1", 1, 1).setQuantity(-5));
    }

    @Test
    void addFailsForDuplicateCode() {
        Inventory inventory = new Inventory();
        assertTrue(inventory.add(food("F1", 1, 1)));
        assertFalse(inventory.add(food("F1", 2, 2)));
        assertFalse(inventory.add(food("f1", 2, 2)));
        assertEquals(1, inventory.size());
    }

    @Test
    void arrayGrowsBeyondInitialCapacity() {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 25; i++) {
            assertTrue(inventory.add(food("F" + i, 1, 1)));
        }
        assertEquals(25, inventory.size());
        assertEquals(25, inventory.getProducts().length);
        assertEquals(25, inventory.totalQuantity(Category.FOOD));
    }
}

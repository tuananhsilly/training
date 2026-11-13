package com.tunna.training.practice2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ConsumptionTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 11, 13);

    private static Electronics electronics(int qty) {
        return new Electronics("E1", "Fan", qty, 10, 12, 0.05);
    }

    private static Food food(int qty, LocalDate expiry) {
        return new Food("F1", "Milk", qty, 1, expiry.minusDays(30), expiry, "Supplier");
    }

    private static Crockery crockery(int qty, LocalDate arrival) {
        return new Crockery("C1", "Bowl", qty, 1, "Maker", arrival);
    }

    @Test
    void electronicsWithFewerThanThreeLeftAreSold() {
        assertEquals(ConsumptionStatus.SOLD, electronics(0).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.SOLD, electronics(2).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.NOT_EVALUATED, electronics(3).evaluateConsumption(TODAY));
    }

    @Test
    void expiredFoodStillInStockIsHardToSell() {
        assertEquals(ConsumptionStatus.HARD_TO_SELL, food(5, TODAY.minusDays(1)).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.NOT_EVALUATED, food(0, TODAY.minusDays(1)).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.NOT_EVALUATED, food(5, TODAY).evaluateConsumption(TODAY));
    }

    @Test
    void crockeryOverFiftyStoredOverTenDaysIsSlowSale() {
        assertEquals(ConsumptionStatus.SLOW_SALE, crockery(51, TODAY.minusDays(11)).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.NOT_EVALUATED, crockery(50, TODAY.minusDays(11)).evaluateConsumption(TODAY));
        assertEquals(ConsumptionStatus.NOT_EVALUATED, crockery(51, TODAY.minusDays(10)).evaluateConsumption(TODAY));
    }

    @Test
    void inventoryFiltersByConsumptionStatus() {
        Inventory inventory = new Inventory();
        SupermarketApp.seed(inventory, TODAY);
        assertEquals("E02", inventory.findByConsumption(ConsumptionStatus.SOLD, TODAY)[0].getCode());
        assertEquals("F02", inventory.findByConsumption(ConsumptionStatus.HARD_TO_SELL, TODAY)[0].getCode());
        assertEquals("C01", inventory.findByConsumption(ConsumptionStatus.SLOW_SALE, TODAY)[0].getCode());
        assertEquals(3, inventory.findByConsumption(ConsumptionStatus.NOT_EVALUATED, TODAY).length);
    }
}

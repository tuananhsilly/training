package com.tunna.training.practice2;

import java.time.LocalDate;

public class Food extends Product {

    private final LocalDate manufactureDate;
    private final LocalDate expiryDate;
    private final String supplier;

    public Food(String code, String name, int quantity, double unitPrice,
                LocalDate manufactureDate, LocalDate expiryDate, String supplier) {
        super(code, name, quantity, unitPrice);
        if (manufactureDate == null || expiryDate == null) {
            throw new IllegalArgumentException("manufacture and expiry dates are required");
        }
        if (expiryDate.isBefore(manufactureDate)) {
            throw new IllegalArgumentException("expiry date must be on or after manufacture date");
        }
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public boolean isExpired(LocalDate today) {
        return today.isAfter(expiryDate);
    }

    @Override
    public Category getCategory() {
        return Category.FOOD;
    }

    @Override
    public ConsumptionStatus evaluateConsumption(LocalDate today) {
        return getQuantity() > 0 && isExpired(today)
                ? ConsumptionStatus.HARD_TO_SELL
                : ConsumptionStatus.NOT_EVALUATED;
    }

    @Override
    protected String getDetails() {
        return String.format("MFG=%s, EXP=%s, supplier=%s", manufactureDate, expiryDate, supplier);
    }
}

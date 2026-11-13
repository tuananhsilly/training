package com.tunna.training.practice2;

public class Electronics extends Product {

    private final int warrantyMonths;
    private final double capacityKw;

    public Electronics(String code, String name, int quantity, double unitPrice,
                       int warrantyMonths, double capacityKw) {
        super(code, name, quantity, unitPrice);
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("warranty months must be >= 0: " + warrantyMonths);
        }
        if (capacityKw < 0) {
            throw new IllegalArgumentException("capacity must be >= 0: " + capacityKw);
        }
        this.warrantyMonths = warrantyMonths;
        this.capacityKw = capacityKw;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public double getCapacityKw() {
        return capacityKw;
    }

    @Override
    public Category getCategory() {
        return Category.ELECTRONICS;
    }

    @Override
    protected String getDetails() {
        return String.format("warranty=%d months, capacity=%.2f KW", warrantyMonths, capacityKw);
    }
}

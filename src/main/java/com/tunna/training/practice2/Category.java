package com.tunna.training.practice2;

public enum Category {
    FOOD("Food", 0.05),
    CROCKERY("Crockery", 0.10),
    ELECTRONICS("Electronics", 0.10);

    private final String label;
    private final double vatRate;

    Category(String label, double vatRate) {
        this.label = label;
        this.vatRate = vatRate;
    }

    public String getLabel() {
        return label;
    }

    public double getVatRate() {
        return vatRate;
    }
}

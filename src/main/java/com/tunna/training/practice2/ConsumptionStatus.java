package com.tunna.training.practice2;

public enum ConsumptionStatus {
    SOLD("Sold"),
    HARD_TO_SELL("Hard to sell"),
    SLOW_SALE("Slow sale"),
    NOT_EVALUATED("Not evaluated");

    private final String label;

    ConsumptionStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package com.tunna.training.practice2;

import java.time.LocalDate;

public class Crockery extends Product {

    private final String manufacturer;
    private final LocalDate arrivalDate;

    public Crockery(String code, String name, int quantity, double unitPrice,
                    String manufacturer, LocalDate arrivalDate) {
        super(code, name, quantity, unitPrice);
        if (arrivalDate == null) {
            throw new IllegalArgumentException("arrival date is required");
        }
        this.manufacturer = manufacturer;
        this.arrivalDate = arrivalDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public Category getCategory() {
        return Category.CROCKERY;
    }

    @Override
    protected String getDetails() {
        return String.format("manufacturer=%s, arrived=%s", manufacturer, arrivalDate);
    }
}

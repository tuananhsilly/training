package com.tunna.training.practice3;

public class Truck extends Vehicle {

    private final double tonnage;

    public Truck(String number, Manufacturer manufacturer, int year, String color, Owner owner,
                 double tonnage) {
        super(number, manufacturer, year, color, owner);
        if (tonnage <= 0) {
            throw new IllegalArgumentException("tonnage must be positive: " + tonnage);
        }
        this.tonnage = tonnage;
    }

    public double getTonnage() {
        return tonnage;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.TRUCK;
    }

    @Override
    protected String getDetails() {
        return String.format("tonnage=%.1f t", tonnage);
    }
}

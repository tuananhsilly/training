package com.tunna.training.practice3;

public class Motorbike extends Vehicle {

    private final double capacity;

    public Motorbike(String number, Manufacturer manufacturer, int year, String color, Owner owner,
                     double capacity) {
        super(number, manufacturer, year, color, owner);
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.MOTORBIKE;
    }

    @Override
    protected String getDetails() {
        return String.format("capacity=%.0f cc", capacity);
    }
}

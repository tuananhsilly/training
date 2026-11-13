package com.tunna.training.practice3;

public enum VehicleType {
    CAR("Car"),
    MOTORBIKE("Motorbike"),
    TRUCK("Truck");

    private final String label;

    VehicleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

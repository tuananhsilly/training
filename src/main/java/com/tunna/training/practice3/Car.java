package com.tunna.training.practice3;

public class Car extends Vehicle {

    private final int seats;
    private final String engineType;

    public Car(String number, Manufacturer manufacturer, int year, String color, Owner owner,
               int seats, String engineType) {
        super(number, manufacturer, year, color, owner);
        if (seats <= 0) {
            throw new IllegalArgumentException("seats must be positive: " + seats);
        }
        if (engineType == null || engineType.isBlank()) {
            throw new IllegalArgumentException("engine type must not be blank");
        }
        this.seats = seats;
        this.engineType = engineType.strip();
    }

    public int getSeats() {
        return seats;
    }

    public String getEngineType() {
        return engineType;
    }

    @Override
    public VehicleType getType() {
        return VehicleType.CAR;
    }

    @Override
    protected String getDetails() {
        return String.format("seats=%d, engine=%s", seats, engineType);
    }
}

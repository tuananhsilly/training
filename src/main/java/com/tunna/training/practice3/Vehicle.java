package com.tunna.training.practice3;

import java.time.Year;
import java.util.regex.Pattern;

// Shared information of every means of transport; subclasses add their own attributes
public abstract class Vehicle {

    private static final Pattern NUMBER = Pattern.compile("[A-Z0-9]{5}");
    private static final int MIN_YEAR_EXCLUSIVE = 2000;

    private final String number;
    private final Manufacturer manufacturer;
    private final int year;
    private final String color;
    private final Owner owner;

    protected Vehicle(String number, Manufacturer manufacturer, int year, String color, Owner owner) {
        this.number = normalizeNumber(number);
        if (manufacturer == null) {
            throw new IllegalArgumentException("manufacturer is required");
        }
        int currentYear = Year.now().getValue();
        if (year <= MIN_YEAR_EXCLUSIVE || year > currentYear) {
            throw new IllegalArgumentException(
                    "year must be > " + MIN_YEAR_EXCLUSIVE + " and <= " + currentYear + ": " + year);
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("color must not be blank");
        }
        if (owner == null) {
            throw new IllegalArgumentException("owner is required");
        }
        this.manufacturer = manufacturer;
        this.year = year;
        this.color = color.strip();
        this.owner = owner;
    }

    public static String normalizeNumber(String number) {
        String normalized = number == null ? "" : number.strip().toUpperCase();
        if (!NUMBER.matcher(normalized).matches()) {
            throw new IllegalArgumentException("vehicle number must be exactly 5 letters/digits: " + number);
        }
        return normalized;
    }

    public abstract VehicleType getType();

    protected abstract String getDetails();

    public String getNumber() {
        return number;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public Owner getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s, %d, %s | %s | owner: %s",
                getType().getLabel(), number, manufacturer.getLabel(), year, color, getDetails(), owner);
    }
}

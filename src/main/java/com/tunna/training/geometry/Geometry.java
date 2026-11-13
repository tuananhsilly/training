package com.tunna.training.geometry;

public abstract class Geometry {

    private final String name;

    protected Geometry(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double area();

    public abstract double perimeter();

    public String describe() {
        return String.format("%s: area=%.2f, perimeter=%.2f", name, area(), perimeter());
    }

    protected static double requirePositive(double value, String field) {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be positive: " + value);
        }
        return value;
    }
}

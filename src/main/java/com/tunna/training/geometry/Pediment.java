package com.tunna.training.geometry;

// A pediment is modelled as an isosceles triangle: base at the bottom, apex centred above it
public class Pediment extends Geometry {

    private final double base;
    private final double height;

    public Pediment(double base, double height) {
        super("Pediment");
        this.base = requirePositive(base, "base");
        this.height = requirePositive(height, "height");
    }

    public double getBase() {
        return base;
    }

    public double getHeight() {
        return height;
    }

    public double slantSide() {
        return Math.hypot(base / 2, height);
    }

    @Override
    public double area() {
        return base * height / 2;
    }

    @Override
    public double perimeter() {
        return base + 2 * slantSide();
    }
}

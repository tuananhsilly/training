package com.tunna.training.geometry;

public class Circle extends Geometry {

    private final double radius;

    public Circle(double radius) {
        super("Circle");
        this.radius = requirePositive(radius, "radius");
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}

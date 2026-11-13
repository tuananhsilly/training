package com.tunna.training.geometry;

public class Rectangle extends Geometry {

    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this("Rectangle", width, height);
    }

    // Lets subclasses such as Square reuse the fields under their own name
    protected Rectangle(String name, double width, double height) {
        super(name);
        this.width = requirePositive(width, "width");
        this.height = requirePositive(height, "height");
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }
}

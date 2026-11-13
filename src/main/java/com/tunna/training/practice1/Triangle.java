package com.tunna.training.practice1;

// Extension: an isosceles triangle with base = width and height = height
public class Triangle extends Shape {

    public Triangle(double base, double height) {
        super(base, height);
    }

    public double getArea() {
        return width * height / 2;
    }

    public double getPerimeter() {
        return width + 2 * Math.hypot(width / 2, height);
    }

    @Override
    public String getInfo() {
        return String.format("%s, area=%.2f, perimeter=%.2f", super.getInfo(), getArea(), getPerimeter());
    }
}

package com.tunna.training.practice1;

// A circle fits in a width x height box where both equal the diameter
public class Circle extends Shape {

    public Circle(double diameter) {
        super(diameter, diameter);
    }

    public double getDiameter() {
        return width;
    }

    public double getRadius() {
        return width / 2;
    }

    public double getArea() {
        return Math.PI * getRadius() * getRadius();
    }

    public double getCircumference() {
        return getDiameter() * Math.PI;
    }

    @Override
    public String getInfo() {
        return String.format("%s, radius=%.2f, area=%.2f, circumference=%.2f",
                super.getInfo(), getRadius(), getArea(), getCircumference());
    }
}

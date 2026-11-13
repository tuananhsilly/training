package com.tunna.training.practice1;

public class Rectangle extends Shape {

    public Rectangle(double width, double length) {
        super(width, length);
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (height + width);
    }

    @Override
    public String getInfo() {
        return String.format("%s, area=%.2f, perimeter=%.2f", super.getInfo(), getArea(), getPerimeter());
    }
}

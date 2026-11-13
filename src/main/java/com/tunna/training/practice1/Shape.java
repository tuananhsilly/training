package com.tunna.training.practice1;

public class Shape {

    protected double width;
    protected double height;

    public Shape(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getInfo() {
        return String.format("%s [width=%.2f, height=%.2f]", getClass().getSimpleName(), width, height);
    }
}

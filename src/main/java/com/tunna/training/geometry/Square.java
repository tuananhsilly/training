package com.tunna.training.geometry;

// A square is a rectangle with equal sides, so it inherits area() and perimeter()
public class Square extends Rectangle {

    public Square(double side) {
        super("Square", side, side);
    }

    public double getSide() {
        return getWidth();
    }
}

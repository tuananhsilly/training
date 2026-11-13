package com.tunna.training.practice1;

// Extension: a square is a rectangle with equal sides, so it reuses getArea() and getPerimeter()
public class Square extends Rectangle {

    public Square(double side) {
        super(side, side);
    }

    public double getSide() {
        return width;
    }

    public double getDiagonal() {
        return width * Math.sqrt(2);
    }

    @Override
    public String getInfo() {
        return String.format("%s, diagonal=%.2f", super.getInfo(), getDiagonal());
    }
}

package com.tunna.training.practice1;

import java.util.List;

public class ShapeApp {

    public static void main(String[] args) {
        Shape shape = new Shape(3, 4);
        Rectangle rectangle = new Rectangle(5, 2);
        Circle circle = new Circle(10);

        System.out.println("== Practice 1 ==");
        System.out.println(shape.getInfo());
        System.out.println(rectangle.getInfo());
        System.out.println(circle.getInfo());

        System.out.println();
        System.out.println("== Extension ==");
        // Every subclass is-a Shape, so getInfo() dispatches to the overriding version
        List<Shape> shapes = List.of(shape, rectangle, circle, new Square(4), new Triangle(6, 4));
        for (Shape s : shapes) {
            System.out.println(s.getInfo());
        }
    }
}

package com.tunna.training.geometry;

import java.util.List;

public class GeometryDemo {

    public static void main(String[] args) {
        List<Geometry> shapes = List.of(
                new Circle(2),
                new Square(3),
                new Pediment(6, 4),
                new Rectangle(4, 2.5));

        // Polymorphism: each call dispatches to the subclass implementation
        for (Geometry shape : shapes) {
            System.out.println(shape.describe());
        }

        double totalArea = shapes.stream().mapToDouble(Geometry::area).sum();
        System.out.printf("Total area: %.2f%n", totalArea);
    }
}

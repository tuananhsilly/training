package com.tunna.training.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GeometryTest {

    private static final double DELTA = 1e-9;

    @Test
    void circle() {
        Circle circle = new Circle(2);
        assertEquals(4 * Math.PI, circle.area(), DELTA);
        assertEquals(4 * Math.PI, circle.perimeter(), DELTA);
    }

    @Test
    void rectangle() {
        Rectangle rectangle = new Rectangle(4, 2.5);
        assertEquals(10, rectangle.area(), DELTA);
        assertEquals(13, rectangle.perimeter(), DELTA);
    }

    @Test
    void squareInheritsFromRectangle() {
        Square square = new Square(3);
        assertInstanceOf(Rectangle.class, square);
        assertEquals("Square", square.getName());
        assertEquals(9, square.area(), DELTA);
        assertEquals(12, square.perimeter(), DELTA);
    }

    @Test
    void pediment() {
        Pediment pediment = new Pediment(6, 4);
        assertEquals(12, pediment.area(), DELTA);
        assertEquals(5, pediment.slantSide(), DELTA);
        assertEquals(16, pediment.perimeter(), DELTA);
    }

    @Test
    void polymorphicDescribe() {
        Geometry shape = new Square(2);
        assertEquals("Square: area=4.00, perimeter=8.00", shape.describe());
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Circle(0));
        assertThrows(IllegalArgumentException.class, () -> new Square(-1));
        assertThrows(IllegalArgumentException.class, () -> new Pediment(3, 0));
    }
}

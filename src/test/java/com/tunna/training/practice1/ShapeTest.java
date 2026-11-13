package com.tunna.training.practice1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ShapeTest {

    private static final double DELTA = 1e-9;

    @Test
    void shapeStoresWidthAndHeight() {
        Shape shape = new Shape(3, 4);
        assertEquals(3, shape.getWidth(), DELTA);
        assertEquals(4, shape.getHeight(), DELTA);
        assertEquals("Shape [width=3.00, height=4.00]", shape.getInfo());
    }

    @Test
    void rectangleAreaAndPerimeter() {
        Rectangle rectangle = new Rectangle(5, 2);
        assertInstanceOf(Shape.class, rectangle);
        assertEquals(10, rectangle.getArea(), DELTA);
        assertEquals(14, rectangle.getPerimeter(), DELTA);
    }

    @Test
    void circleAreaAndCircumference() {
        Circle circle = new Circle(10);
        assertInstanceOf(Shape.class, circle);
        assertEquals(5, circle.getRadius(), DELTA);
        assertEquals(25 * Math.PI, circle.getArea(), DELTA);
        assertEquals(10 * Math.PI, circle.getCircumference(), DELTA);
    }

    @Test
    void squareReusesRectangleFormulas() {
        Square square = new Square(4);
        assertInstanceOf(Rectangle.class, square);
        assertEquals(16, square.getArea(), DELTA);
        assertEquals(16, square.getPerimeter(), DELTA);
    }

    @Test
    void triangleAreaAndPerimeter() {
        Triangle triangle = new Triangle(6, 4);
        assertEquals(12, triangle.getArea(), DELTA);
        assertEquals(16, triangle.getPerimeter(), DELTA);
    }

    @Test
    void overriddenInfoIsUsedThroughShapeReference() {
        Shape shape = new Rectangle(5, 2);
        assertTrue(shape.getInfo().startsWith("Rectangle [width=5.00, height=2.00], area=10.00"));
    }

    @Test
    void rejectsNonPositiveDimensions() {
        assertThrows(IllegalArgumentException.class, () -> new Shape(0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Circle(-2));
    }
}

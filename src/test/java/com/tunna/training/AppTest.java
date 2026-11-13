package com.tunna.training;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void greetsByName() {
        assertEquals("Hello, Tunna!", App.greet("Tunna"));
    }

    @Test
    void greetsWorldWhenNameMissing() {
        assertEquals("Hello, World!", App.greet(null));
        assertEquals("Hello, World!", App.greet("  "));
    }
}

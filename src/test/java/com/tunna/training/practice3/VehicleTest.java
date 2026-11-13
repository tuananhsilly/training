package com.tunna.training.practice3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Year;
import org.junit.jupiter.api.Test;

class VehicleTest {

    private static final Owner OWNER = new Owner("001200000001", "Nguyen Van An", "an@example.com");

    @Test
    void vehicleNumberMustBeExactlyFiveCharacters() {
        assertEquals("30A01", new Car("30a01", Manufacturer.TOYOTA, 2020, "White", OWNER, 5, "Petrol").getNumber());
        assertThrows(IllegalArgumentException.class,
                () -> new Truck("30A0", Manufacturer.SUZUKI, 2020, "White", OWNER, 2));
        assertThrows(IllegalArgumentException.class,
                () -> new Truck("30A012", Manufacturer.SUZUKI, 2020, "White", OWNER, 2));
        assertThrows(IllegalArgumentException.class,
                () -> new Truck("30-01", Manufacturer.SUZUKI, 2020, "White", OWNER, 2));
    }

    @Test
    void yearMustBeAfter2000AndNotInTheFuture() {
        int now = Year.now().getValue();
        assertDoesNotThrow(() -> new Motorbike("29B12", Manufacturer.HONDA, 2001, "Red", OWNER, 110));
        assertDoesNotThrow(() -> new Motorbike("29B12", Manufacturer.HONDA, now, "Red", OWNER, 110));
        assertThrows(IllegalArgumentException.class,
                () -> new Motorbike("29B12", Manufacturer.HONDA, 2000, "Red", OWNER, 110));
        assertThrows(IllegalArgumentException.class,
                () -> new Motorbike("29B12", Manufacturer.HONDA, now + 1, "Red", OWNER, 110));
    }

    @Test
    void onlyKnownManufacturers() {
        assertEquals(Manufacturer.YAMAHA, Manufacturer.parse(" yamaha "));
        assertThrows(IllegalArgumentException.class, () -> Manufacturer.parse("Ford"));
    }

    @Test
    void ownerValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Owner("12345678901", "A", "a@b.com"));
        assertThrows(IllegalArgumentException.class, () -> new Owner("12345678901a", "A", "a@b.com"));
        assertThrows(IllegalArgumentException.class, () -> new Owner("123456789012", "A", "a@b"));
        assertThrows(IllegalArgumentException.class, () -> new Owner("123456789012", "A", "a.b.com"));
        assertDoesNotThrow(() -> new Owner("123456789012", "A", "first.last+tag@mail.co.vn"));
    }
}

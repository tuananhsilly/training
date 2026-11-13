package com.tunna.training.practice3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleManagerTest {

    private VehicleManager manager;

    @BeforeEach
    void setUp() {
        manager = new VehicleManager();
        TrafficPoliceApp.seed(manager);
    }

    @Test
    void rejectsDuplicateVehicleNumber() {
        Owner owner = manager.findOwner("001200000001");
        assertThrows(IllegalArgumentException.class,
                () -> manager.add(new Truck("30a01", Manufacturer.SUZUKI, 2020, "Red", owner, 3)));
        assertEquals(6, manager.size());
    }

    @Test
    void cmndBelongsToOnePerson() {
        Owner impostor = new Owner("001200000001", "Someone Else", "else@example.com");
        assertThrows(IllegalArgumentException.class,
                () -> manager.add(new Truck("99Z99", Manufacturer.SUZUKI, 2020, "Red", impostor, 3)));
    }

    @Test
    void searchByNumberIgnoresCase() {
        assertEquals("29C34", manager.findByNumber("29c34").getNumber());
        assertNull(manager.findByNumber("00000"));
    }

    @Test
    void findsVehiclesOfOwner() {
        List<Vehicle> vehicles = manager.findByOwnerCmnd("001200000002");
        assertEquals(List.of("29C34", "51G90"), vehicles.stream().map(Vehicle::getNumber).toList());
        assertTrue(manager.findByOwnerCmnd("999999999999").isEmpty());
    }

    @Test
    void deletesAllVehiclesOfManufacturer() {
        assertEquals(3, manager.removeByManufacturer(Manufacturer.HONDA));
        assertEquals(3, manager.size());
        assertEquals(0, manager.removeByManufacturer(Manufacturer.HONDA));
    }

    @Test
    void topManufacturerAndTies() {
        assertEquals(List.of(Manufacturer.HONDA), manager.topManufacturers());
        manager.removeByManufacturer(Manufacturer.HONDA);
        assertEquals(List.of(Manufacturer.YAMAHA, Manufacturer.TOYOTA, Manufacturer.SUZUKI),
                manager.topManufacturers());
        assertTrue(new VehicleManager().topManufacturers().isEmpty());
    }

    @Test
    void manufacturersSortedByCountDescending() {
        List<Integer> counts = manager.manufacturersByVehicleCountDesc().stream()
                .map(Map.Entry::getValue).toList();
        assertEquals(List.of(3, 1, 1, 1), counts);
        assertEquals(Manufacturer.HONDA, manager.manufacturersByVehicleCountDesc().get(0).getKey());
    }

    @Test
    void countsByType() {
        assertEquals(Map.of(VehicleType.CAR, 2, VehicleType.MOTORBIKE, 3, VehicleType.TRUCK, 1),
                manager.countByType());
    }

    @Test
    void appAddsVehicleAndReusesKnownOwner() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String input = String.join("\n",
                "1", "3", "88h88", "suzuki", "2021", "Green", "001200000003", "5",
                "1", "1", "30A01",
                "0") + "\n";
        new TrafficPoliceApp(new Scanner(input), new PrintStream(buffer), manager).run();
        String output = buffer.toString();

        assertEquals(7, manager.size());
        assertEquals("Le Van Cuong", manager.findByNumber("88H88").getOwner().getFullName());
        assertTrue(output.contains("Existing owner: Le Van Cuong"));
        assertTrue(output.contains("Failed: vehicle number 30A01 already exists."));
    }
}

package com.tunna.training.practice3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {

    private final Map<String, Vehicle> vehiclesByNumber = new LinkedHashMap<>();
    private final Map<String, Owner> ownersByCmnd = new HashMap<>();

    // 1. Rejects a duplicate vehicle number, or a CMND already registered to a different person
    public void add(Vehicle vehicle) {
        if (vehiclesByNumber.containsKey(vehicle.getNumber())) {
            throw new IllegalArgumentException("vehicle number already exists: " + vehicle.getNumber());
        }
        Owner known = ownersByCmnd.get(vehicle.getOwner().getCmnd());
        if (known != null && !known.sameInfoAs(vehicle.getOwner())) {
            throw new IllegalArgumentException("CMND " + known.getCmnd() + " already belongs to " + known);
        }
        ownersByCmnd.putIfAbsent(vehicle.getOwner().getCmnd(), vehicle.getOwner());
        vehiclesByNumber.put(vehicle.getNumber(), vehicle);
    }

    public Owner findOwner(String cmnd) {
        return ownersByCmnd.get(cmnd);
    }

    public List<Vehicle> getAll() {
        return List.copyOf(vehiclesByNumber.values());
    }

    public int size() {
        return vehiclesByNumber.size();
    }

    // 2.
    public Vehicle findByNumber(String number) {
        return vehiclesByNumber.get(number.strip().toUpperCase());
    }

    // 3.
    public List<Vehicle> findByOwnerCmnd(String cmnd) {
        return vehiclesByNumber.values().stream()
                .filter(v -> v.getOwner().getCmnd().equals(cmnd))
                .toList();
    }

    // 4. Returns how many vehicles were removed
    public int removeByManufacturer(Manufacturer manufacturer) {
        int before = vehiclesByNumber.size();
        vehiclesByNumber.values().removeIf(v -> v.getManufacturer() == manufacturer);
        return before - vehiclesByNumber.size();
    }

    // 5. Every manufacturer tied for the highest count; empty when there are no vehicles
    public List<Manufacturer> topManufacturers() {
        Map<Manufacturer, Integer> counts = countByManufacturer();
        int max = counts.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        if (max == 0) {
            return List.of();
        }
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .toList();
    }

    // 6. Manufacturers ordered by number of vehicles, most first
    public List<Map.Entry<Manufacturer, Integer>> manufacturersByVehicleCountDesc() {
        List<Map.Entry<Manufacturer, Integer>> entries = new ArrayList<>(countByManufacturer().entrySet());
        entries.sort(Map.Entry.<Manufacturer, Integer>comparingByValue(Comparator.reverseOrder()));
        return entries;
    }

    // 7.
    public Map<VehicleType, Integer> countByType() {
        Map<VehicleType, Integer> counts = new EnumMap<>(VehicleType.class);
        for (VehicleType type : VehicleType.values()) {
            counts.put(type, 0);
        }
        for (Vehicle vehicle : vehiclesByNumber.values()) {
            counts.merge(vehicle.getType(), 1, Integer::sum);
        }
        return counts;
    }

    public Map<Manufacturer, Integer> countByManufacturer() {
        Map<Manufacturer, Integer> counts = new EnumMap<>(Manufacturer.class);
        for (Manufacturer manufacturer : Manufacturer.values()) {
            counts.put(manufacturer, 0);
        }
        for (Vehicle vehicle : vehiclesByNumber.values()) {
            counts.merge(vehicle.getManufacturer(), 1, Integer::sum);
        }
        return counts;
    }
}

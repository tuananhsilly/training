package com.tunna.training.practice3;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TrafficPoliceApp {

    private final Scanner in;
    private final PrintStream out;
    private final VehicleManager manager;

    public TrafficPoliceApp(Scanner in, PrintStream out, VehicleManager manager) {
        this.in = in;
        this.out = out;
        this.manager = manager;
    }

    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();
        seed(manager);
        new TrafficPoliceApp(new Scanner(System.in), System.out, manager).run();
    }

    static void seed(VehicleManager manager) {
        Owner an = new Owner("001200000001", "Nguyen Van An", "an.nguyen@example.com");
        Owner binh = new Owner("001200000002", "Tran Thi Binh", "binh.tran@example.com");
        Owner cuong = new Owner("001200000003", "Le Van Cuong", "cuong.le@example.com");
        manager.add(new Car("30A01", Manufacturer.TOYOTA, 2020, "White", an, 5, "Petrol"));
        manager.add(new Motorbike("29B12", Manufacturer.HONDA, 2019, "Red", an, 125));
        manager.add(new Motorbike("29C34", Manufacturer.YAMAHA, 2022, "Blue", binh, 155));
        manager.add(new Motorbike("30D56", Manufacturer.HONDA, 2021, "Black", cuong, 110));
        manager.add(new Truck("51C78", Manufacturer.SUZUKI, 2018, "Silver", cuong, 2.5));
        manager.add(new Car("51G90", Manufacturer.HONDA, 2023, "Grey", binh, 7, "Hybrid"));
    }

    public void run() {
        try {
            while (true) {
                out.println();
                out.println("===== VEHICLE MANAGEMENT =====");
                out.println("1. Add vehicle");
                out.println("2. Search by vehicle number");
                out.println("3. Find vehicles by owner CMND");
                out.println("4. Delete all vehicles of a manufacturer");
                out.println("5. Manufacturer with the most vehicles");
                out.println("6. Manufacturers sorted by number of vehicles (descending)");
                out.println("7. Statistics by vehicle type");
                out.println("8. List all vehicles");
                out.println("0. Exit");
                switch (readInt("Choose: ", 0, 8)) {
                    case 1 -> addVehicle();
                    case 2 -> searchByNumber();
                    case 3 -> findByOwner();
                    case 4 -> deleteByManufacturer();
                    case 5 -> printTopManufacturers();
                    case 6 -> printManufacturersSorted();
                    case 7 -> printTypeStatistics();
                    case 8 -> printVehicles(manager.getAll());
                    default -> {
                        out.println("Bye!");
                        return;
                    }
                }
            }
        } catch (NoSuchElementException endOfInput) {
            out.println("Input closed, exiting.");
        }
    }

    void addVehicle() {
        out.println("Vehicle type: 1. Car  2. Motorbike  3. Truck");
        int type = readInt("Choose type: ", 1, 3);
        try {
            String number = Vehicle.normalizeNumber(readText("Vehicle number (5 letters/digits): "));
            if (manager.findByNumber(number) != null) {
                out.println("Failed: vehicle number " + number + " already exists.");
                return;
            }
            Manufacturer manufacturer = Manufacturer.parse(readText("Manufacturer (Honda/Yamaha/Toyota/Suzuki): "));
            int year = readInt("Year of manufacture: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
            String color = readText("Color: ");
            Owner owner = readOwner();

            Vehicle vehicle = switch (type) {
                case 1 -> new Car(number, manufacturer, year, color, owner,
                        readInt("Number of seats: ", 1, Integer.MAX_VALUE),
                        readText("Engine type: "));
                case 2 -> new Motorbike(number, manufacturer, year, color, owner,
                        readDouble("Capacity (cc): "));
                default -> new Truck(number, manufacturer, year, color, owner,
                        readDouble("Tonnage (t): "));
            };
            manager.add(vehicle);
            out.println("Added: " + vehicle);
        } catch (IllegalArgumentException e) {
            out.println("Failed: " + e.getMessage());
        }
    }

    // A known CMND reuses the stored owner, so each CMND always maps to one person
    private Owner readOwner() {
        String cmnd = readText("Owner CMND (12 digits): ");
        Owner known = manager.findOwner(cmnd);
        if (known != null) {
            out.println("Existing owner: " + known);
            return known;
        }
        return new Owner(cmnd, readText("Owner full name: "), readText("Owner email: "));
    }

    private void searchByNumber() {
        Vehicle vehicle = manager.findByNumber(readText("Vehicle number: "));
        out.println(vehicle == null ? "Not found." : vehicle.toString());
    }

    private void findByOwner() {
        printVehicles(manager.findByOwnerCmnd(readText("Owner CMND: ")));
    }

    private void deleteByManufacturer() {
        try {
            Manufacturer manufacturer = Manufacturer.parse(readText("Manufacturer to delete: "));
            int removed = manager.removeByManufacturer(manufacturer);
            out.printf("Deleted %d vehicle(s) of %s.%n", removed, manufacturer.getLabel());
        } catch (IllegalArgumentException e) {
            out.println("Failed: " + e.getMessage());
        }
    }

    private void printTopManufacturers() {
        List<Manufacturer> top = manager.topManufacturers();
        if (top.isEmpty()) {
            out.println("No vehicles yet.");
            return;
        }
        int count = manager.countByManufacturer().get(top.get(0));
        for (Manufacturer manufacturer : top) {
            out.printf("%s: %d vehicle(s)%n", manufacturer.getLabel(), count);
        }
    }

    private void printManufacturersSorted() {
        for (Map.Entry<Manufacturer, Integer> entry : manager.manufacturersByVehicleCountDesc()) {
            out.printf("%-8s %d%n", entry.getKey().getLabel(), entry.getValue());
        }
    }

    private void printTypeStatistics() {
        for (Map.Entry<VehicleType, Integer> entry : manager.countByType().entrySet()) {
            out.printf("%-10s %d%n", entry.getKey().getLabel(), entry.getValue());
        }
    }

    private void printVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            out.println("No vehicles found.");
            return;
        }
        for (Vehicle vehicle : vehicles) {
            out.println(vehicle);
        }
    }

    private String readText(String prompt) {
        while (true) {
            out.print(prompt);
            String line = in.nextLine().strip();
            if (!line.isEmpty()) {
                return line;
            }
            out.println("Value must not be empty.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            String line = readText(prompt);
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            out.println("Please enter a valid whole number.");
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            String line = readText(prompt);
            try {
                double value = Double.parseDouble(line);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            out.println("Please enter a number > 0.");
        }
    }
}

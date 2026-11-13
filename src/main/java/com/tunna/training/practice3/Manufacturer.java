package com.tunna.training.practice3;

public enum Manufacturer {
    HONDA, YAMAHA, TOYOTA, SUZUKI;

    public static Manufacturer parse(String text) {
        for (Manufacturer m : values()) {
            if (m.name().equalsIgnoreCase(text.strip())) {
                return m;
            }
        }
        throw new IllegalArgumentException("manufacturer must be one of Honda, Yamaha, Toyota, Suzuki: " + text);
    }

    public String getLabel() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

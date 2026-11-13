package com.tunna.training.practice3;

import java.util.regex.Pattern;

public class Owner {

    private static final Pattern CMND = Pattern.compile("\\d{12}");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private final String cmnd;
    private final String fullName;
    private final String email;

    public Owner(String cmnd, String fullName, String email) {
        if (cmnd == null || !CMND.matcher(cmnd).matches()) {
            throw new IllegalArgumentException("CMND must be exactly 12 digits: " + cmnd);
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("full name must not be blank");
        }
        if (email == null || !EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("invalid email: " + email);
        }
        this.cmnd = cmnd;
        this.fullName = fullName.strip();
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public boolean sameInfoAs(Owner other) {
        return cmnd.equals(other.cmnd)
                && fullName.equalsIgnoreCase(other.fullName)
                && email.equalsIgnoreCase(other.email);
    }

    @Override
    public String toString() {
        return String.format("%s (CMND %s, %s)", fullName, cmnd, email);
    }
}

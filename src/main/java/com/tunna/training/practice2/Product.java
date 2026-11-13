package com.tunna.training.practice2;

// Common data of every kind of goods; subclasses decide their category (and therefore their VAT rate)
public abstract class Product {

    private final String code;
    private final String name;
    private int quantity;
    private double unitPrice;

    protected Product(String code, String name, int quantity, double unitPrice) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("code must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this.code = code;
        this.name = name;
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public abstract Category getCategory();

    protected abstract String getDetails();

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be >= 0: " + quantity);
        }
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("unit price must be >= 0: " + unitPrice);
        }
        this.unitPrice = unitPrice;
    }

    public double getVatRate() {
        return getCategory().getVatRate();
    }

    public double getInventoryValue() {
        return quantity * unitPrice;
    }

    public double getVatAmount() {
        return getInventoryValue() * getVatRate();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | qty=%d, price=%.2f, VAT %.0f%%=%.2f | %s",
                getCategory().getLabel(), code, name, quantity, unitPrice,
                getVatRate() * 100, getVatAmount(), getDetails());
    }
}

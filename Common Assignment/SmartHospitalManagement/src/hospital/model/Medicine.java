package hospital.model;

import hospital.exception.OutOfStockException;

/**
 * Medicine class representing a medicine in the pharmacy.
 * Demonstrates encapsulation and basic attributes.
 */
public class Medicine {
    private String medicineId;
    private String medicineName;
    private String category;
    private String manufacturer;
    private int quantity;
    private double price;
    private int reorderLevel;
    private String dosage;

    // Constructors
    public Medicine() {
    }

    public Medicine(String medicineId, String medicineName, String category, String manufacturer,
                    int quantity, double price, int reorderLevel, String dosage) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.category = category;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
        this.reorderLevel = reorderLevel;
        this.dosage = dosage;
    }

    // Getters and Setters
    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    // Methods
    /**
     * Add stock to the medicine.
     * @param amount quantity to add
     */
    public void addStock(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    /**
     * Reduce stock from the medicine.
     * @param amount quantity to reduce
     * @throws OutOfStockException if requested amount exceeds available quantity
     */
    public void reduceStock(int amount) throws OutOfStockException {
        if (amount > this.quantity) {
            throw new OutOfStockException("Insufficient stock. Available: " + this.quantity + ", Requested: " + amount);
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.quantity -= amount;
    }

    /**
     * Check if the medicine is low in stock.
     * @return true if quantity <= reorderLevel
     */
    public boolean isLowStock() {
        return this.quantity <= this.reorderLevel;
    }

    public void displayMedicine() {
        System.out.println("Medicine ID: " + medicineId);
        System.out.println("Medicine Name: " + medicineName);
        System.out.println("Category: " + category);
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);
        System.out.println("Reorder Level: " + reorderLevel);
        System.out.println("Dosage: " + dosage);
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineId='" + medicineId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", category='" + category + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", reorderLevel=" + reorderLevel +
                ", dosage='" + dosage + '\'' +
                '}';
    }
}
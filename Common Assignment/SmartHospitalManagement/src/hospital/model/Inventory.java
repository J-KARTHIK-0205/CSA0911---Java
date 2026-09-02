package hospital.model;

import hospital.exception.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Inventory class to manage all medicines.
 * Demonstrates HashMap, Hashtable, Iterator, ListIterator, and Generics as required.
 */
public class Inventory {
    // Using HashMap for medicine lookup by ID (requirement)
    private Map<String, Medicine> medicinesById = new HashMap<>();

    // Using Hashtable for notification registry (requirement)
    private java.util.Hashtable<String, String> notificationRegistry = new java.util.Hashtable<>();

    // Additional collections for demonstration
    private Set<String> medicineIds = new HashSet<>(); // Unique medicine IDs
    private Set<String> categories = new HashSet<>(); // Unique medicine categories
    private List<Medicine> medicineList = new ArrayList<>(); // List of all medicines

    /**
     * Add a medicine to the inventory.
     * @param medicine Medicine object to add
     * @throws InvalidMedicineException if medicine data is invalid
     */
    public void addMedicine(Medicine medicine) throws InvalidMedicineException {
        // Basic validation
        if (medicine == null) {
            throw new InvalidMedicineException("Medicine cannot be null");
        }
        if (medicine.getMedicineId() == null || medicine.getMedicineId().trim().isEmpty()) {
            throw new InvalidMedicineException("Medicine ID is required");
        }

        // Check for duplicate ID
        if (medicineIds.contains(medicine.getMedicineId())) {
            throw new InvalidMedicineException("Medicine ID already exists: " + medicine.getMedicineId());
        }

        // Add to all collections
        medicineList.add(medicine);
        medicinesById.put(medicine.getMedicineId(), medicine);
        medicineIds.add(medicine.getMedicineId());
        if (medicine.getCategory() != null) {
            categories.add(medicine.getCategory());
        }
    }

    /**
     * Remove a medicine from the inventory.
     * @param medicineId ID of medicine to remove
     * @return true if removed
     */
    public boolean removeMedicine(String medicineId) {
        Medicine medicine = medicinesById.get(medicineId);
        if (medicine == null) {
            return false;
        }

        medicineList.remove(medicine);
        medicinesById.remove(medicineId);
        medicineIds.remove(medicineId);
        // Note: We don't remove from categories as other medicines might use it

        // Remove from notification registry
        notificationRegistry.remove(medicineId);

        return true;
    }

    /**
     * Search for a medicine by ID.
     * @param medicineId ID of medicine to search for
     * @return Medicine object if found
     * @throws MedicineNotFoundException if medicine not found
     */
    public Medicine searchMedicine(String medicineId) throws MedicineNotFoundException {
        Medicine medicine = medicinesById.get(medicineId);
        if (medicine == null) {
            throw new MedicineNotFoundException("Medicine not found with ID: " + medicineId);
        }
        return medicine;
    }

    /**
     * Update medicine information.
     * @param medicineId ID of medicine to update
     * @param name New name
     * @param category New category
     * @param manufacturer New manufacturer
     * @param quantity New quantity
     * @param price New price
     * @param reorderLevel New reorder level
     * @param dosage New dosage
     * @throws MedicineNotFoundException if medicine not found
     * @throws InvalidMedicineException if medicine data is invalid
     */
    public void updateMedicine(String medicineId, String name, String category, String manufacturer,
                               int quantity, double price, int reorderLevel, String dosage)
            throws MedicineNotFoundException, InvalidMedicineException {
        Medicine medicine = searchMedicine(medicineId);

        // Validate the new data
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidMedicineException("Medicine name is required");
        }
        if (quantity < 0) {
            throw new InvalidMedicineException("Quantity cannot be negative");
        }
        if (price < 0) {
            throw new InvalidMedicineException("Price cannot be negative");
        }
        if (reorderLevel < 0) {
            throw new InvalidMedicineException("Reorder level cannot be negative");
        }

        // Update the medicine
        medicine.setMedicineName(name);
        medicine.setCategory(category);
        medicine.setManufacturer(manufacturer);
        medicine.setQuantity(quantity);
        medicine.setPrice(price);
        medicine.setReorderLevel(reorderLevel);
        medicine.setDosage(dosage);

        // Update category set if needed
        if (category != null && !categories.contains(category)) {
            categories.add(category);
        }
    }

    /**
     * Dispense medicine (reduce stock).
     * @param medicineId ID of medicine
     * @param quantity Quantity to dispense
     * @return true if successful
     * @throws OutOfStockException if insufficient stock
     * @throws MedicineNotFoundException if medicine not found
     */
    public boolean dispenseMedicine(String medicineId, int quantity)
            throws OutOfStockException, MedicineNotFoundException {
        Medicine medicine = searchMedicine(medicineId);
        medicine.reduceStock(quantity); // This will throw OutOfStockException if insufficient

        // Add to notification registry for low stock alert
        if (medicine.isLowStock()) {
            notificationRegistry.put(medicineId, "LOW_STOCK_ALERT");
        }

        return true;
    }

    /**
     * Restock medicine (increase inventory quantity).
     * @param medicineId ID of medicine
     * @param quantity Quantity to add
     * @return true if successful
     * @throws MedicineNotFoundException if medicine not found
     */
    public boolean restockMedicine(String medicineId, int quantity)
            throws MedicineNotFoundException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Restock quantity must be positive");
        }
        Medicine medicine = searchMedicine(medicineId);
        medicine.addStock(quantity);

        // Remove from notification registry if stock is sufficient
        if (!medicine.isLowStock()) {
            notificationRegistry.remove(medicineId);
        }

        return true;
    }

    /**
     * Get all medicines.
     * @return List of all medicines
     */
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicineList);
    }

    /**
     * Get medicine by ID.
     * @param medicineId ID of medicine
     * @return Medicine object if found, null otherwise
     */
    public Medicine getMedicineById(String medicineId) {
        return medicinesById.get(medicineId);
    }

    /**
     * Display all medicines in the inventory.
     */
    public void displayInventory() {
        if (medicineList.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }
        System.out.println("\n=== Pharmacy Inventory ===");
        for (Medicine medicine : medicineList) {
            medicine.displayMedicine();
            System.out.println("-------------------");
        }
    }

    /**
     * Display inventory report.
     */
    public void displayInventoryReport() {
        if (medicineList.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }

        int totalMedicines = medicineList.size();
        int totalQuantity = 0;
        double totalValue = 0.0;
        int lowStockCount = 0;
        int outOfStockCount = 0;

        for (Medicine medicine : medicineList) {
            totalQuantity += medicine.getQuantity();
            totalValue += medicine.getQuantity() * medicine.getPrice();
            if (medicine.isLowStock()) {
                lowStockCount++;
                if (medicine.getQuantity() == 0) {
                    outOfStockCount++;
                }
            }
        }

        System.out.println("\n=== Pharmacy Inventory Report ===");
        System.out.println("Total Medicines: " + totalMedicines);
        System.out.println("Total Stock Quantity: " + totalQuantity);
        System.out.println("Total Inventory Value: $" + String.format("%.2f", totalValue));
        System.out.println("Low Stock Medicines: " + lowStockCount);
        System.out.println("Out of Stock Medicines: " + outOfStockCount);

        // Display low stock medicines
        if (lowStockCount > 0) {
            System.out.println("\n--- Low Stock Medicines ---");
            for (Medicine medicine : medicineList) {
                if (medicine.isLowStock()) {
                    System.out.println("- " + medicine.getMedicineName() +
                              " (ID: " + medicine.getMedicineId() +
                              ", Qty: " + medicine.getQuantity() +
                              ", Reorder Level: " + medicine.getReorderLevel() + ")");
                }
            }
        }

        // Display out of stock medicines
        if (outOfStockCount > 0) {
            System.out.println("\n--- Out of Stock Medicines ---");
            for (Medicine medicine : medicineList) {
                if (medicine.getQuantity() == 0) {
                    System.out.println("- " + medicine.getMedicineName() +
                              " (ID: " + medicine.getMedicineId() + ")");
                }
            }
        }
    }

    /**
     * Display low stock report.
     */
    public void displayLowStockReport() {
        boolean hasLowStock = false;

        System.out.println("\n=== Low Stock Report ===");
        for (Medicine medicine : medicineList) {
            if (medicine.isLowStock()) {
                if (!hasLowStock) {
                    hasLowStock = true;
                }
                System.out.println("- " + medicine.getMedicineName() +
                          " (ID: " + medicine.getMedicineId() +
                          ", Current Stock: " + medicine.getQuantity() +
                          ", Reorder Level: " + medicine.getReorderLevel() + ")");
            }
        }

        if (!hasLowStock) {
            System.out.println("No medicines are currently low in stock.");
        }
    }

    /**
     * Get count of medicines.
     * @return Number of medicines
     */
    public int getMedicineCount() {
        return medicineList.size();
    }

    /**
     * Get unique medicine categories using HashSet.
     * @return Set of unique medicine categories
     */
    public Set<String> getUniqueCategories() {
        return new HashSet<>(categories);
    }

    /**
     * Get medicines by category.
     * @param category Category to filter by
     * @return List of medicines with given category
     */
    public List<Medicine> getMedicinesByCategory(String category) {
        List<Medicine> result = new ArrayList<>();
        for (Medicine medicine : medicineList) {
            if (category.equalsIgnoreCase(medicine.getCategory())) {
                result.add(medicine);
            }
        }
        return result;
    }

    /**
     * Demonstrate Iterator usage for searching records.
     * @param medicineId ID to search for
     * @return Medicine if found
     * @throws MedicineNotFoundException if not found
     */
    public Medicine searchMedicineWithIterator(String medicineId) throws MedicineNotFoundException {
        Iterator<Medicine> iterator = medicineList.iterator();
        while (iterator.hasNext()) {
            Medicine medicine = iterator.next();
            if (medicine.getMedicineId().equals(medicineId)) {
                return medicine;
            }
        }
        throw new MedicineNotFoundException("Medicine not found with ID: " + medicineId);
    }

    /**
     * Demonstrate ListIterator usage for updating/traversal.
     * This example updates the first medicine's name to add "[Updated]" prefix.
     */
    public void demonstrateListIterator() {
        if (medicineList.isEmpty()) {
            System.out.println("No medicines to demonstrate ListIterator.");
            return;
        }

        ListIterator<Medicine> listIterator = medicineList.listIterator();
        if (listIterator.hasNext()) {
            Medicine firstMedicine = listIterator.next();
            String originalName = firstMedicine.getMedicineName();
            firstMedicine.setMedicineName(originalName + " [Updated]");
            System.out.println("Updated first medicine name using ListIterator: " +
                              originalName + " -> " + firstMedicine.getMedicineName());
        }
    }

    /**
     * Demonstrate Hashtable usage for notification registry.
     */
    public void demonstrateNotificationRegistry() {
        System.out.println("\n=== Notification Registry (Hashtable) ===");
        if (notificationRegistry.isEmpty()) {
            System.out.println("Notification registry is empty.");
            return;
        }

        // Iterate through Hashtable keys
        java.util.Enumeration<String> keys = notificationRegistry.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = notificationRegistry.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
    }
}
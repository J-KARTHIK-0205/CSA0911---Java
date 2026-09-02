package hospital.model;

/**
 * LowStockNotification extends Notification.
 * Demonstrates inheritance and method overriding (polymorphism).
 */
public class LowStockNotification extends Notification {
    // Additional fields specific to low stock notifications
    private String medicineName;
    private int currentQuantity;
    private int reorderLevel;

    // Constructors
    public LowStockNotification() {
        super();
    }

    public LowStockNotification(String notificationId, String recipient, String message, String notificationType,
                                String medicineName, int currentQuantity, int reorderLevel) {
        super(notificationId, recipient, message, notificationType);
        this.medicineName = medicineName;
        this.currentQuantity = currentQuantity;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters for additional fields
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /**
     * Override to send low stock notification in a specific format.
     * This demonstrates runtime polymorphism.
     */
    @Override
    public void sendNotification() {
        System.out.println("\n=== LOW STOCK NOTIFICATION ===");
        System.out.println("Medicine: " + medicineName);
        System.out.println("Current Quantity: " + currentQuantity);
        System.out.println("Reorder Level: " + reorderLevel);
        System.out.println("Status: LOW STOCK - Please restock soon!");
        System.out.println("==============================");
    }
}
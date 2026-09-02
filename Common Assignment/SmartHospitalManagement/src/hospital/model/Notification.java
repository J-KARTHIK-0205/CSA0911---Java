package hospital.model;

/**
 * Base Notification class.
 * Demonstrates inheritance and polymorphism.
 */
public class Notification {
    private String notificationId;
    private String recipient;
    private String message;
    private String notificationType;
    private String timestamp;

    // Constructors
    public Notification() {
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public Notification(String notificationId, String recipient, String message, String notificationType) {
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.message = message;
        this.notificationType = notificationType;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Send the notification.
     * This method can be overridden by subclasses to demonstrate polymorphism.
     */
    public void sendNotification() {
        System.out.println("\n=== NOTIFICATION ===");
        System.out.println("ID: " + notificationId);
        System.out.println("To: " + recipient);
        System.out.println("Type: " + notificationType);
        System.out.println("Time: " + timestamp);
        System.out.println("Message:");
        System.out.println(message);
        System.out.println("====================");
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", recipient='" + recipient + '\'' +
                ", message='" + message + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
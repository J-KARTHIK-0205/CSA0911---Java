package hospital.service;

import hospital.exception.*;
import hospital.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Service class for managing notifications.
 * Demonstrates ArrayList, Iterator, and Queue for notification dispatch.
 */
public class NotificationService {
    // Queue for storing notifications to be dispatched
    private Queue<Notification> notificationQueue = new LinkedList<>();

    // List for storing all notifications ever sent (for history)
    private List<Notification> notificationHistory = new ArrayList<>();

    // Counter for generating notification IDs
    private int notificationCounter = 1000;

    /**
     * Create and add a notification to the queue.
     * @param recipient Recipient of the notification
     * @param message Message content
     * @param notificationType Type of notification
     */
    public void createNotification(String recipient, String message, String notificationType) {
        String notificationId = "NOTIF" + notificationCounter++;
        Notification notification = new Notification(notificationId, recipient, message, notificationType);
        notificationQueue.add(notification);
        notificationHistory.add(notification);
        System.out.println("Notification created and queued for " + recipient + ": " + notificationType);
    }

    /**
     * Create and add an appointment reminder notification.
     * @param patientName Name of the patient
     * @param doctorName Name of the doctor
     * @param date Date of appointment
     * @param time Time of appointment
     * @param appointmentId ID of appointment
     */
    public void createAppointmentReminder(String patientName, String doctorName, String date, String time, String appointmentId) {
        String message = "APPOINTMENT REMINDER\nPatient: " + patientName +
                        "\nDoctor: " + doctorName +
                        "\nDate: " + date +
                        "\nTime: " + time +
                        "\nAppointment ID: " + appointmentId;
        createNotification(patientName, message, "APPOINTMENT_REMINDER");
    }

    /**
     * Create and add a low stock notification.
     * @param medicineName Name of the medicine
     * @param currentQuantity Current stock quantity
     * @param reorderLevel Reorder level threshold
     */
    public void createLowStockNotification(String medicineName, int currentQuantity, int reorderLevel) {
        String message = "LOW STOCK NOTIFICATION\nMedicine: " + medicineName +
                        "\nCurrent Quantity: " + currentQuantity +
                        "\nReorder Level: " + reorderLevel +
                        "\nPlease restock soon.";
        createNotification("Pharmacy Manager", message, "LOW_STOCK");
    }

    /**
     * Process the notification queue (simulates a notification dispatcher thread).
     * This method would typically run in a separate thread.
     */
    public void processNotificationQueue() {
        System.out.println("\n=== Notification Dispatcher Started ===");
        while (!notificationQueue.isEmpty()) {
            Notification notification = notificationQueue.poll();
            if (notification != null) {
                notification.sendNotification(); // This demonstrates polymorphism
                try {
                    Thread.sleep(500); // Simulate processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Notification dispatcher interrupted.");
                    break;
                }
            }
        }
        System.out.println("Notification queue is empty. Dispatcher stopped.");
    }

    /**
     * Display all notifications in the queue (without removing them).
     */
    public void displayNotificationQueue() {
        if (notificationQueue.isEmpty()) {
            System.out.println("Notification queue is empty.");
            return;
        }
        System.out.println("\n=== Current Notification Queue ===");
        // We need to iterate without removing, so we'll use a copy or iterator
        Iterator<Notification> iterator = new ArrayList<>(notificationQueue).iterator();
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println(count + ". " + iterator.next());
            count++;
        }
    }

    /**
     * Display notification history.
     */
    public void displayNotificationHistory() {
        if (notificationHistory.isEmpty()) {
            System.out.println("No notification history.");
            return;
        }
        System.out.println("\n=== Notification History ===");
        Iterator<Notification> iterator = notificationHistory.iterator();
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println(count + ". " + iterator.next());
            count++;
        }
    }

    /**
     * Get count of notifications in queue.
     * @return Number of notifications waiting to be dispatched
     */
    public int getNotificationQueueCount() {
        return notificationQueue.size();
    }

    /**
     * Get total count of notifications sent.
     * @return Total number of notifications ever sent
     */
    public int getNotificationHistoryCount() {
        return notificationHistory.size();
    }
}
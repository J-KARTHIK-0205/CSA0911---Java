package hospital.thread;

import hospital.service.*;
import hospital.model.*;

/**
 * Producer thread for creating notifications.
 * Demonstrates producer-consumer pattern with wait() and notify().
 */
public class NotificationProducer implements Runnable {
    private final NotificationService notificationService;
    private final boolean runContinuously;
    private volatile boolean running = true;

    public NotificationProducer(NotificationService notificationService, boolean runContinuously) {
        this.notificationService = notificationService;
        this.runContinuously = runContinuously;
    }

    public void stopRunning() {
        this.running = false;
    }

    @Override
    public void run() {
        int notificationCount = 0;

        while (running) {
            try {
                // Create different types of notifications
                if (notificationCount % 3 == 0) {
                    // Appointment reminder
                    notificationService.createAppointmentReminder(
                            "John Doe", "Dr. Smith", "2026-09-15", "10:30 AM", "APT001");
                } else if (notificationCount % 3 == 1) {
                    // Low stock notification
                    notificationService.createLowStockNotification(
                            "Paracetamol", 5, 10);
                } else {
                    // Generic notification
                    notificationService.createNotification(
                            "Admin", "System check completed at " + java.time.LocalDateTime.now(),
                            "SYSTEM_INFO");
                }

                notificationCount++;

                // If not running continuously, break after a few notifications
                if (!runContinuously && notificationCount >= 5) {
                    break;
                }

                // Wait for a bit before creating next notification
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Notification producer interrupted.");
                break;
            } catch (Exception e) {
                System.out.println("Error in notification producer: " + e.getMessage());
            }
        }

        System.out.println("Notification producer stopped.");
    }
}
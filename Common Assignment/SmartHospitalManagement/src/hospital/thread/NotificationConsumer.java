package hospital.thread;

import hospital.service.*;
import hospital.model.*;

/**
 * Consumer thread for processing notifications from the queue.
 * Demonstrates consumer-producer pattern with wait() and notify().
 */
public class NotificationConsumer implements Runnable {
    private final NotificationService notificationService;
    private final boolean runContinuously;
    private volatile boolean running = true;

    public NotificationConsumer(NotificationService notificationService, boolean runContinuously) {
        this.notificationService = notificationService;
        this.runContinuously = runContinuously;
    }

    public void stopRunning() {
        this.running = false;
    }

    @Override
    public void run() {
        System.out.println("Notification consumer started. Waiting for notifications...");

        while (running) {
            try {
                // Process notifications from the queue
                if (notificationService.getNotificationQueueCount() != 0) {
                    notificationService.processNotificationQueue();
                }

                // If not running continuously, break after processing once
                if (!runContinuously) {
                    break;
                }

                // Wait for a bit before checking again
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Notification consumer interrupted.");
                break;
            } catch (Exception e) {
                System.out.println("Error in notification consumer: " + e.getMessage());
            }
        }

        System.out.println("Notification consumer stopped.");
    }
}
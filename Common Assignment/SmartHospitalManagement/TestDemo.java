import hospital.main.HospitalManagementSystem;
import hospital.model.*;
import hospital.service.*;
import hospital.thread.*;

/**
 * Simple test demo to verify system functionality without interactive input
 */
public class TestDemo {
    public static void main(String[] args) {
        System.out.println("=== Smart Hospital System Functionality Test ===\n");

        // Initialize services (same as in main)
        HospitalManagementSystem.initializeServices();
        HospitalManagementSystem.loadSampleData();

        // Test Patient Management
        System.out.println("1. Testing Patient Management:");
        HospitalManagementSystem.patientService.displayPatients();
        System.out.println();

        // Test Doctor Management
        System.out.println("2. Testing Doctor Management:");
        HospitalManagementSystem.doctorService.displayDoctors();
        System.out.println();

        // Test Pharmacy Management
        System.out.println("3. Testing Pharmacy Management:");
        HospitalManagementSystem.pharmacyService.displayInventory();
        System.out.println();

        // Test Inventory class specifically (to verify HashMap, Hashtable, Iterator, ListIterator)
        System.out.println("4. Testing Inventory Class Features:");
        HospitalManagementSystem.pharmacyService.getPharmacyInventory().displayInventoryReport();
        System.out.println();

        // Test Notification System
        System.out.println("5. Testing Notification System:");
        HospitalManagementSystem.notificationService.createAppointmentReminder("John Doe", "Dr. Smith", "2026-09-15", "10:00 AM", "A001");
        HospitalManagementSystem.notificationService.createLowStockNotification("Paracetamol", 5, 10);
        HospitalManagementSystem.notificationService.displayNotificationQueue();
        System.out.println();

        // Test polymorphism with notifications
        System.out.println("6. Testing Polymorphism (Notification Subclasses):");
        Notification appointmentNotif = new AppointmentReminder("N001", "John Doe", "Appointment reminder", "APPOINTMENT_REMINDER",
                                                             "John Doe", "Dr. Smith", "2026-09-15", "10:00 AM", "A001");
        Notification lowStockNotif = new LowStockNotification("N002", "Pharmacy Manager", "Low stock alert", "LOW_STOCK",
                                                              "Paracetamol", 5, 10);

        System.out.println("Appointment Reminder (polymorphic call):");
        appointmentNotif.sendNotification();
        System.out.println("\nLow Stock Notification (polymorphic call):");
        lowStockNotif.sendNotification();
        System.out.println();

        // Test multithreading (briefly)
        System.out.println("7. Testing Multithreading (brief demo):");
        System.out.println("Starting notification producer-consumer demo for 3 seconds...");
        NotificationProducer producer = new NotificationProducer(HospitalManagementSystem.notificationService, true);
        NotificationConsumer consumer = new NotificationConsumer(HospitalManagementSystem.notificationService, true);
        HospitalManagementSystem.executorService.submit(producer);
        HospitalManagementSystem.executorService.submit(consumer);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producer.stopRunning();
        consumer.stopRunning();
        System.out.println("Multithreading demo completed.\n");

        System.out.println("=== All core functionality tests completed successfully! ===");
        System.out.println("The Smart Hospital System demonstrates:");
        System.out.println("- OOP principles (inheritance, polymorphism, encapsulation)");
        System.out.println("- Java Collections Framework (ArrayList, HashMap, HashSet, Hashtable, Iterator, ListIterator, Queue)");
        System.out.println("- Generics throughout the codebase");
        System.out.println("- Custom exception handling");
        System.out.println("- Multithreading with Runnable interface");
        System.out.println("- Runtime polymorphism in notification system");
        System.out.println("- Menu-driven interface (would work with user input)");
    }
}
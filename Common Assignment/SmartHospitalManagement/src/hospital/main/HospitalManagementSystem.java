package hospital.main;

import hospital.exception.*;
import hospital.model.*;
import hospital.service.*;
import hospital.thread.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main class for the Smart Hospital Management System.
 * Provides a console/menu-driven interface for all system functionalities.
 */
public class HospitalManagementSystem {
    // Services
    private static PatientService patientService;
    private static DoctorService doctorService;
    private static AppointmentService appointmentService;
    private static PharmacyService pharmacyService;
    private static NotificationService notificationService;
    private static ReportGenerator reportGenerator;

    // Thread pool for managing background tasks
    private static ExecutorService executorService;

    // Scanner for user input
    private static Scanner scanner;

    public static void main(String[] args) {
        // Initialize services
        initializeServices();

        // Load sample data
        loadSampleData();

        // Start the main menu
        mainMenu();
    }

    /**
     * Initialize all services.
     */
    private static void initializeServices() {
        patientService = new PatientService();
        doctorService = new DoctorService();
        appointmentService = new AppointmentService(patientService, doctorService);
        pharmacyService = new PharmacyService();
        notificationService = new NotificationService();
        reportGenerator = new ReportGenerator(patientService, doctorService, appointmentService, pharmacyService);
        scanner = new Scanner(System.in);

        // Create a fixed thread pool for background tasks
        executorService = Executors.newFixedThreadPool(3);

        System.out.println("Smart Hospital Management System initialized successfully!");
    }

    /**
     * Load sample data when the system starts.
     */
    private static void loadSampleData() {
        try {
            // Sample Patients
            Patient patient1 = new Patient("P001", "Arun Kumar", 30, "Male", "9876543210", "arun@email.com", "Regular", "No known allergies");
            Patient patient2 = new Patient("P002", "Priya Sharma", 25, "Female", "9876543211", "priya@email.com", "Regular", "Mild asthma");
            Patient patient3 = new Patient("P003", "Rahul Raj", 35, "Male", "9876543212", "rahul@email.com", "Regular", "Hypertension");

            patientService.registerPatient(patient1);
            patientService.registerPatient(patient2);
            patientService.registerPatient(patient3);

            // Sample Doctors
            Doctor doctor1 = new Doctor("D001", "Dr. Meena", "Cardiology", "Cardiology Department", 1000.0);
            doctor1.addSlot("10:00 AM");
            doctor1.addSlot("11:00 AM");
            doctor1.addSlot("02:00 PM");
            doctor1.addSlot("03:00 PM");

            Doctor doctor2 = new Doctor("D002", "Dr. Kumar", "General Medicine", "General Medicine Department", 800.0);
            doctor2.addSlot("09:00 AM");
            doctor2.addSlot("10:00 AM");
            doctor2.addSlot("11:00 AM");
            doctor2.addSlot("04:00 PM");

            Doctor doctor3 = new Doctor("D003", "Dr. Anitha", "Pediatrics", "Pediatrics Department", 900.0);
            doctor3.addSlot("10:00 AM");
            doctor3.addSlot("11:00 AM");
            doctor3.addSlot("02:00 PM");
            doctor3.addSlot("04:00 PM");

            doctorService.registerDoctor(doctor1);
            doctorService.registerDoctor(doctor2);
            doctorService.registerDoctor(doctor3);

            // Sample Medicines
            Medicine medicine1 = new Medicine("M001", "Paracetamol", "Pain Relief", "PharmaCorp", 50, 10.0, 10, "500mg");
            Medicine medicine2 = new Medicine("M002", "Amoxicillin", "Antibiotic", "MediLife", 30, 25.0, 5, "250mg");
            Medicine medicine3 = new Medicine("M003", "Cetirizine", "Antihistamine", "HealthPlus", 100, 15.0, 20, "10mg");

            pharmacyService.addMedicine(medicine1);
            pharmacyService.addMedicine(medicine2);
            pharmacyService.addMedicine(medicine3);

            System.out.println("Sample data loaded successfully!");
        } catch (Exception e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }

    /**
     * Display the main menu and handle user choices.
     */
    private static void mainMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("SMART HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Pharmacy Management");
            System.out.println("5. Notification Management");
            System.out.println("6. Reports");
            System.out.println("7. Multithreading Demonstration");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 8);

            switch (choice) {
                case 1:
                    patientManagementMenu();
                    break;
                case 2:
                    doctorManagementMenu();
                    break;
                case 3:
                    appointmentManagementMenu();
                    break;
                case 4:
                    pharmacyManagementMenu();
                    break;
                case 5:
                    notificationManagementMenu();
                    break;
                case 6:
                    reportsMenu();
                    break;
                case 7:
                    multithreadingDemoMenu();
                    break;
                case 8:
                    exit = true;
                    System.out.println("Thank you for using Smart Hospital Management System!");
                    break;
            }
        }

        // Shutdown thread pool before exiting
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }

        if (scanner != null) {
            scanner.close();
        }
    }

    /**
     * Get a valid integer choice from the user within a specified range.
     * @param min Minimum valid choice (inclusive)
     * @param max Maximum valid choice (inclusive)
     * @return Valid user choice
     */
    private static int getUserChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    /**
     * Patient Management submenu.
     */
    private static void patientManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("PATIENT MANAGEMENT");
            System.out.println("-".repeat(30));
            System.out.println("1. Register Patient");
            System.out.println("2. Display Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 5);

            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    patientService.displayPatients();
                    break;
                case 3:
                    searchPatient();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Doctor Management submenu.
     */
    private static void doctorManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("DOCTOR MANAGEMENT");
            System.out.println("-".repeat(30));
            System.out.println("1. Register Doctor");
            System.out.println("2. Display Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Display Available Slots");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 5);

            switch (choice) {
                case 1:
                    registerDoctor();
                    break;
                case 2:
                    doctorService.displayDoctors();
                    break;
                case 3:
                    searchDoctor();
                    break;
                case 4:
                    displayAvailableSlots();
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Appointment Management submenu.
     */
    private static void appointmentManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("APPOINTMENT MANAGEMENT");
            System.out.println("-".repeat(30));
            System.out.println("1. Book Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. Display Appointments");
            System.out.println("4. Display Waitlist");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 5);

            switch (choice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    cancelAppointment();
                    break;
                case 3:
                    appointmentService.displayAppointments();
                    break;
                case 4:
                    appointmentService.displayWaitlist("D001"); // Show waitlist for first doctor as example
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Pharmacy Management submenu.
     */
    private static void pharmacyManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("PHARMACY MANAGEMENT");
            System.out.println("-".repeat(30));
            System.out.println("1. Add Medicine");
            System.out.println("2. Search Medicine");
            System.out.println("3. Update Medicine");
            System.out.println("4. Dispense Medicine");
            System.out.println("5. Restock Medicine");
            System.out.println("6. Display Inventory");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 7);

            switch (choice) {
                case 1:
                    addMedicine();
                    break;
                case 2:
                    searchMedicine();
                    break;
                case 3:
                    updateMedicine();
                    break;
                case 4:
                    dispenseMedicine();
                    break;
                case 5:
                    restockMedicine();
                    break;
                case 6:
                    pharmacyService.displayInventory();
                    break;
                case 7:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Notification Management submenu.
     */
    private static void notificationManagementMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("NOTIFICATION MANAGEMENT");
            System.out.println("-".repeat(30));
            System.out.println("1. Create Appointment Reminder");
            System.out.println("2. Create Low Stock Notification");
            System.out.println("3. Display Notification Queue");
            System.out.println("4. Display Notification History");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 5);

            switch (choice) {
                case 1:
                    createAppointmentReminder();
                    break;
                case 2:
                    createLowStockNotification();
                    break;
                case 3:
                    notificationService.displayNotificationQueue();
                    break;
                case 4:
                    notificationService.displayNotificationHistory();
                    break;
                case 5:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Reports submenu.
     */
    private static void reportsMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("REPORTS");
            System.out.println("-".repeat(30));
            System.out.println("1. Patient Visit Report");
            System.out.println("2. Pharmacy Inventory Report");
            System.out.println("3. Doctor Utilization Report");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 4);

            switch (choice) {
                case 1:
                    reportGenerator.generatePatientVisitReport();
                    break;
                case 2:
                    reportGenerator.generatePharmacyInventoryReport();
                    break;
                case 3:
                    reportGenerator.generateDoctorUtilizationReport();
                    break;
                case 4:
                    back = true;
                    break;
            }
        }
    }

    /**
     * Multithreading Demonstration submenu.
     */
    private static void multithreadingDemoMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\n" + "-".repeat(30));
            System.out.println("MULTITHREADING DEMONSTRATION");
            System.out.println("-".repeat(30));
            System.out.println("1. Concurrent Appointment Booking Demo");
            System.out.println("2. Notification Producer-Consumer Demo");
            System.out.println("3. Report Generation Demo");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getUserChoice(1, 4);

            switch (choice) {
                case 1:
                    demoConcurrentAppointmentBooking();
                    break;
                case 2:
                    demoNotificationProducerConsumer();
                    break;
                case 3:
                    demoReportGeneration();
                    break;
                case 4:
                    back = true;
                    break;
            }
        }
    }

    // ========== Helper methods for menu options ==========

    private static void registerPatient() {
        try {
            System.out.println("\n--- Register New Patient ---");
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Gender: ");
            String gender = scanner.nextLine().trim();
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Enter Patient Category (Regular/SeniorCitizen/Emergency): ");
            String patientCategory = scanner.nextLine().trim();
            System.out.print("Enter Medical History: ");
            String medicalHistory = scanner.nextLine().trim();

            Patient patient;
            switch (patientCategory.toLowerCase()) {
                case "seniorcitizen":
                    patient = new SeniorCitizenPatient(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
                    break;
                case "emergency":
                    patient = new EmergencyPatient(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
                    break;
                case "regular":
                default:
                    patient = new Patient(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
                    break;
            }

            patientService.registerPatient(patient);
            System.out.println("Patient registered successfully!");
        } catch (Exception e) {
            System.out.println("Error registering patient: " + e.getMessage());
        }
    }

    private static void searchPatient() {
        try {
            System.out.print("\nEnter Patient ID to search: ");
            String patientId = scanner.nextLine().trim();
            Patient patient = patientService.searchPatient(patientId);
            System.out.println("\n--- Patient Found ---");
            patient.displayPatient();
        } catch (Exception e) {
            System.out.println("Error searching patient: " + e.getMessage());
        }
    }

    private static void updatePatient() {
        try {
            System.out.print("\nEnter Patient ID to update: ");
            String patientId = scanner.nextLine().trim();

            // Check if patient exists
            Patient existingPatient = patientService.searchPatient(patientId);

            System.out.println("\n--- Enter new details (leave blank to keep current value) ---");
            System.out.print("Enter Name [" + existingPatient.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = existingPatient.getName();

            System.out.print("Enter Age [" + existingPatient.getAge() + "]: ");
            String ageInput = scanner.nextLine().trim();
            int age = ageInput.isEmpty() ? existingPatient.getAge() : Integer.parseInt(ageInput);

            System.out.print("Enter Gender [" + existingPatient.getGender() + "]: ");
            String gender = scanner.nextLine().trim();
            if (gender.isEmpty()) gender = existingPatient.getGender();

            System.out.print("Enter Phone [" + existingPatient.getPhone() + "]: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) phone = existingPatient.getPhone();

            System.out.print("Enter Email [" + existingPatient.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) email = existingPatient.getEmail();

            System.out.print("Enter Patient Category [" + existingPatient.getPatientCategory() + "]: ");
            String patientCategory = scanner.nextLine().trim();
            if (patientCategory.isEmpty()) patientCategory = existingPatient.getPatientCategory();

            System.out.print("Enter Medical History [" + existingPatient.getMedicalHistory() + "]: ");
            String medicalHistory = scanner.nextLine().trim();
            if (medicalHistory.isEmpty()) medicalHistory = existingPatient.getMedicalHistory();

            patientService.updatePatient(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
        } catch (Exception e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    private static void registerDoctor() {
        try {
            System.out.println("\n--- Register New Doctor ---");
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter Specialization: ");
            String specialization = scanner.nextLine().trim();
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            System.out.print("Enter Consultation Fee: ");
            double consultationFee = Double.parseDouble(scanner.nextLine().trim());

            Doctor doctor = new Doctor(doctorId, name, specialization, department, consultationFee);

            // Add some default time slots
            doctor.addSlot("09:00 AM");
            doctor.addSlot("10:00 AM");
            doctor.addSlot("11:00 AM");
            doctor.addSlot("02:00 PM");
            doctor.addSlot("03:00 PM");
            doctor.addSlot("04:00 PM");

            doctorService.registerDoctor(doctor);
            System.out.println("Doctor registered successfully!");
        } catch (Exception e) {
            System.out.println("Error registering doctor: " + e.getMessage());
        }
    }

    private static void searchDoctor() {
        try {
            System.out.print("\nEnter Doctor ID to search: ");
            String doctorId = scanner.nextLine().trim();
            Doctor doctor = doctorService.searchDoctor(doctorId);
            System.out.println("\n--- Doctor Found ---");
            doctor.displayDoctor();
        } catch (Exception e) {
            System.out.println("Error searching doctor: " + e.getMessage());
        }
    }

    private static void displayAvailableSlots() {
        try {
            System.out.print("\nEnter Doctor ID to see available slots: ");
            String doctorId = scanner.nextLine().trim();
            doctorService.displayAvailableSlots(doctorId);
        } catch (Exception e) {
            System.out.println("Error displaying available slots: " + e.getMessage());
        }
    }

    private static void bookAppointment() {
        try {
            System.out.println("\n--- Book New Appointment ---");
            System.out.print("Enter Appointment ID: ");
            String appointmentId = scanner.nextLine().trim();
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine().trim();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();
            System.out.print("Enter Time (HH:MM AM/PM): ");
            String time = scanner.nextLine().trim();
            System.out.print("Enter Reason: ");
            String reason = scanner.nextLine().trim();

            appointmentService.bookAppointment(appointmentId, patientId, doctorId, date, time, reason);
            System.out.println("Appointment booked successfully!");
        } catch (Exception e) {
            System.out.println("Error booking appointment: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        try {
            System.out.print("\nEnter Appointment ID to cancel: ");
            String appointmentId = scanner.nextLine().trim();

            boolean result = appointmentService.cancelAppointment(appointmentId);
            if (result) {
                System.out.println("Appointment cancelled successfully!");
            } else {
                System.out.println("Failed to cancel appointment. Appointment may not exist or is not in BOOKED status.");
            }
        } catch (Exception e) {
            System.out.println("Error cancelling appointment: " + e.getMessage());
        }
    }

    private static void addMedicine() {
        try {
            System.out.println("\n--- Add New Medicine ---");
            System.out.print("Enter Medicine ID: ");
            String medicineId = scanner.nextLine().trim();
            System.out.print("Enter Medicine Name: ");
            String medicineName = scanner.nextLine().trim();
            System.out.print("Enter Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Enter Manufacturer: ");
            String manufacturer = scanner.nextLine().trim();
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Enter Reorder Level: ");
            int reorderLevel = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Dosage: ");
            String dosage = scanner.nextLine().trim();

            Medicine medicine = new Medicine(medicineId, medicineName, category, manufacturer, quantity, price, reorderLevel, dosage);
            pharmacyService.addMedicine(medicine);
            System.out.println("Medicine added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding medicine: " + e.getMessage());
        }
    }

    private static void searchMedicine() {
        try {
            System.out.print("\nEnter Medicine ID to search: ");
            String medicineId = scanner.nextLine().trim();
            Medicine medicine = pharmacyService.searchMedicine(medicineId);
            System.out.println("\n--- Medicine Found ---");
            medicine.displayMedicine();
        } catch (Exception e) {
            System.out.println("Error searching medicine: " + e.getMessage());
        }
    }

    private static void updateMedicine() {
        try {
            System.out.print("\nEnter Medicine ID to update: ");
            String medicineId = scanner.nextLine().trim();

            // Check if medicine exists
            Medicine existingMedicine = pharmacyService.searchMedicine(medicineId);

            System.out.println("\n--- Enter new details (leave blank to keep current value) ---");
            System.out.print("Enter Medicine Name [" + existingMedicine.getMedicineName() + "]: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) name = existingMedicine.getMedicineName();

            System.out.print("Enter Category [" + existingMedicine.getCategory() + "]: ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) category = existingMedicine.getCategory();

            System.out.print("Enter Manufacturer [" + existingMedicine.getManufacturer() + "]: ");
            String manufacturer = scanner.nextLine().trim();
            if (manufacturer.isEmpty()) manufacturer = existingMedicine.getManufacturer();

            System.out.print("Enter Quantity [" + existingMedicine.getQuantity() + "]: ");
            String qtyInput = scanner.nextLine().trim();
            int quantity = qtyInput.isEmpty() ? existingMedicine.getQuantity() : Integer.parseInt(qtyInput);

            System.out.print("Enter Price [" + existingMedicine.getPrice() + "]: ");
            String priceInput = scanner.nextLine().trim();
            double price = priceInput.isEmpty() ? existingMedicine.getPrice() : Double.parseDouble(priceInput);

            System.out.print("Enter Reorder Level [" + existingMedicine.getReorderLevel() + "]: ");
            String reorderInput = scanner.nextLine().trim();
            int reorderLevel = reorderInput.isEmpty() ? existingMedicine.getReorderLevel() : Integer.parseInt(reorderInput);

            System.out.print("Enter Dosage [" + existingMedicine.getDosage() + "]: ");
            String dosage = scanner.nextLine().trim();
            if (dosage.isEmpty()) dosage = existingMedicine.getDosage();

            pharmacyService.updateMedicine(medicineId, name, category, manufacturer, quantity, price, reorderLevel, dosage);
        } catch (Exception e) {
            System.out.println("Error updating medicine: " + e.getMessage());
        }
    }

    private static void dispenseMedicine() {
        try {
            System.out.println("\n--- Dispense Medicine ---");
            System.out.print("Enter Medicine ID: ");
            String medicineId = scanner.nextLine().trim();
            System.out.print("Enter Quantity to dispense: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            boolean result = pharmacyService.dispenseMedicine(medicineId, quantity);
            if (result) {
                System.out.println("Medicine dispensed successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error dispensing medicine: " + e.getMessage());
        }
    }

    private static void restockMedicine() {
        try {
            System.out.println("\n--- Restock Medicine ---");
            System.out.print("Enter Medicine ID: ");
            String medicineId = scanner.nextLine().trim();
            System.out.print("Enter Quantity to add: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            boolean result = pharmacyService.restockMedicine(medicineId, quantity);
            if (result) {
                System.out.println("Medicine restocked successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error restocking medicine: " + e.getMessage());
        }
    }

    private static void createAppointmentReminder() {
        try {
            System.out.println("\n--- Create Appointment Reminder ---");
            System.out.print("Enter Patient Name: ");
            String patientName = scanner.nextLine().trim();
            System.out.print("Enter Doctor Name: ");
            String doctorName = scanner.nextLine().trim();
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();
            System.out.print("Enter Time (HH:MM AM/PM): ");
            String time = scanner.nextLine().trim();
            System.out.print("Enter Appointment ID: ");
            String appointmentId = scanner.nextLine().trim();

            notificationService.createAppointmentReminder(patientName, doctorName, date, time, appointmentId);
        } catch (Exception e) {
            System.out.println("Error creating appointment reminder: " + e.getMessage());
        }
    }

    private static void createLowStockNotification() {
        try {
            System.out.println("\n--- Create Low Stock Notification ---");
            System.out.print("Enter Medicine Name: ");
            String medicineName = scanner.nextLine().trim();
            System.out.print("Enter Current Quantity: ");
            int currentQuantity = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter Reorder Level: ");
            int reorderLevel = Integer.parseInt(scanner.nextLine().trim());

            notificationService.createLowStockNotification(medicineName, currentQuantity, reorderLevel);
        } catch (Exception e) {
            System.out.println("Error creating low stock notification: " + e.getMessage());
        }
    }

    private static void demoConcurrentAppointmentBooking() {
        try {
            System.out.println("\n--- Concurrent Appointment Booking Demo ---");
            System.out.println("Simulating two patients trying to book the same doctor/time slot...");

            // Use existing sample data: Patient P001 (Arun Kumar) and Patient P002 (Priya Sharma)
            // trying to book with Doctor D001 (Dr. Meena) at 10:00 AM

            AppointmentBookingTask task1 = new AppointmentBookingTask(
                    appointmentService, patientService, doctorService,
                    "P001", "D001", "2026-09-15", "10:00 AM", "Regular checkup", "Patient 1 (Arun Kumar)");

            AppointmentBookingTask task2 = new AppointmentBookingTask(
                    appointmentService, patientService, doctorService,
                    "P002", "D001", "2026-09-15", "10:00 AM", "Follow-up visit", "Patient 2 (Priya Sharma)");

            // Execute tasks concurrently using thread pool
            executorService.submit(task1);
            executorService.submit(task2);

            // Give some time for tasks to complete
            Thread.sleep(2000);

            System.out.println("\nDemo completed. Check above results to see which booking succeeded.");
        } catch (Exception e) {
            System.out.println("Error in concurrent appointment booking demo: " + e.getMessage());
        }
    }

    private static void demoNotificationProducerConsumer() {
        try {
            System.out.println("\n--- Notification Producer-Consumer Demo ---");

            // Create producer and consumer tasks
            NotificationProducer producer = new NotificationProducer(notificationService, true);
            NotificationConsumer consumer = new NotificationConsumer(notificationService, true);

            // Submit tasks to thread pool
            executorService.submit(producer);
            executorService.submit(consumer);

            // Let them run for a few seconds
            Thread.sleep(5000);

            // Stop the tasks
            producer.stopRunning();
            consumer.stopRunning();

            // Give time for graceful shutdown
            Thread.sleep(1000);

            System.out.println("\nDemo completed. Check above output for producer-consumer interaction.");
        } catch (Exception e) {
            System.out.println("Error in notification producer-consumer demo: " + e.getMessage());
        }
    }

    private static void demoReportGeneration() {
        try {
            System.out.println("\n--- Report Generation Demo ---");

            ReportTask reportTask = new ReportTask(reportGenerator, false);

            // Execute task using thread pool
            executorService.submit(reportTask);

            // Give time for task to complete
            Thread.sleep(3000);

            System.out.println("\nDemo completed. Check above output for generated reports.");
        } catch (Exception e) {
            System.out.println("Error in report generation demo: " + e.getMessage());
        }
    }
}
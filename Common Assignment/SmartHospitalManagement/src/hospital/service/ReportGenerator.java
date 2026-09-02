package hospital.service;

import hospital.exception.*;
import hospital.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service class for generating reports.
 * Demonstrates ArrayList, HashMap, HashSet, Iterator, and Generics.
 */
public class ReportGenerator {
    // References to other services for data
    private PatientService patientService;
    private DoctorService doctorService;
    private AppointmentService appointmentService;
    private PharmacyService pharmacyService;

    public ReportGenerator(PatientService patientService, DoctorService doctorService,
                           AppointmentService appointmentService, PharmacyService pharmacyService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.pharmacyService = pharmacyService;
    }

    /**
     * Generate and display patient visit report.
     */
    public void generatePatientVisitReport() {
        System.out.println("\n=== Patient Visit Report ===");

        // Get all patients
        List<Patient> patients = patientService.getAllPatients();
        int totalPatients = patients.size();

        // Get all appointments
        List<Appointment> appointments = appointmentService.getAllAppointments();
        int totalAppointments = appointments.size();

        // Count appointments by status
        int bookedCount = 0;
        int cancelledCount = 0;
        int completedCount = 0;
        int waitlistedCount = 0;

        Map<String, Integer> appointmentsByDoctor = new HashMap<>();
        Map<String, Integer> appointmentsByPatient = new HashMap<>();

        for (Appointment appointment : appointments) {
            Appointment.Status status = appointment.getStatus();
            switch (status) {
                case BOOKED: bookedCount++; break;
                case CANCELLED: cancelledCount++; break;
                case COMPLETED: completedCount++; break;
                case WAITLISTED: waitlistedCount++; break;
            }

            // Count by doctor
            String doctorId = appointment.getDoctorId();
            appointmentsByDoctor.put(doctorId, appointmentsByDoctor.getOrDefault(doctorId, 0) + 1);

            // Count by patient
            String patientId = appointment.getPatientId();
            appointmentsByPatient.put(patientId, appointmentsByPatient.getOrDefault(patientId, 0) + 1);
        }

        int waitingPatients = waitlistedCount; // Simplified - each waitlisted appointment is a waiting patient

        System.out.println("Total Patients Registered: " + totalPatients);
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Booked Appointments: " + bookedCount);
        System.out.println("Cancelled Appointments: " + cancelledCount);
        System.out.println("Completed Appointments: " + completedCount);
        System.out.println("Waitlisted Appointments: " + waitlistedCount);
        System.out.println("Patients Currently Waiting: " + waitingPatients);

        System.out.println("\n--- Appointments by Doctor ---");
        if (appointmentsByDoctor.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            Iterator<Map.Entry<String, Integer>> doctorIterator = appointmentsByDoctor.entrySet().iterator();
            while (doctorIterator.hasNext()) {
                Map.Entry<String, Integer> entry = doctorIterator.next();
                try {
                    Doctor doctor = doctorService.searchDoctor(entry.getKey());
                    System.out.println("- Dr. " + doctor.getName() + " (" + doctor.getSpecialization() + "): " + entry.getValue() + " appointments");
                } catch (Exception e) {
                    System.out.println("- Doctor ID: " + entry.getKey() + ": " + entry.getValue() + " appointments");
                }
            }
        }

        System.out.println("\n--- Appointments by Patient ---");
        if (appointmentsByPatient.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            Iterator<Map.Entry<String, Integer>> patientIterator = appointmentsByPatient.entrySet().iterator();
            while (patientIterator.hasNext()) {
                Map.Entry<String, Integer> entry = patientIterator.next();
                try {
                    Patient patient = patientService.searchPatient(entry.getKey());
                    System.out.println("- " + patient.getName() + ": " + entry.getValue() + " appointments");
                } catch (Exception e) {
                    System.out.println("- Patient ID: " + entry.getKey() + ": " + entry.getValue() + " appointments");
                }
            }
        }
    }

    /**
     * Generate and display pharmacy inventory report.
     * This delegates to the pharmacy service's inventory report.
     */
    public void generatePharmacyInventoryReport() {
        pharmacyService.displayInventoryReport();
    }

    /**
     * Generate and display doctor utilization report.
     */
    public void generateDoctorUtilizationReport() {
        System.out.println("\n=== Doctor Utilization Report ===");

        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }

        List<Appointment> appointments = appointmentService.getAllAppointments();

        System.out.printf("%-5s %-20s %-15s %-10s %-10s %-10s %-15s%n",
                "ID", "Doctor Name", "Specialization", "Total Slots", "Booked", "Available", "Utilization %");
        System.out.println("-----------------------------------------------------------------------------------------------");

        for (Doctor doctor : doctors) {
            String doctorId = doctor.getDoctorId();
            int bookedSlots = 0;

            // Count booked appointments for this doctor
            for (Appointment appointment : appointments) {
                if (appointment.getDoctorId().equals(doctorId) &&
                    appointment.getStatus() == Appointment.Status.BOOKED) {
                    bookedSlots++;
                }
            }

            int totalSlots = doctor.getAvailableSlots().size() + bookedSlots;
            int availableSlots = doctor.getAvailableSlots().size();
            double utilizationPercent = (totalSlots > 0) ? ((double) bookedSlots / totalSlots) * 100 : 0;

            System.out.printf("%-5s %-20s %-15s %-10d %-10d %-10d %-14.1f%%%n",
                    doctorId,
                    doctor.getName(),
                    doctor.getSpecialization(),
                    totalSlots,
                    bookedSlots,
                    availableSlots,
                    utilizationPercent);
        }
    }

    /**
     * Generate and display low stock report from pharmacy.
     * This delegates to the pharmacy service's low stock report.
     */
    public void generateLowStockReport() {
        pharmacyService.displayLowStockReport();
    }

    /**
     * Generate and display waitlist report.
     */
    public void generateWaitlistReport() {
        System.out.println("\n=== Appointment Waitlist Report ===");

        List<Doctor> doctors = doctorService.getAllDoctors();
        boolean hasWaitlist = false;

        for (Doctor doctor : doctors) {
            String doctorId = doctor.getDoctorId();
            // We would need to access the waitlist from appointment service
            // For simplicity, we'll check if there are any waitlisted appointments
            List<Appointment> appointments = appointmentService.getDoctorAppointments(doctorId);
            int waitlistedCount = 0;
            for (Appointment appointment : appointments) {
                if (appointment.getStatus() == Appointment.Status.WAITLISTED) {
                    waitlistedCount++;
                }
            }

            if (waitlistedCount > 0) {
                if (!hasWaitlist) {
                    hasWaitlist = true;
                }
                System.out.println("- Dr. " + doctor.getName() + " (" + doctor.getSpecialization() + "): " +
                                  waitlistedCount + " patient(s) waiting");
            }
        }

        if (!hasWaitlist) {
            System.out.println("No patients currently on waitlist.");
        }
    }
}
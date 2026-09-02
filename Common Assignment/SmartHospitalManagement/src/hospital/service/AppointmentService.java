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
import java.util.LinkedList;
import java.util.Queue;

/**
 * Service class for managing appointment operations.
 * Demonstrates ArrayList, HashMap, HashSet, Iterator, and Generics.
 * Also demonstrates Queue for waitlist functionality.
 */
public class AppointmentService {
    // Collections for storing appointments
    private List<Appointment> appointmentList = new ArrayList<>();
    private Map<String, Appointment> appointmentMap = new HashMap<>(); // appointmentId -> Appointment
    private Map<String, List<Appointment>> patientAppointments = new HashMap<>(); // patientId -> List of appointments
    private Map<String, List<Appointment>> doctorAppointments = new HashMap<>(); // doctorId -> List of appointments
    private Map<String, Queue<String>> waitlist = new HashMap<>(); // doctorId -> Queue of patientIds waiting for a slot

    // References to other services for validation
    private PatientService patientService;
    private DoctorService doctorService;

    public AppointmentService(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    /**
     * Book an appointment.
     * @param appointmentId ID of appointment
     * @param patientId ID of patient
     * @param doctorId ID of doctor
     * @param date Date of appointment
     * @param time Time of appointment
     * @param reason Reason for appointment
     * @return Appointment object if successful
     * @throws Exception if validation fails
     */
    public Appointment bookAppointment(String appointmentId, String patientId, String doctorId, String date, String time, String reason) throws Exception {
        // Validate patient and doctor exist
        Patient patient = patientService.searchPatient(patientId);
        Doctor doctor = doctorService.searchDoctor(doctorId);

        // Check if appointment ID already exists
        if (appointmentMap.containsKey(appointmentId)) {
            throw new InvalidAppointmentException("Appointment ID already exists: " + appointmentId);
        }

        // Check if the time slot is available for the doctor
        if (!doctor.checkAvailability(time)) {
            throw new InvalidAppointmentException("Time slot not available for doctor: " + time);
        }

        // Check if patient already has an appointment at this time
        if (hasPatientAppointmentAtTime(patientId, date, time)) {
            throw new InvalidAppointmentException("Patient already has an appointment at this time: " + date + " " + time);
        }

        // Create appointment
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, date, time, Appointment.Status.BOOKED, reason);

        // Add to collections
        appointmentList.add(appointment);
        appointmentMap.put(appointmentId, appointment);

        // Update patient appointments map
        patientAppointments.computeIfAbsent(patientId, k -> new ArrayList<>()).add(appointment);

        // Update doctor appointments map
        doctorAppointments.computeIfAbsent(doctorId, k -> new ArrayList<>()).add(appointment);

        // Book the slot in doctor's schedule
        doctor.bookSlot(time);

        return appointment;
    }

    /**
     * Cancel an appointment.
     * @param appointmentId ID of appointment to cancel
     * @return true if successful
     */
    public boolean cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentMap.get(appointmentId);
        if (appointment == null) {
            return false;
        }

        // Only allow cancelling booked appointments
        if (appointment.getStatus() != Appointment.Status.BOOKED) {
            return false;
        }

        // Update appointment status
        appointment.cancel();

        // Free up the doctor's slot
        try {
            Doctor doctor = doctorService.searchDoctor(appointment.getDoctorId());
            doctor.cancelSlot(appointment.getTime());
        } catch (DoctorNotFoundException e) {
            // Doctor not found - this shouldn't happen if data is consistent,
            // but we'll continue anyway as the appointment is already cancelled
            System.err.println("Warning: Doctor not found when cancelling appointment: " + e.getMessage());
        }

        // Process waitlist for this doctor's time slot
        processWaitlist(appointment.getDoctorId(), appointment.getTime());

        return true;
    }

    /**
     * Process waitlist when a slot becomes available.
     * @param doctorId ID of doctor
     * @param time Time slot that became available
     */
    private void processWaitlist(String doctorId, String time) {
        Queue<String> patientWaitlist = waitlist.get(doctorId);
        if (patientWaitlist != null && !patientWaitlist.isEmpty()) {
            String nextPatientId = patientWaitlist.poll();
            // In a real system, we would automatically book the appointment for the next patient
            // For simplicity, we'll just notify that a slot is available
            System.out.println("Slot " + time + " is now available for patient " + nextPatientId + " (from waitlist)");

            // If waitlist is empty, remove it to save space
            if (patientWaitlist.isEmpty()) {
                waitlist.remove(doctorId);
            }
        }
    }

    /**
     * Add patient to waitlist for a doctor's time slot.
     * @param doctorId ID of doctor
     * @param patientId ID of patient
     * @param time Time slot desired
     */
    public void addToWaitlist(String doctorId, String patientId, String time) {
        waitlist.computeIfAbsent(doctorId, k -> new LinkedList<>()).add(patientId);
        System.out.println("Patient " + patientId + " added to waitlist for Dr. " + doctorId + " at " + time);
    }

    /**
     * Display all appointments.
     */
    public void displayAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\n=== Appointment List ===");
        for (Appointment appointment : appointmentList) {
            appointment.displayAppointment();
            System.out.println("-------------------");
        }
    }

    /**
     * Search for an appointment by ID.
     * @param appointmentId ID of appointment to search for
     * @return Appointment object if found
     * @throws Exception if appointment not found
     */
    public Appointment searchAppointment(String appointmentId) throws Exception {
        Appointment appointment = appointmentMap.get(appointmentId);
        if (appointment == null) {
            throw new Exception("Appointment not found with ID: " + appointmentId);
        }
        return appointment;
    }

    /**
     * Get all appointments.
     * @return List of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentList);
    }

    /**
     * Get appointments for a specific patient.
     * @param patientId ID of patient
     * @return List of appointments for the patient
     */
    public List<Appointment> getPatientAppointments(String patientId) {
        return patientAppointments.getOrDefault(patientId, new ArrayList<>());
    }

    /**
     * Get appointments for a specific doctor.
     * @param doctorId ID of doctor
     * * @return List of appointments for the doctor
     */
    public List<Appointment> getDoctorAppointments(String doctorId) {
        return doctorAppointments.getOrDefault(doctorId, new ArrayList<>());
    }

    /**
     * Display waitlist for a doctor.
     * @param doctorId ID of doctor
     */
    public void displayWaitlist(String doctorId) {
        Queue<String> patientWaitlist = waitlist.get(doctorId);
        if (patientWaitlist == null || patientWaitlist.isEmpty()) {
            System.out.println("No patients in waitlist for Dr. " + doctorId);
            return;
        }
        System.out.println("\n=== Waitlist for Dr. " + doctorId + " ===");
        Iterator<String> iterator = patientWaitlist.iterator();
        while (iterator.hasNext()) {
            System.out.println("- Patient ID: " + iterator.next());
        }
    }

    /**
     * Check if patient already has an appointment at a specific time.
     * @param patientId ID of patient
     * @param date Date of appointment
     * @param time Time of appointment
     * @return true if patient has appointment at this time
     */
    private boolean hasPatientAppointmentAtTime(String patientId, String date, String time) {
        List<Appointment> patientApps = getPatientAppointments(patientId);
        for (Appointment appointment : patientApps) {
            if (appointment.getDate().equals(date) && appointment.getTime().equals(time) &&
                appointment.getStatus() == Appointment.Status.BOOKED) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get count of appointments.
     * @return Number of appointments
     */
    public int getAppointmentCount() {
        return appointmentList.size();
    }
}
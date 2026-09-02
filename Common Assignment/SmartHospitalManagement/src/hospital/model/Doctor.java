package hospital.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Doctor class representing a hospital doctor.
 * Demonstrates encapsulation and basic attributes.
 */
public class Doctor {
    private String doctorId;
    private String name;
    private String specialization;
    private String department;
    private double consultationFee;
    private List<String> availableSlots; // List of available time slots (e.g., "10:00 AM")

    // Constructors
    public Doctor() {
        this.availableSlots = new ArrayList<>();
    }

    public Doctor(String doctorId, String name, String specialization, String department,
                  double consultationFee) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.department = department;
        this.consultationFee = consultationFee;
        this.availableSlots = new ArrayList<>();
    }

    public Doctor(String doctorId, String name, String specialization, String department,
                  double consultationFee, List<String> availableSlots) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.department = department;
        this.consultationFee = consultationFee;
        this.availableSlots = availableSlots != null ? availableSlots : new ArrayList<>();
    }

    // Getters and Setters
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(List<String> availableSlots) {
        this.availableSlots = availableSlots;
    }

    // Methods
    /**
     * Add a time slot to the doctor's available slots.
     * @param slot time slot to add
     */
    public void addSlot(String slot) {
        if (!availableSlots.contains(slot)) {
            availableSlots.add(slot);
        }
    }

    /**
     * Remove a time slot from the doctor's available slots.
     * @param slot time slot to remove
     */
    public void removeSlot(String slot) {
        availableSlots.remove(slot);
    }

    public void displayDoctor() {
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Name: " + name);
        System.out.println("Specialization: " + specialization);
        System.out.println("Department: " + department);
        System.out.println("Consultation Fee: " + consultationFee);
        System.out.println("Available Slots: " + availableSlots);
    }

    /**
     * Check if a given slot is available.
     * @param slot time slot to check
     * @return true if available
     */
    public boolean checkAvailability(String slot) {
        return availableSlots.contains(slot);
    }

    /**
     * Book a slot (remove from available slots).
     * @param slot time slot to book
     * @return true if successfully booked
     */
    public boolean bookSlot(String slot) {
        return availableSlots.remove(slot);
    }

    /**
     * Cancel a slot (add back to available slots).
     * @param slot time slot to cancel
     */
    public void cancelSlot(String slot) {
        if (!availableSlots.contains(slot)) {
            availableSlots.add(slot);
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", department='" + department + '\'' +
                ", consultationFee=" + consultationFee +
                ", availableSlots=" + availableSlots +
                '}';
    }
}
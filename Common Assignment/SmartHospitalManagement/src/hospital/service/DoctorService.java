package hospital.service;

import hospital.exception.DoctorNotFoundException;
import hospital.exception.InvalidPatientException;
import hospital.model.Doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service class for managing doctor operations.
 * Demonstrates ArrayList, HashMap, HashSet, Iterator, and Generics.
 */
public class DoctorService {
    // Collections for storing doctors
    private List<Doctor> doctorList = new ArrayList<>();
    private Map<String, Doctor> doctorMap = new HashMap<>(); // doctorId -> Doctor
    private Set<String> doctorIds = new HashSet<>(); // Unique doctor IDs
    private Set<String> specializations = new HashSet<>(); // Unique specializations

    /**
     * Register a new doctor.
     * @param doctor Doctor object to register
     * @throws InvalidPatientException if doctor data is invalid (reusing for simplicity)
     */
    public void registerDoctor(Doctor doctor) throws InvalidPatientException {
        validateDoctor(doctor);
        if (doctorIds.contains(doctor.getDoctorId())) {
            throw new InvalidPatientException("Doctor ID already exists: " + doctor.getDoctorId());
        }
        doctorList.add(doctor);
        doctorMap.put(doctor.getDoctorId(), doctor);
        doctorIds.add(doctor.getDoctorId());
        if (doctor.getSpecialization() != null) {
            specializations.add(doctor.getSpecialization());
        }
    }

    /**
     * Validate doctor data.
     * @param doctor Doctor to validate
     * @throws InvalidPatientException if validation fails
     */
    private void validateDoctor(Doctor doctor) throws InvalidPatientException {
        if (doctor == null) {
            throw new InvalidPatientException("Doctor cannot be null");
        }
        if (doctor.getDoctorId() == null || doctor.getDoctorId().trim().isEmpty()) {
            throw new InvalidPatientException("Doctor ID is required");
        }
        if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
            throw new InvalidPatientException("Doctor name is required");
        }
        if (doctor.getConsultationFee() < 0) {
            throw new InvalidPatientException("Consultation fee cannot be negative");
        }
    }

    /**
     * Display all doctors.
     */
    public void displayDoctors() {
        if (doctorList.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }
        System.out.println("\n=== Doctor List ===");
        for (Doctor doctor : doctorList) {
            doctor.displayDoctor();
            System.out.println("-------------------");
        }
    }

    /**
     * Search for a doctor by ID.
     * @param doctorId ID of doctor to search for
     * @return Doctor object if found
     * @throws DoctorNotFoundException if doctor not found
     */
    public Doctor searchDoctor(String doctorId) throws DoctorNotFoundException {
        Doctor doctor = doctorMap.get(doctorId);
        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
        }
        return doctor;
    }

    /**
     * Get all doctors.
     * @return List of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorList);
    }

    /**
     * Display available appointment slots for a doctor.
     * @param doctorId ID of doctor
     */
    public void displayAvailableSlots(String doctorId) {
        try {
            Doctor doctor = searchDoctor(doctorId);
            System.out.println("Available slots for Dr. " + doctor.getName() + ":");
            if (doctor.getAvailableSlots().isEmpty()) {
                System.out.println("No available slots.");
            } else {
                for (String slot : doctor.getAvailableSlots()) {
                    System.out.println("- " + slot);
                }
            }
        } catch (DoctorNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Book a slot for a doctor.
     * @param doctorId ID of doctor
     * @param slot Time slot to book
     * @return true if successfully booked
     */
    public boolean bookDoctorSlot(String doctorId, String slot) {
        try {
            Doctor doctor = searchDoctor(doctorId);
            if (doctor.checkAvailability(slot)) {
                doctor.bookSlot(slot);
                return true;
            }
            return false;
        } catch (DoctorNotFoundException e) {
            return false;
        }
    }

    /**
     * Cancel a slot for a doctor.
     * @param doctorId ID of doctor
     * @param slot Time slot to cancel
     */
    public void cancelDoctorSlot(String doctorId, String slot) {
        try {
            Doctor doctor = searchDoctor(doctorId);
            doctor.cancelSlot(slot);
        } catch (DoctorNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Demonstrate Iterator usage for searching records.
     * @param doctorId ID to search for
     * @return Doctor if found
     * @throws DoctorNotFoundException if not found
     */
    public Doctor searchDoctorWithIterator(String doctorId) throws DoctorNotFoundException {
        Iterator<Doctor> iterator = doctorList.iterator();
        while (iterator.hasNext()) {
            Doctor doctor = iterator.next();
            if (doctor.getDoctorId().equals(doctorId)) {
                return doctor;
            }
        }
        throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
    }

    /**
     * Get count of doctors.
     * @return Number of doctors
     */
    public int getDoctorCount() {
        return doctorList.size();
    }

    /**
     * Get unique specializations using HashSet.
     * @return Set of unique specializations
     */
    public Set<String> getUniqueSpecializations() {
        return new HashSet<>(specializations);
    }

    /**
     * Get doctors by specialization.
     * @param specialization Specialization to filter by
     * @return List of doctors with given specialization
     */
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if (specialization.equalsIgnoreCase(doctor.getSpecialization())) {
                result.add(doctor);
            }
        }
        return result;
    }
}
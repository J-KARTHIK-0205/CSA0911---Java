package hospital.service;

import hospital.exception.InvalidPatientException;
import hospital.exception.PatientNotFoundException;
import hospital.model.EmergencyPatient;
import hospital.model.Patient;
import hospital.model.RegularPatient;
import hospital.model.SeniorCitizenPatient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Service class for managing patient operations.
 * Demonstrates ArrayList, HashMap, HashSet, Iterator, ListIterator, and Generics.
 */
public class PatientService {
    // Collections for storing patients
    private List<Patient> patientList = new ArrayList<>();
    private Map<String, Patient> patientMap = new HashMap<>(); // patientId -> Patient
    private Set<String> patientIds = new HashSet<>(); // Unique patient IDs

    /**
     * Register a new patient.
     * @param patient Patient object to register
     * @throws InvalidPatientException if patient data is invalid
     */
    public void registerPatient(Patient patient) throws InvalidPatientException {
        validatePatient(patient);
        if (patientIds.contains(patient.getPatientId())) {
            throw new InvalidPatientException("Patient ID already exists: " + patient.getPatientId());
        }
        patientList.add(patient);
        patientMap.put(patient.getPatientId(), patient);
        patientIds.add(patient.getPatientId());
    }

    /**
     * Validate patient data.
     * @param patient Patient to validate
     * @throws InvalidPatientException if validation fails
     */
    private void validatePatient(Patient patient) throws InvalidPatientException {
        if (patient == null) {
            throw new InvalidPatientException("Patient cannot be null");
        }
        if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
            throw new InvalidPatientException("Patient ID is required");
        }
        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            throw new InvalidPatientException("Patient name is required");
        }
        if (patient.getAge() <= 0) {
            throw new InvalidPatientException("Age must be positive");
        }
        // Additional validations can be added
    }

    /**
     * Display all patients.
     */
    public void displayPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("\n=== Patient List ===");
        for (Patient patient : patientList) {
            patient.displayPatient();
            System.out.println("-------------------");
        }
    }

    /**
     * Search for a patient by ID.
     * @param patientId ID of patient to search for
     * @return Patient object if found
     * @throws PatientNotFoundException if patient not found
     */
    public Patient searchPatient(String patientId) throws PatientNotFoundException {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            throw new PatientNotFoundException("Patient not found with ID: " + patientId);
        }
        return patient;
    }

    /**
     * Update patient information.
     * @param patientId ID of patient to update
     * @param name New name
     * @param age New age
     * @param gender New gender
     * @param phone New phone
     * @param email New email
     * @param patientCategory New patient category
     * @param medicalHistory New medical history
     * @throws PatientNotFoundException if patient not found
     */
    public void updatePatient(String patientId, String name, int age, String gender, String phone, String email,
                              String patientCategory, String medicalHistory) throws PatientNotFoundException {
        Patient patient = searchPatient(patientId);
        patient.updatePatient(name, age, gender, phone, email, patientCategory, medicalHistory);
        System.out.println("Patient updated successfully.");
    }

    /**
     * Get all patients.
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientList);
    }

    /**
     * Demonstrate Iterator usage for searching records.
     * @param patientId ID to search for
     * @return Patient if found
     * @throws PatientNotFoundException if not found
     */
    public Patient searchPatientWithIterator(String patientId) throws PatientNotFoundException {
        Iterator<Patient> iterator = patientList.iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        throw new PatientNotFoundException("Patient not found with ID: " + patientId);
    }

    /**
     * Demonstrate ListIterator usage for updating/traversal.
     * This example updates the first patient's name to add "[Updated]" prefix.
     */
    public void demonstrateListIterator() {
        if (patientList.isEmpty()) {
            System.out.println("No patients to demonstrate ListIterator.");
            return;
        }

        ListIterator<Patient> listIterator = patientList.listIterator();
        if (listIterator.hasNext()) {
            Patient firstPatient = listIterator.next();
            String originalName = firstPatient.getName();
            firstPatient.setName(originalName + " [Updated]");
            System.out.println("Updated first patient name using ListIterator: " +
                              originalName + " -> " + firstPatient.getName());
        }
    }

    /**
     * Remove a patient (example of Iterator remove operation).
     * @param patientId ID of patient to remove
     * @return true if removed
     */
    public boolean removePatient(String patientId) {
        Iterator<Patient> iterator = patientList.iterator();
        while (iterator.hasNext()) {
            Patient patient = iterator.next();
            if (patient.getPatientId().equals(patientId)) {
                iterator.remove(); // Using Iterator's remove method
                patientMap.remove(patientId);
                patientIds.remove(patientId);
                return true;
            }
        }
        return false;
    }

    /**
     * Get count of patients.
     * @return Number of patients
     */
    public int getPatientCount() {
        return patientList.size();
    }

    /**
     * Get unique patient categories using HashSet.
     * @return Set of unique patient categories
     */
    public Set<String> getUniquePatientCategories() {
        Set<String> categories = new HashSet<>();
        for (Patient patient : patientList) {
            if (patient.getPatientCategory() != null) {
                categories.add(patient.getPatientCategory());
            }
        }
        return categories;
    }
}
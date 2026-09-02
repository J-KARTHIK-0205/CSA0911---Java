package hospital.model;

/**
 * EmergencyPatient extends Patient.
 * Demonstrates inheritance and method overriding.
 */
public class EmergencyPatient extends Patient {
    private static final double CONSULTATION_FEE = 1000.0; // Emergency fee

    public EmergencyPatient() {
    }

    public EmergencyPatient(String patientId, String name, int age, String gender, String phone, String email,
                            String patientCategory, String medicalHistory) {
        super(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
    }

    /**
     * Override to calculate consultation fee for emergency patient.
     * @return consultation fee
     */
    public double calculateConsultationFee() {
        return CONSULTATION_FEE;
    }

    /**
     * Override to get appointment priority.
     * Emergency patients get highest priority.
     * @return priority value
     */
    public int getAppointmentPriority() {
        return 0; // Highest priority
    }

    @Override
    public void displayPatient() {
        super.displayPatient();
        System.out.println("Consultation Fee: " + calculateConsultationFee());
        System.out.println("Patient Type: Emergency");
    }
}
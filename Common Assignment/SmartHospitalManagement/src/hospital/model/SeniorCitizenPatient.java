package hospital.model;

/**
 * SeniorCitizenPatient extends Patient.
 * Demonstrates inheritance and method overriding.
 */
public class SeniorCitizenPatient extends Patient {
    private static final double CONSULTATION_FEE = 250.0; // Discounted fee

    public SeniorCitizenPatient() {
    }

    public SeniorCitizenPatient(String patientId, String name, int age, String gender, String phone, String email,
                                String patientCategory, String medicalHistory) {
        super(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
    }

    /**
     * Override to calculate consultation fee for senior citizen patient.
     * @return consultation fee
     */
    public double calculateConsultationFee() {
        return CONSULTATION_FEE;
    }

    /**
     * Override to get appointment priority.
     * Senior citizens get higher priority (lower number).
     * @return priority value
     */
    public int getAppointmentPriority() {
        return 1; // Higher priority
    }

    @Override
    public void displayPatient() {
        super.displayPatient();
        System.out.println("Consultation Fee: " + calculateConsultationFee());
        System.out.println("Patient Type: Senior Citizen");
    }
}
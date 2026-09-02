package hospital.model;

/**
 * RegularPatient extends Patient.
 * Demonstrates inheritance and method overriding.
 */
public class RegularPatient extends Patient {
    private static final double CONSULTATION_FEE = 500.0; // Normal fee

    public RegularPatient() {
    }

    public RegularPatient(String patientId, String name, int age, String gender, String phone, String email,
                          String patientCategory, String medicalHistory) {
        super(patientId, name, age, gender, phone, email, patientCategory, medicalHistory);
    }

    /**
     * Override to calculate consultation fee for regular patient.
     * @return consultation fee
     */
    public double calculateConsultationFee() {
        return CONSULTATION_FEE;
    }

    /**
     * Override to get appointment priority (lower number means higher priority).
     * Regular patients have normal priority.
     * @return priority value
     */
    public int getAppointmentPriority() {
        return 2; // Normal priority
    }

    @Override
    public void displayPatient() {
        super.displayPatient();
        System.out.println("Consultation Fee: " + calculateConsultationFee());
        System.out.println("Patient Type: Regular");
    }
}
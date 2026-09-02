package hospital.exception;

/**
 * Custom exception for when a patient is not found.
 */
public class PatientNotFoundException extends Exception {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
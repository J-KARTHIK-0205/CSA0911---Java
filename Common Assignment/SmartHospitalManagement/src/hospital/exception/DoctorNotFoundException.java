package hospital.exception;

/**
 * Custom exception for when a doctor is not found.
 */
public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
package hospital.exception;

/**
 * Custom exception for duplicate appointment attempts.
 */
public class DuplicateAppointmentException extends Exception {
    public DuplicateAppointmentException(String message) {
        super(message);
    }
}
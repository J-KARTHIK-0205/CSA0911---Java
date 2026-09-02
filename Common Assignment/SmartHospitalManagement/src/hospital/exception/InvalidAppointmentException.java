package hospital.exception;

/**
 * Custom exception for invalid appointment data.
 */
public class InvalidAppointmentException extends Exception {
    public InvalidAppointmentException(String message) {
        super(message);
    }
}
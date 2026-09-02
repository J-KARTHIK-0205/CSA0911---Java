package hospital.exception;

/**
 * Custom exception for invalid patient data.
 */
public class InvalidPatientException extends Exception {
    public InvalidPatientException(String message) {
        super(message);
    }
}
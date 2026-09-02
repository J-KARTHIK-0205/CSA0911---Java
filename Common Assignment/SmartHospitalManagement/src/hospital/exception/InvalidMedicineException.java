package hospital.exception;

/**
 * Custom exception for invalid medicine data.
 */
public class InvalidMedicineException extends Exception {
    public InvalidMedicineException(String message) {
        super(message);
    }
}
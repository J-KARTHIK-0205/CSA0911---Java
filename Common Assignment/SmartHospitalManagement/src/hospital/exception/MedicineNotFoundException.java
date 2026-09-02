package hospital.exception;

/**
 * Custom exception for when a medicine is not found.
 */
public class MedicineNotFoundException extends Exception {
    public MedicineNotFoundException(String message) {
        super(message);
    }
}
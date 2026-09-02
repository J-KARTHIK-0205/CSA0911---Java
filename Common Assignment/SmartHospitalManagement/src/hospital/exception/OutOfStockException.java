package hospital.exception;

/**
 * Custom exception for out of stock medicines.
 */
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
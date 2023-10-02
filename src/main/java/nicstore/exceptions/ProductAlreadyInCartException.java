package nicstore.exceptions;

public class ProductAlreadyInCartException extends RuntimeException {

    public ProductAlreadyInCartException(String message) {
        super(message);
    }
}

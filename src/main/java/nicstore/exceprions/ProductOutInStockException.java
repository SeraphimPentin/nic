package nicstore.exceprions;

public class ProductOutInStockException extends RuntimeException {
    public ProductOutInStockException(String message){
        super(message);
    }
}

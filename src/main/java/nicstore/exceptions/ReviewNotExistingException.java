package nicstore.exceptions;

public class ReviewNotExistingException extends RuntimeException {

    public ReviewNotExistingException(String message){
        super(message);
    }
}

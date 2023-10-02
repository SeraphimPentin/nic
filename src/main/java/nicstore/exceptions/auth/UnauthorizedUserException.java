package nicstore.exceptions.auth;

public class UnauthorizedUserException extends RuntimeException{

    public UnauthorizedUserException(String message){
        super(message);
    }
}
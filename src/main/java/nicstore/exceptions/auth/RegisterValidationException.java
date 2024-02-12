package nicstore.exceptions.auth;

public class RegisterValidationException extends RuntimeException{

    public RegisterValidationException(String message){
        super(message);
    }
}
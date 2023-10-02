package nicstore.exceptions.auth;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message){
        super(message);
    }
}

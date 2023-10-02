package nicstore.exceptions.auth;

public class UserFailedAuthenticationException extends  RuntimeException{

    public UserFailedAuthenticationException(String message){
        super(message);
    }
}

package nicstore.exceprions.auth;

import java.util.Map;

public class FormException extends  RuntimeException{

    private final Map<String, String> errors;

    public FormException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

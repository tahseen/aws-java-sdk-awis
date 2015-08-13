package amazon.services.awis;

import java.util.LinkedHashMap;
import java.util.Map;

public class Result {
    protected Map<String, String> errors = new LinkedHashMap<String, String>();
    protected String response;
    
    public Result(String response) {
        this.response = response;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
    
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
}

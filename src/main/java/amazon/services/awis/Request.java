package amazon.services.awis;

import java.util.ArrayList;
import java.util.List;

import amazon.services.awis.enums.Action;
import amazon.services.awis.enums.ResponseGroup;

/**
 * Base class for all Action Request objects.
 * 
 * @author Tahseen Ur Rehman Fida
 */
public class Request {
    private Action action;
    private List<ResponseGroup> responseGroups;
    
    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public List<ResponseGroup> getResponseGroups() {
        return responseGroups;
    }

    public void setResponseGroups(List<ResponseGroup> responseGroups) {
        this.responseGroups = responseGroups;
    }
    
    public void addResponseGroup(ResponseGroup responseGroup) {
        if(this.responseGroups == null) {
            this.responseGroups = new ArrayList<>();
        }
        this.responseGroups.add(responseGroup);
    }
    
}

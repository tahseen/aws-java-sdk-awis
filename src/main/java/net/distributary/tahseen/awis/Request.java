package net.distributary.tahseen.awis;

import java.util.ArrayList;
import java.util.List;

import net.distributary.tahseen.awis.enums.Action;

/**
 * Base class for all Action Request objects.
 * 
 * @author Tahseen Ur Rehman Fida
 */
public class Request<T> {
    private Action action;
    private List<T> responseGroups;
    
    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    public List<T> getResponseGroups() {
        return responseGroups;
    }

    public void setResponseGroups(List<T> responseGroups) {
        this.responseGroups = responseGroups;
    }
    
    public void addResponseGroup(T responseGroup) {
        if(this.responseGroups == null) {
            this.responseGroups = new ArrayList<>();
        }
        this.responseGroups.add(responseGroup);
    }
}

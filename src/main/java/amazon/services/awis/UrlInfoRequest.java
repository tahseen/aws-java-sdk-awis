package amazon.services.awis;

import amazon.services.awis.enums.Action;
import amazon.services.awis.enums.UrlInfoResponseGroup;

/**
 * The request object for {@link Action.UrlInfo}
 * 
 * @author Tahseen Ur Rehman Fida
 */
public class UrlInfoRequest extends Request<UrlInfoResponseGroup> {
    /**
     * Any valid URL.
     */
    private String url;
    
    public UrlInfoRequest() {
        setAction(Action.UrlInfo);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

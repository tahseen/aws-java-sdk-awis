package amazon.services.awis;

import amazon.services.awis.enums.Action;

/**
 * The request object for {@link Action.UrlInfo}
 * 
 * @author Tahseen Ur Rehman Fida
 */
public class UrlInfoRequest extends Request {
    /**
     * Any valid URL.
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

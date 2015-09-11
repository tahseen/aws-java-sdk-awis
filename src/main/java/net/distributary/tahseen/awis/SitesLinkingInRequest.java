package net.distributary.tahseen.awis;

import net.distributary.tahseen.awis.enums.Action;
import net.distributary.tahseen.awis.enums.SitesLinkingInResponseGroup;

public class SitesLinkingInRequest extends Request<SitesLinkingInResponseGroup> {
    /**
     * Any valid URL.
     */
    private String url;
    
    /**
	 * 1-based index of result at which to start. Note: An empty document will be returned if this value exceeds the total number of available results.
	 */
	private Integer start;	
	
	/**
	 * Number of result at which to start. Used for paging through results. Default value is '0.'
	 */
	private Integer count;	
	
	public SitesLinkingInRequest() {	
		setAction(Action.SitesLinkingIn);
		addResponseGroup(SitesLinkingInResponseGroup.SitesLinkingIn);
	}

	public Integer getStart() {
		return start;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

package net.distributary.tahseen.awis;

import net.distributary.tahseen.awis.enums.Action;
import net.distributary.tahseen.awis.enums.CategoryBrowseResponseGroup;

public class CategoryBrowseRequest extends Request<CategoryBrowseResponseGroup> {
	/**
	 * Valid category path (Top/Arts, Top/Business/Automotive)
	 */
	private String path;
	
	/**
	 * Whether to return descriptions with categories: (True | False)
	 */
	private Boolean descriptions;

    public CategoryBrowseRequest() {
        setAction(Action.CategoryBrowse);
    }
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Boolean descriptions) {
		this.descriptions = descriptions;
	}
}

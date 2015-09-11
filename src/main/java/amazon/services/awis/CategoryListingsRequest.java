package amazon.services.awis;

import amazon.services.awis.enums.Action;
import amazon.services.awis.enums.CategoryListingsResponseGroup;
import amazon.services.awis.enums.SortBy;

public class CategoryListingsRequest extends Request<CategoryListingsResponseGroup> {
	/**
	 * Valid category path. Note that top-level categories will not return any listings unless Recursive=yes is specified (see below). 
	 * Example values are Top/Arts, Top/Business/Automotive
	 */
	private String path;
	
	/**
	 * How to sort the results returned by this service: ( Popularity | Title | AverageReview )
	 */
	private SortBy sortBy;
	
	/**
	 * Whether to return listings for the current category only, or for the current category plus all subcategories: (True | False)
	 */
	private Boolean recursive;
	
	/**
	 * 1-based index of result at which to start. Note: An empty document will be returned if this value exceeds the total number of available results.
	 */
	private Integer start;	
	
	/**
	 * Number of results to return for this request, beginning from specified Start number (maximum 20)
	 */
	private Integer count;	
	
	/**
	 * Whether to return descriptions with categories: (True | False)
	 */
	private Boolean descriptions;
	
	public CategoryListingsRequest() {	
		setAction(Action.CategoryListings);
		addResponseGroup(CategoryListingsResponseGroup.Listings);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public SortBy getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}

	public Boolean getRecursive() {
		return recursive;
	}

	public void setRecursive(Boolean recursive) {
		this.recursive = recursive;
	}

	public Integer getStart() {
		return start;
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

	public Boolean getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Boolean descriptions) {
		this.descriptions = descriptions;
	}
}

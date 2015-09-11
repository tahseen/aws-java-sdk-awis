package amazon.services.awis.enums;

public enum CategoryBrowseResponseGroup {
	
	/**
	 * All sub-categories within the specified category path
	 */
	
	Categories,
	
    /**
     * Categories that are related to the specified category path
     */
    
    RelatedCategories,
    
    /**
     * Language categories in which the specified category path is available
     */
    LanguageCategories,
    
    /**
     * "Letter Bars" (A, B, C, etc.) for categories that contain them
     */
    LetterBars,
}

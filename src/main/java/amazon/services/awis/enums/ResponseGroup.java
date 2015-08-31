package amazon.services.awis.enums;

public enum ResponseGroup {
    /**
     *  Up to 11 related links
     */
    RelatedLinks,
    
    /**
     * Up to 3 DMOZ (Open Directory) categories the site belongs to
     */
    Categories,
    
    /**
     * The Alexa three month average traffic rank
     */
    Rank, 
    
    /**
     * Percentage of viewers, page views, and traffic rank broken out by country
     */
    RankByCountry, 
    
    /**
     * Usage statistics such as reach and page views
     */
    UsageStats, 
    
    /**
     * Contact information for the site owner or registrar
     */
    ContactInfo,
    
    /**
     * Whether the site is likely to contain adult content ('yes' or 'no')
     */
    AdultContent, 
    
    /**
     * Median load time and percent of known sites that are slower
     */
    Speed,
    
    /**
     * Content language code and character-encoding (note that this may not match the language or character encoding of any given page on the website because the languange and character set returned are those of the majority of pages)
     */
    Language,
    
    /**
     * Other domains owned by the same owner as this site
     */
    OwnedDomains,
    
    /**
     * A count of links pointing in to this site
     */
    LinksInCount,
    
    /**
     * Title, description, and date the site was created
     */
    SiteData,
    
    /**
     * Meta-Response Group
     * 
     * Up to 11 related links and up to 3 DMOZ categories (equivalent to ResponseGroup=RelatedLinks,Categories)
     */
    Related, 
    
    /**
     * Meta-Response Group
     *  
     * Traffic rank and usage statistics (equivalent to ResponseGroup=Rank,UsageStats)
     */
    TrafficData, 
    
    /**
     * Meta-Response Group
     * 
     * Information about the site's content (equivalent to ResponseGroup=SiteData,AdultContent,Popups,Speed,Language)
     */
    ContentData,
    
    /**
     * Meta-Response Group
     * 
     * History is the only available response group for action TrafficHistory
     */
    History
}

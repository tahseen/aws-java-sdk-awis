//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.10 at 03:27:02 PM EET 
//


package net.distributary.tahseen.awis.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategoryBrowseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CategoryBrowseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Categories" type="{http://awis.amazonaws.com/doc/2005-10-05}CategoriesType" minOccurs="0"/&gt;
 *         &lt;element name="LanguageCategories" type="{http://awis.amazonaws.com/doc/2005-10-05}CategoriesType" minOccurs="0"/&gt;
 *         &lt;element name="RelatedCategories" type="{http://awis.amazonaws.com/doc/2005-10-05}CategoriesType" minOccurs="0"/&gt;
 *         &lt;element name="LetterBars" type="{http://awis.amazonaws.com/doc/2005-10-05}LetterBarsType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryBrowseType", propOrder = {
    "categories",
    "languageCategories",
    "relatedCategories",
    "letterBars"
})
public class CategoryBrowseType {

    @XmlElement(name = "Categories")
    protected CategoriesType categories;
    @XmlElement(name = "LanguageCategories")
    protected CategoriesType languageCategories;
    @XmlElement(name = "RelatedCategories")
    protected CategoriesType relatedCategories;
    @XmlElement(name = "LetterBars")
    protected LetterBarsType letterBars;

    /**
     * Gets the value of the categories property.
     * 
     * @return
     *     possible object is
     *     {@link CategoriesType }
     *     
     */
    public CategoriesType getCategories() {
        return categories;
    }

    /**
     * Sets the value of the categories property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoriesType }
     *     
     */
    public void setCategories(CategoriesType value) {
        this.categories = value;
    }

    /**
     * Gets the value of the languageCategories property.
     * 
     * @return
     *     possible object is
     *     {@link CategoriesType }
     *     
     */
    public CategoriesType getLanguageCategories() {
        return languageCategories;
    }

    /**
     * Sets the value of the languageCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoriesType }
     *     
     */
    public void setLanguageCategories(CategoriesType value) {
        this.languageCategories = value;
    }

    /**
     * Gets the value of the relatedCategories property.
     * 
     * @return
     *     possible object is
     *     {@link CategoriesType }
     *     
     */
    public CategoriesType getRelatedCategories() {
        return relatedCategories;
    }

    /**
     * Sets the value of the relatedCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoriesType }
     *     
     */
    public void setRelatedCategories(CategoriesType value) {
        this.relatedCategories = value;
    }

    /**
     * Gets the value of the letterBars property.
     * 
     * @return
     *     possible object is
     *     {@link LetterBarsType }
     *     
     */
    public LetterBarsType getLetterBars() {
        return letterBars;
    }

    /**
     * Sets the value of the letterBars property.
     * 
     * @param value
     *     allowed object is
     *     {@link LetterBarsType }
     *     
     */
    public void setLetterBars(LetterBarsType value) {
        this.letterBars = value;
    }

}

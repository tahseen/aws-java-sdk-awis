//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.10 at 11:19:43 PM PDT 
//


package net.distributary.tahseen.awis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Structure containing information related to the owner or contact for the site
 * 
 * <p>Java class for ContactInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://alexa.amazonaws.com/doc/2005-10-05/}UrlServiceType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PhoneNumbers" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="PhoneNumber" type="{http://alexa.amazonaws.com/doc/2005-10-05/}PhoneNumberType" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="OwnerName" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
 *         &lt;element name="Email" type="{http://alexa.amazonaws.com/doc/2005-10-05/}EmailType" minOccurs="0"/&gt;
 *         &lt;element name="PhysicalAddress" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://alexa.amazonaws.com/doc/2005-10-05/}PhysicalAddressType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="UnformattedAddress" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="CompanyStockTicker" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Symbol" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
 *                   &lt;element name="Exchange" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoType", propOrder = {
    "phoneNumbers",
    "ownerName",
    "email",
    "physicalAddress",
    "companyStockTicker"
})
public class ContactInfoType
    extends UrlServiceType
{

    @XmlElement(name = "PhoneNumbers")
    protected ContactInfoType.PhoneNumbers phoneNumbers;
    @XmlElement(name = "OwnerName")
    protected GenericDataType ownerName;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "PhysicalAddress")
    protected ContactInfoType.PhysicalAddress physicalAddress;
    @XmlElement(name = "CompanyStockTicker")
    protected ContactInfoType.CompanyStockTicker companyStockTicker;

    /**
     * Gets the value of the phoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoType.PhoneNumbers }
     *     
     */
    public ContactInfoType.PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the value of the phoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoType.PhoneNumbers }
     *     
     */
    public void setPhoneNumbers(ContactInfoType.PhoneNumbers value) {
        this.phoneNumbers = value;
    }

    /**
     * Gets the value of the ownerName property.
     * 
     * @return
     *     possible object is
     *     {@link GenericDataType }
     *     
     */
    public GenericDataType getOwnerName() {
        return ownerName;
    }

    /**
     * Sets the value of the ownerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericDataType }
     *     
     */
    public void setOwnerName(GenericDataType value) {
        this.ownerName = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the physicalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoType.PhysicalAddress }
     *     
     */
    public ContactInfoType.PhysicalAddress getPhysicalAddress() {
        return physicalAddress;
    }

    /**
     * Sets the value of the physicalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoType.PhysicalAddress }
     *     
     */
    public void setPhysicalAddress(ContactInfoType.PhysicalAddress value) {
        this.physicalAddress = value;
    }

    /**
     * Gets the value of the companyStockTicker property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoType.CompanyStockTicker }
     *     
     */
    public ContactInfoType.CompanyStockTicker getCompanyStockTicker() {
        return companyStockTicker;
    }

    /**
     * Sets the value of the companyStockTicker property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoType.CompanyStockTicker }
     *     
     */
    public void setCompanyStockTicker(ContactInfoType.CompanyStockTicker value) {
        this.companyStockTicker = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Symbol" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
     *         &lt;element name="Exchange" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "symbol",
        "exchange"
    })
    public static class CompanyStockTicker {

        @XmlElement(name = "Symbol")
        protected GenericDataType symbol;
        @XmlElement(name = "Exchange")
        protected GenericDataType exchange;

        /**
         * Gets the value of the symbol property.
         * 
         * @return
         *     possible object is
         *     {@link GenericDataType }
         *     
         */
        public GenericDataType getSymbol() {
            return symbol;
        }

        /**
         * Sets the value of the symbol property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenericDataType }
         *     
         */
        public void setSymbol(GenericDataType value) {
            this.symbol = value;
        }

        /**
         * Gets the value of the exchange property.
         * 
         * @return
         *     possible object is
         *     {@link GenericDataType }
         *     
         */
        public GenericDataType getExchange() {
            return exchange;
        }

        /**
         * Sets the value of the exchange property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenericDataType }
         *     
         */
        public void setExchange(GenericDataType value) {
            this.exchange = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="PhoneNumber" type="{http://alexa.amazonaws.com/doc/2005-10-05/}PhoneNumberType" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "phoneNumber"
    })
    public static class PhoneNumbers {

        @XmlElement(name = "PhoneNumber", required = true)
        protected List<PhoneNumberType> phoneNumber;

        /**
         * Gets the value of the phoneNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PhoneNumberType }
         * 
         * 
         */
        public List<PhoneNumberType> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<PhoneNumberType>();
            }
            return this.phoneNumber;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;extension base="{http://alexa.amazonaws.com/doc/2005-10-05/}PhysicalAddressType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="UnformattedAddress" type="{http://alexa.amazonaws.com/doc/2005-10-05/}GenericDataType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/extension&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "unformattedAddress"
    })
    public static class PhysicalAddress
        extends PhysicalAddressType
    {

        @XmlElement(name = "UnformattedAddress")
        protected GenericDataType unformattedAddress;

        /**
         * Gets the value of the unformattedAddress property.
         * 
         * @return
         *     possible object is
         *     {@link GenericDataType }
         *     
         */
        public GenericDataType getUnformattedAddress() {
            return unformattedAddress;
        }

        /**
         * Sets the value of the unformattedAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link GenericDataType }
         *     
         */
        public void setUnformattedAddress(GenericDataType value) {
            this.unformattedAddress = value;
        }

    }

}

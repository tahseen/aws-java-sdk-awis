//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.26 at 01:55:20 PM PDT 
//


package net.distributary.tahseen.awis.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://alexa.amazonaws.com/doc/2005-10-05/}Request" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://alexa.amazonaws.com/doc/2005-10-05/}OperationInformation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://alexa.amazonaws.com/doc/2005-10-05/}ResponseGroupInformation" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "request",
    "operationInformation",
    "responseGroupInformation"
})
@XmlRootElement(name = "Information")
public class Information {

    @XmlElement(name = "Request")
    protected List<Request> request;
    @XmlElement(name = "OperationInformation")
    protected List<OperationInformation> operationInformation;
    @XmlElement(name = "ResponseGroupInformation")
    protected List<ResponseGroupInformation> responseGroupInformation;

    /**
     * Gets the value of the request property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the request property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Request }
     * 
     * 
     */
    public List<Request> getRequest() {
        if (request == null) {
            request = new ArrayList<Request>();
        }
        return this.request;
    }

    /**
     * Gets the value of the operationInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperationInformation }
     * 
     * 
     */
    public List<OperationInformation> getOperationInformation() {
        if (operationInformation == null) {
            operationInformation = new ArrayList<OperationInformation>();
        }
        return this.operationInformation;
    }

    /**
     * Gets the value of the responseGroupInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responseGroupInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponseGroupInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponseGroupInformation }
     * 
     * 
     */
    public List<ResponseGroupInformation> getResponseGroupInformation() {
        if (responseGroupInformation == null) {
            responseGroupInformation = new ArrayList<ResponseGroupInformation>();
        }
        return this.responseGroupInformation;
    }

}
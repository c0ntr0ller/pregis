
package ru.gosuslugi.dom.schema.integration.base;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}HeaderType">
 *       &lt;sequence>
 *         &lt;element name="SenderID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *         &lt;element name="IsOperatorSighnature" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "senderID",
    "isOperatorSighnature"
})
@XmlRootElement(name = "RequestHeader")
public class RequestHeader
    extends HeaderType
{

    @XmlElement(name = "SenderID", required = true)
    protected String senderID;
    @XmlElement(name = "IsOperatorSighnature")
    protected Boolean isOperatorSighnature;

    /**
     * Gets the value of the senderID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * Sets the value of the senderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderID(String value) {
        this.senderID = value;
    }

    /**
     * Gets the value of the isOperatorSighnature property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOperatorSighnature() {
        return isOperatorSighnature;
    }

    /**
     * Sets the value of the isOperatorSighnature property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOperatorSighnature(Boolean value) {
        this.isOperatorSighnature = value;
    }

}

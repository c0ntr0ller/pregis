
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Физлицо (собственник, не собственник)
 * 
 * <p>Java class for HIndType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HIndType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IndGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *         &lt;element name="IsRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsResides" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HIndType", propOrder = {
    "indGUID",
    "isRegistered",
    "isResides"
})
public class HIndType {

    @XmlElement(name = "IndGUID", required = true)
    protected String indGUID;
    @XmlElement(name = "IsRegistered")
    protected boolean isRegistered;
    @XmlElement(name = "IsResides")
    protected boolean isResides;

    /**
     * Gets the value of the indGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndGUID() {
        return indGUID;
    }

    /**
     * Sets the value of the indGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndGUID(String value) {
        this.indGUID = value;
    }

    /**
     * Gets the value of the isRegistered property.
     * 
     */
    public boolean isIsRegistered() {
        return isRegistered;
    }

    /**
     * Sets the value of the isRegistered property.
     * 
     */
    public void setIsRegistered(boolean value) {
        this.isRegistered = value;
    }

    /**
     * Gets the value of the isResides property.
     * 
     */
    public boolean isIsResides() {
        return isResides;
    }

    /**
     * Sets the value of the isResides property.
     * 
     */
    public void setIsResides(boolean value) {
        this.isResides = value;
    }

}

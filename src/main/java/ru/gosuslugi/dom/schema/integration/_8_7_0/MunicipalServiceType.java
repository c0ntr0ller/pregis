
package ru.gosuslugi.dom.schema.integration._8_7_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Коммунальная услуга
 * 
 * <p>Java class for MunicipalServiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MunicipalServiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MSType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *         &lt;element name="StartDateFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="StartDateTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="AccountingType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MunicipalServiceType", propOrder = {
    "msType",
    "startDateFrom",
    "startDateTo",
    "accountingType"
})
public class MunicipalServiceType {

    @XmlElement(name = "MSType", required = true)
    protected NsiRef msType;
    @XmlElement(name = "StartDateFrom", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDateFrom;
    @XmlElement(name = "StartDateTo", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDateTo;
    @XmlElement(name = "AccountingType")
    protected NsiRef accountingType;

    /**
     * Gets the value of the msType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getMSType() {
        return msType;
    }

    /**
     * Sets the value of the msType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setMSType(NsiRef value) {
        this.msType = value;
    }

    /**
     * Gets the value of the startDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateFrom() {
        return startDateFrom;
    }

    /**
     * Sets the value of the startDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateFrom(XMLGregorianCalendar value) {
        this.startDateFrom = value;
    }

    /**
     * Gets the value of the startDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateTo() {
        return startDateTo;
    }

    /**
     * Sets the value of the startDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateTo(XMLGregorianCalendar value) {
        this.startDateTo = value;
    }

    /**
     * Gets the value of the accountingType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getAccountingType() {
        return accountingType;
    }

    /**
     * Sets the value of the accountingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setAccountingType(NsiRef value) {
        this.accountingType = value;
    }

}

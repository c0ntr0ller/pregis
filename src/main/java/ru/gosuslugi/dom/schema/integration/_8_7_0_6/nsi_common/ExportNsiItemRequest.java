
package ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi_common;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="RegistryNumber" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}NsiItemRegistryNumberType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}ListGroup"/>
 *         &lt;element name="ModifiedAfter" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
    "registryNumber",
    "listGroup",
    "modifiedAfter"
})
@XmlRootElement(name = "exportNsiItemRequest")
public class ExportNsiItemRequest
    extends BaseType
{

    @XmlElement(name = "RegistryNumber", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger registryNumber;
    @XmlElement(name = "ListGroup", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
    protected String listGroup;
    @XmlElement(name = "ModifiedAfter")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAfter;

    /**
     * Gets the value of the registryNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRegistryNumber() {
        return registryNumber;
    }

    /**
     * Sets the value of the registryNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRegistryNumber(BigInteger value) {
        this.registryNumber = value;
    }

    /**
     * Gets the value of the listGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListGroup() {
        return listGroup;
    }

    /**
     * Sets the value of the listGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListGroup(String value) {
        this.listGroup = value;
    }

    /**
     * Gets the value of the modifiedAfter property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedAfter() {
        return modifiedAfter;
    }

    /**
     * Sets the value of the modifiedAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedAfter(XMLGregorianCalendar value) {
        this.modifiedAfter = value;
    }

}

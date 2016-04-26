
package ru.gosuslugi.dom.schema.integration._8_7_0_4.nsi;

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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="RegistryNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}NsiItemRegistryNumberType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="51"/>
 *               &lt;enumeration value="59"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
    "modifiedAfter"
})
@XmlRootElement(name = "exportDataProviderNsiItemRequest")
public class ExportDataProviderNsiItemRequest
    extends BaseType
{

    @XmlElement(name = "RegistryNumber", required = true)
    protected BigInteger registryNumber;
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

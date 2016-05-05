
package ru.gosuslugi.dom.schema.integration.services.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Базовый тип помещения (обновление данных для ОМС)
 * 
 * <p>Java class for PremisesBasicUpdateOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PremisesBasicUpdateOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}GKN_EGRP_KeyType">
 *       &lt;sequence>
 *         &lt;element name="PremisesNum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}PremisesNumType" minOccurs="0"/>
 *         &lt;element name="TerminationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PremisesBasicUpdateOMSType", propOrder = {
    "premisesNum",
    "terminationDate"
})
@XmlSeeAlso({
    NonResidentialPremisesUpdateOMSType.class,
    ResidentialPremisesUpdateOMSType.class
})
public class PremisesBasicUpdateOMSType
    extends GKNEGRPKeyType
{

    @XmlElement(name = "PremisesNum")
    protected String premisesNum;
    @XmlElement(name = "TerminationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar terminationDate;

    /**
     * Gets the value of the premisesNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPremisesNum() {
        return premisesNum;
    }

    /**
     * Sets the value of the premisesNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPremisesNum(String value) {
        this.premisesNum = value;
    }

    /**
     * Gets the value of the terminationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the value of the terminationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTerminationDate(XMLGregorianCalendar value) {
        this.terminationDate = value;
    }

}

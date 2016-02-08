
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Базовый тип помещения (для импорта от ОМС)
 * 
 * <p>Java class for PremisesBasicOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PremisesBasicOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}GKNType">
 *       &lt;sequence>
 *         &lt;element name="PremisesNum" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}PremisesNumType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PremisesBasicOMSType", propOrder = {
    "premisesNum"
})
@XmlSeeAlso({
    ResidentialPremisesOMSType.class,
    NonResidentialPremisesOMSType.class
})
public class PremisesBasicOMSType
    extends GKNType
{

    @XmlElement(name = "PremisesNum", required = true)
    protected String premisesNum;

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

}

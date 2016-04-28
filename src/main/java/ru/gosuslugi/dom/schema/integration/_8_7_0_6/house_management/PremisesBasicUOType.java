
package ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Базовый тип помещения (для импорта от УО)
 * 
 * <p>Java class for PremisesBasicUOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PremisesBasicUOType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}GKN_EGRP_KeyType">
 *       &lt;sequence>
 *         &lt;element name="PremisesNum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}PremisesNumType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PremisesBasicUOType", propOrder = {
    "premisesNum"
})
@XmlSeeAlso({
    NonResidentialPremisesUOType.class,
    ResidentialPremisesUOType.class
})
public class PremisesBasicUOType
    extends GKNEGRPKeyType
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


package ru.gosuslugi.dom.schema.integration._8_6_0_6.bills;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseType">
 *       &lt;choice>
 *         &lt;element name="isUO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isRSO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isUO",
    "isRSO"
})
@XmlRootElement(name = "exportOrgPeriodRequest")
public class ExportOrgPeriodRequest
    extends BaseType
{

    protected Boolean isUO;
    protected Boolean isRSO;

    /**
     * Gets the value of the isUO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUO() {
        return isUO;
    }

    /**
     * Sets the value of the isUO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUO(Boolean value) {
        this.isUO = value;
    }

    /**
     * Gets the value of the isRSO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRSO() {
        return isRSO;
    }

    /**
     * Sets the value of the isRSO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRSO(Boolean value) {
        this.isRSO = value;
    }

}

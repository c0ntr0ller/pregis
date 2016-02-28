
package ru.gosuslugi.dom.schema.integration._8_6_0_4.inspection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="YearFrom" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}YearType" minOccurs="0"/>
 *         &lt;element name="YearTo" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}YearType" minOccurs="0"/>
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
    "yearFrom",
    "yearTo"
})
@XmlRootElement(name = "exportInspectionPlansRequest")
public class ExportInspectionPlansRequest
    extends BaseType
{

    @XmlElement(name = "YearFrom")
    protected Short yearFrom;
    @XmlElement(name = "YearTo")
    protected Short yearTo;

    /**
     * Gets the value of the yearFrom property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getYearFrom() {
        return yearFrom;
    }

    /**
     * Sets the value of the yearFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setYearFrom(Short value) {
        this.yearFrom = value;
    }

    /**
     * Gets the value of the yearTo property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getYearTo() {
        return yearTo;
    }

    /**
     * Sets the value of the yearTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setYearTo(Short value) {
        this.yearTo = value;
    }

}

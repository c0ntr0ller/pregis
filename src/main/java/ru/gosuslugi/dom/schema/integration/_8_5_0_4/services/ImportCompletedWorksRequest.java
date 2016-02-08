
package ru.gosuslugi.dom.schema.integration._8_5_0_4.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="CompletedWorksByPeriod" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/services/}CompletedWorksByPeriodType"/>
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
    "completedWorksByPeriod"
})
@XmlRootElement(name = "importCompletedWorksRequest")
public class ImportCompletedWorksRequest
    extends BaseType
{

    @XmlElement(name = "CompletedWorksByPeriod", required = true)
    protected CompletedWorksByPeriodType completedWorksByPeriod;

    /**
     * Gets the value of the completedWorksByPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link CompletedWorksByPeriodType }
     *     
     */
    public CompletedWorksByPeriodType getCompletedWorksByPeriod() {
        return completedWorksByPeriod;
    }

    /**
     * Sets the value of the completedWorksByPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompletedWorksByPeriodType }
     *     
     */
    public void setCompletedWorksByPeriod(CompletedWorksByPeriodType value) {
        this.completedWorksByPeriod = value;
    }

}

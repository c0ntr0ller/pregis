
package ru.gosuslugi.dom.schema.integration._8_5_0_4.services;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="WorkingPlan" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/services/}WorkingPlanType" maxOccurs="100"/>
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
    "workingPlan"
})
@XmlRootElement(name = "importWorkingPlanRequest")
public class ImportWorkingPlanRequest
    extends BaseType
{

    @XmlElement(name = "WorkingPlan", required = true)
    protected List<WorkingPlanType> workingPlan;

    /**
     * Gets the value of the workingPlan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workingPlan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkingPlan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkingPlanType }
     * 
     * 
     */
    public List<WorkingPlanType> getWorkingPlan() {
        if (workingPlan == null) {
            workingPlan = new ArrayList<WorkingPlanType>();
        }
        return this.workingPlan;
    }

}

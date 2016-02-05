
package ru.gosuslugi.dom.schema.integration._8_5_0_2.services;

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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="reportingPeriodGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType" maxOccurs="100"/>
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
    "reportingPeriodGuid"
})
@XmlRootElement(name = "exportCompletedWorksRequest")
public class ExportCompletedWorksRequest
    extends BaseType
{

    @XmlElement(required = true)
    protected List<String> reportingPeriodGuid;

    /**
     * Gets the value of the reportingPeriodGuid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportingPeriodGuid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportingPeriodGuid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReportingPeriodGuid() {
        if (reportingPeriodGuid == null) {
            reportingPeriodGuid = new ArrayList<String>();
        }
        return this.reportingPeriodGuid;
    }

}

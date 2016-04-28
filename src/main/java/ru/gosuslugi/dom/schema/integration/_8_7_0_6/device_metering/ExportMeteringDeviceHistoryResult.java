
package ru.gosuslugi.dom.schema.integration._8_7_0_6.device_metering;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}BaseType">
 *       &lt;choice>
 *         &lt;element name="exportMeteringDeviceHistoryResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/device-metering/}exportMeteringDeviceHistoryResultType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}ErrorMessage"/>
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
    "exportMeteringDeviceHistoryResult",
    "errorMessage"
})
@XmlRootElement(name = "exportMeteringDeviceHistoryResult")
public class ExportMeteringDeviceHistoryResult
    extends BaseType
{

    protected List<ExportMeteringDeviceHistoryResultType> exportMeteringDeviceHistoryResult;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the exportMeteringDeviceHistoryResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportMeteringDeviceHistoryResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportMeteringDeviceHistoryResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportMeteringDeviceHistoryResultType }
     * 
     * 
     */
    public List<ExportMeteringDeviceHistoryResultType> getExportMeteringDeviceHistoryResult() {
        if (exportMeteringDeviceHistoryResult == null) {
            exportMeteringDeviceHistoryResult = new ArrayList<ExportMeteringDeviceHistoryResultType>();
        }
        return this.exportMeteringDeviceHistoryResult;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageType }
     *     
     */
    public ErrorMessageType getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageType }
     *     
     */
    public void setErrorMessage(ErrorMessageType value) {
        this.errorMessage = value;
    }

}

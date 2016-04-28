
package ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management;

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
 *         &lt;element name="exportHouseResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}exportHouseResultType"/>
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
    "exportHouseResult",
    "errorMessage"
})
@XmlRootElement(name = "exportHouseResult")
public class ExportHouseResult
    extends BaseType
{

    protected ExportHouseResultType exportHouseResult;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the exportHouseResult property.
     * 
     * @return
     *     possible object is
     *     {@link ExportHouseResultType }
     *     
     */
    public ExportHouseResultType getExportHouseResult() {
        return exportHouseResult;
    }

    /**
     * Sets the value of the exportHouseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportHouseResultType }
     *     
     */
    public void setExportHouseResult(ExportHouseResultType value) {
        this.exportHouseResult = value;
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

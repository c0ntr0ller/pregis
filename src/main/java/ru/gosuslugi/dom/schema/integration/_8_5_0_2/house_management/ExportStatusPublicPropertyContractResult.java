
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;choice>
 *         &lt;element name="exportStatusPublicPropertyContract" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}exportStatusPublicPropertyContractResultType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ErrorMessage"/>
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
    "exportStatusPublicPropertyContract",
    "errorMessage"
})
@XmlRootElement(name = "exportStatusPublicPropertyContractResult")
public class ExportStatusPublicPropertyContractResult
    extends BaseType
{

    protected ExportStatusPublicPropertyContractResultType exportStatusPublicPropertyContract;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the exportStatusPublicPropertyContract property.
     * 
     * @return
     *     possible object is
     *     {@link ExportStatusPublicPropertyContractResultType }
     *     
     */
    public ExportStatusPublicPropertyContractResultType getExportStatusPublicPropertyContract() {
        return exportStatusPublicPropertyContract;
    }

    /**
     * Sets the value of the exportStatusPublicPropertyContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportStatusPublicPropertyContractResultType }
     *     
     */
    public void setExportStatusPublicPropertyContract(ExportStatusPublicPropertyContractResultType value) {
        this.exportStatusPublicPropertyContract = value;
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

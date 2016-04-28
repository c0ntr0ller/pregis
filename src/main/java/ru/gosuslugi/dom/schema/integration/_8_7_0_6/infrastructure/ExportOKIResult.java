
package ru.gosuslugi.dom.schema.integration._8_7_0_6.infrastructure;

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
 *         &lt;element name="ExportOKIResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/infrastructure/}exportOKIResultType" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}ErrorMessage" maxOccurs="unbounded"/>
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
    "exportOKIResult",
    "errorMessage"
})
@XmlRootElement(name = "exportOKIResult")
public class ExportOKIResult
    extends BaseType
{

    @XmlElement(name = "ExportOKIResult")
    protected List<ExportOKIResultType> exportOKIResult;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/")
    protected List<ErrorMessageType> errorMessage;

    /**
     * Gets the value of the exportOKIResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportOKIResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportOKIResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportOKIResultType }
     * 
     * 
     */
    public List<ExportOKIResultType> getExportOKIResult() {
        if (exportOKIResult == null) {
            exportOKIResult = new ArrayList<ExportOKIResultType>();
        }
        return this.exportOKIResult;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorMessage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorMessageType }
     * 
     * 
     */
    public List<ErrorMessageType> getErrorMessage() {
        if (errorMessage == null) {
            errorMessage = new ArrayList<ErrorMessageType>();
        }
        return this.errorMessage;
    }

}

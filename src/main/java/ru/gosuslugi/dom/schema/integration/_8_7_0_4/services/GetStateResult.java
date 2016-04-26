
package ru.gosuslugi.dom.schema.integration._8_7_0_4.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseAsyncResponseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.CommonResultType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
 *         &lt;element name="ImportResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}CommonResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportHMServicesTarifsResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/services/}exportHMServicesTarifsResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportCompletedWorksResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/services/}exportCompletedWorksResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportWorkingListResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/services/}exportWorkingListResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportWorkingPlanResult" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/services/}exportWorkingPlanResultType" maxOccurs="unbounded"/>
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
    "errorMessage",
    "importResult",
    "exportHMServicesTarifsResult",
    "exportCompletedWorksResult",
    "exportWorkingListResult",
    "exportWorkingPlanResult"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "ImportResult")
    protected List<CommonResultType> importResult;
    protected List<ExportHMServicesTarifsResultType> exportHMServicesTarifsResult;
    protected List<ExportCompletedWorksResultType> exportCompletedWorksResult;
    protected List<ExportWorkingListResultType> exportWorkingListResult;
    protected List<ExportWorkingPlanResultType> exportWorkingPlanResult;

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

    /**
     * Gets the value of the importResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommonResultType }
     * 
     * 
     */
    public List<CommonResultType> getImportResult() {
        if (importResult == null) {
            importResult = new ArrayList<CommonResultType>();
        }
        return this.importResult;
    }

    /**
     * Gets the value of the exportHMServicesTarifsResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportHMServicesTarifsResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportHMServicesTarifsResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportHMServicesTarifsResultType }
     * 
     * 
     */
    public List<ExportHMServicesTarifsResultType> getExportHMServicesTarifsResult() {
        if (exportHMServicesTarifsResult == null) {
            exportHMServicesTarifsResult = new ArrayList<ExportHMServicesTarifsResultType>();
        }
        return this.exportHMServicesTarifsResult;
    }

    /**
     * Gets the value of the exportCompletedWorksResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportCompletedWorksResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportCompletedWorksResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportCompletedWorksResultType }
     * 
     * 
     */
    public List<ExportCompletedWorksResultType> getExportCompletedWorksResult() {
        if (exportCompletedWorksResult == null) {
            exportCompletedWorksResult = new ArrayList<ExportCompletedWorksResultType>();
        }
        return this.exportCompletedWorksResult;
    }

    /**
     * Gets the value of the exportWorkingListResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportWorkingListResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportWorkingListResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportWorkingListResultType }
     * 
     * 
     */
    public List<ExportWorkingListResultType> getExportWorkingListResult() {
        if (exportWorkingListResult == null) {
            exportWorkingListResult = new ArrayList<ExportWorkingListResultType>();
        }
        return this.exportWorkingListResult;
    }

    /**
     * Gets the value of the exportWorkingPlanResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportWorkingPlanResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportWorkingPlanResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportWorkingPlanResultType }
     * 
     * 
     */
    public List<ExportWorkingPlanResultType> getExportWorkingPlanResult() {
        if (exportWorkingPlanResult == null) {
            exportWorkingPlanResult = new ArrayList<ExportWorkingPlanResultType>();
        }
        return this.exportWorkingPlanResult;
    }

}

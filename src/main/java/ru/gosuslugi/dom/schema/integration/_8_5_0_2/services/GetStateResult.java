
package ru.gosuslugi.dom.schema.integration._8_5_0_2.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseAsyncResponseType;
import ru.gosuslugi.dom.schema.integration._8_5_0.CommonResultType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ErrorMessage"/>
 *         &lt;element name="ImportResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}CommonResultType" maxOccurs="unbounded"/>
 *         &lt;element name="ExportHMServicesTarifsResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/services/}exportHMServicesTarifsResultType" maxOccurs="unbounded"/>
 *         &lt;element name="ExportMSRSOResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/services/}exportMSRSOResultType" maxOccurs="unbounded"/>
 *         &lt;element name="ExportCompletedWorksResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/services/}exportCompletedWorksResultType" maxOccurs="unbounded"/>
 *         &lt;element name="ExportWorkingListResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/services/}exportWorkingListResultType" maxOccurs="unbounded"/>
 *         &lt;element name="ExportWorkingPlanResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/services/}exportWorkingPlanResultType" maxOccurs="unbounded"/>
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
    "exportMSRSOResult",
    "exportCompletedWorksResult",
    "exportWorkingListResult",
    "exportWorkingPlanResult"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "ImportResult")
    protected List<CommonResultType> importResult;
    @XmlElement(name = "ExportHMServicesTarifsResult")
    protected List<ExportHMServicesTarifsResultType> exportHMServicesTarifsResult;
    @XmlElement(name = "ExportMSRSOResult")
    protected List<ExportMSRSOResultType> exportMSRSOResult;
    @XmlElement(name = "ExportCompletedWorksResult")
    protected List<ExportCompletedWorksResultType> exportCompletedWorksResult;
    @XmlElement(name = "ExportWorkingListResult")
    protected List<ExportWorkingListResultType> exportWorkingListResult;
    @XmlElement(name = "ExportWorkingPlanResult")
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
     * Gets the value of the exportMSRSOResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportMSRSOResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportMSRSOResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportMSRSOResultType }
     * 
     * 
     */
    public List<ExportMSRSOResultType> getExportMSRSOResult() {
        if (exportMSRSOResult == null) {
            exportMSRSOResult = new ArrayList<ExportMSRSOResultType>();
        }
        return this.exportMSRSOResult;
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

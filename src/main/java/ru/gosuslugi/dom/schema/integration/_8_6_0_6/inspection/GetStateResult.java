
package ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseAsyncResponseType;
import ru.gosuslugi.dom.schema.integration._8_6_0.CommonResultType;
import ru.gosuslugi.dom.schema.integration._8_6_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}ErrorMessage"/>
 *         &lt;element name="CommonResult" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}CommonResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportInspectionPlanResult" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}exportInspectionPlanResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportExaminationResult" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}exportExaminationResultType" maxOccurs="unbounded"/>
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
    "commonResult",
    "exportInspectionPlanResult",
    "exportExaminationResult"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "CommonResult")
    protected List<CommonResultType> commonResult;
    protected List<ExportInspectionPlanResultType> exportInspectionPlanResult;
    protected List<ExportExaminationResultType> exportExaminationResult;

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
     * Gets the value of the commonResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commonResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommonResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CommonResultType }
     * 
     * 
     */
    public List<CommonResultType> getCommonResult() {
        if (commonResult == null) {
            commonResult = new ArrayList<CommonResultType>();
        }
        return this.commonResult;
    }

    /**
     * Gets the value of the exportInspectionPlanResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportInspectionPlanResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportInspectionPlanResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportInspectionPlanResultType }
     * 
     * 
     */
    public List<ExportInspectionPlanResultType> getExportInspectionPlanResult() {
        if (exportInspectionPlanResult == null) {
            exportInspectionPlanResult = new ArrayList<ExportInspectionPlanResultType>();
        }
        return this.exportInspectionPlanResult;
    }

    /**
     * Gets the value of the exportExaminationResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportExaminationResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportExaminationResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportExaminationResultType }
     * 
     * 
     */
    public List<ExportExaminationResultType> getExportExaminationResult() {
        if (exportExaminationResult == null) {
            exportExaminationResult = new ArrayList<ExportExaminationResultType>();
        }
        return this.exportExaminationResult;
    }

}
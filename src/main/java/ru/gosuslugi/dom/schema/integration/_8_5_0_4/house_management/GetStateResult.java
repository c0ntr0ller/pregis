
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseAsyncResponseType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}ErrorMessage"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}ImportResult" maxOccurs="unbounded"/>
 *         &lt;element name="exportHouseResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportHouseResultType"/>
 *         &lt;element name="exportMeteringDeviceDataResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportMeteringDeviceDataResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportShareEncbrDataResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportShareEncbrDataResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportStatusCAChResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportStatusCAChResultType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exportCAChResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportCAChResultType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="exportStatusPublicPropertyContractResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportStatusPublicPropertyContractResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportAccountResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportAccountResultType" maxOccurs="unbounded"/>
 *         &lt;element name="exportVotingProtocolResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}exportVotingProtocolResultType" maxOccurs="unbounded"/>
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
    "exportHouseResult",
    "exportMeteringDeviceDataResult",
    "exportShareEncbrDataResult",
    "exportStatusCAChResult",
    "exportCAChResult",
    "exportStatusPublicPropertyContractResult",
    "exportAccountResult",
    "exportVotingProtocolResult"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "ImportResult")
    protected List<ImportResult> importResult;
    protected ExportHouseResultType exportHouseResult;
    protected List<ExportMeteringDeviceDataResultType> exportMeteringDeviceDataResult;
    protected List<ExportShareEncbrDataResultType> exportShareEncbrDataResult;
    protected List<ExportStatusCAChResultType> exportStatusCAChResult;
    protected List<ExportCAChResultType> exportCAChResult;
    protected List<ExportStatusPublicPropertyContractResultType> exportStatusPublicPropertyContractResult;
    protected List<ExportAccountResultType> exportAccountResult;
    protected List<ExportVotingProtocolResultType> exportVotingProtocolResult;

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
     * {@link ImportResult }
     * 
     * 
     */
    public List<ImportResult> getImportResult() {
        if (importResult == null) {
            importResult = new ArrayList<ImportResult>();
        }
        return this.importResult;
    }

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
     * Gets the value of the exportMeteringDeviceDataResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportMeteringDeviceDataResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportMeteringDeviceDataResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportMeteringDeviceDataResultType }
     * 
     * 
     */
    public List<ExportMeteringDeviceDataResultType> getExportMeteringDeviceDataResult() {
        if (exportMeteringDeviceDataResult == null) {
            exportMeteringDeviceDataResult = new ArrayList<ExportMeteringDeviceDataResultType>();
        }
        return this.exportMeteringDeviceDataResult;
    }

    /**
     * Gets the value of the exportShareEncbrDataResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportShareEncbrDataResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportShareEncbrDataResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportShareEncbrDataResultType }
     * 
     * 
     */
    public List<ExportShareEncbrDataResultType> getExportShareEncbrDataResult() {
        if (exportShareEncbrDataResult == null) {
            exportShareEncbrDataResult = new ArrayList<ExportShareEncbrDataResultType>();
        }
        return this.exportShareEncbrDataResult;
    }

    /**
     * Gets the value of the exportStatusCAChResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportStatusCAChResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportStatusCAChResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportStatusCAChResultType }
     * 
     * 
     */
    public List<ExportStatusCAChResultType> getExportStatusCAChResult() {
        if (exportStatusCAChResult == null) {
            exportStatusCAChResult = new ArrayList<ExportStatusCAChResultType>();
        }
        return this.exportStatusCAChResult;
    }

    /**
     * Gets the value of the exportCAChResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportCAChResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportCAChResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportCAChResultType }
     * 
     * 
     */
    public List<ExportCAChResultType> getExportCAChResult() {
        if (exportCAChResult == null) {
            exportCAChResult = new ArrayList<ExportCAChResultType>();
        }
        return this.exportCAChResult;
    }

    /**
     * Gets the value of the exportStatusPublicPropertyContractResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportStatusPublicPropertyContractResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportStatusPublicPropertyContractResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportStatusPublicPropertyContractResultType }
     * 
     * 
     */
    public List<ExportStatusPublicPropertyContractResultType> getExportStatusPublicPropertyContractResult() {
        if (exportStatusPublicPropertyContractResult == null) {
            exportStatusPublicPropertyContractResult = new ArrayList<ExportStatusPublicPropertyContractResultType>();
        }
        return this.exportStatusPublicPropertyContractResult;
    }

    /**
     * Gets the value of the exportAccountResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportAccountResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportAccountResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportAccountResultType }
     * 
     * 
     */
    public List<ExportAccountResultType> getExportAccountResult() {
        if (exportAccountResult == null) {
            exportAccountResult = new ArrayList<ExportAccountResultType>();
        }
        return this.exportAccountResult;
    }

    /**
     * Gets the value of the exportVotingProtocolResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exportVotingProtocolResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExportVotingProtocolResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportVotingProtocolResultType }
     * 
     * 
     */
    public List<ExportVotingProtocolResultType> getExportVotingProtocolResult() {
        if (exportVotingProtocolResult == null) {
            exportVotingProtocolResult = new ArrayList<ExportVotingProtocolResultType>();
        }
        return this.exportVotingProtocolResult;
    }

}
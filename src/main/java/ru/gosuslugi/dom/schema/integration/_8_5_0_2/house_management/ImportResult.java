
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;
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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ErrorMessage"/>
 *         &lt;element name="CommonResult" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}CommonResultType">
 *                 &lt;choice minOccurs="0">
 *                   &lt;element name="ImportHouseUO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
 *                   &lt;element name="ImportHouseRSO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
 *                   &lt;element name="ImportHouseOMS" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
 *                   &lt;element name="importContract" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}exportStatusCAChResultType"/>
 *                   &lt;element name="importCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}exportStatusCAChResultType"/>
 *                 &lt;/choice>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "commonResult"
})
@XmlRootElement(name = "ImportResult")
public class ImportResult
    extends BaseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "CommonResult")
    protected List<ImportResult.CommonResult> commonResult;

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
     * {@link ImportResult.CommonResult }
     * 
     * 
     */
    public List<ImportResult.CommonResult> getCommonResult() {
        if (commonResult == null) {
            commonResult = new ArrayList<ImportResult.CommonResult>();
        }
        return this.commonResult;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}CommonResultType">
     *       &lt;choice minOccurs="0">
     *         &lt;element name="ImportHouseUO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
     *         &lt;element name="ImportHouseRSO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
     *         &lt;element name="ImportHouseOMS" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}OGFImportStatusType"/>
     *         &lt;element name="importContract" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}exportStatusCAChResultType"/>
     *         &lt;element name="importCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}exportStatusCAChResultType"/>
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
        "importHouseUO",
        "importHouseRSO",
        "importHouseOMS",
        "importContract",
        "importCharter"
    })
    public static class CommonResult
        extends CommonResultType
    {

        @XmlElement(name = "ImportHouseUO")
        protected OGFImportStatusType importHouseUO;
        @XmlElement(name = "ImportHouseRSO")
        protected OGFImportStatusType importHouseRSO;
        @XmlElement(name = "ImportHouseOMS")
        protected OGFImportStatusType importHouseOMS;
        protected ExportStatusCAChResultType importContract;
        protected ExportStatusCAChResultType importCharter;

        /**
         * Gets the value of the importHouseUO property.
         * 
         * @return
         *     possible object is
         *     {@link OGFImportStatusType }
         *     
         */
        public OGFImportStatusType getImportHouseUO() {
            return importHouseUO;
        }

        /**
         * Sets the value of the importHouseUO property.
         * 
         * @param value
         *     allowed object is
         *     {@link OGFImportStatusType }
         *     
         */
        public void setImportHouseUO(OGFImportStatusType value) {
            this.importHouseUO = value;
        }

        /**
         * Gets the value of the importHouseRSO property.
         * 
         * @return
         *     possible object is
         *     {@link OGFImportStatusType }
         *     
         */
        public OGFImportStatusType getImportHouseRSO() {
            return importHouseRSO;
        }

        /**
         * Sets the value of the importHouseRSO property.
         * 
         * @param value
         *     allowed object is
         *     {@link OGFImportStatusType }
         *     
         */
        public void setImportHouseRSO(OGFImportStatusType value) {
            this.importHouseRSO = value;
        }

        /**
         * Gets the value of the importHouseOMS property.
         * 
         * @return
         *     possible object is
         *     {@link OGFImportStatusType }
         *     
         */
        public OGFImportStatusType getImportHouseOMS() {
            return importHouseOMS;
        }

        /**
         * Sets the value of the importHouseOMS property.
         * 
         * @param value
         *     allowed object is
         *     {@link OGFImportStatusType }
         *     
         */
        public void setImportHouseOMS(OGFImportStatusType value) {
            this.importHouseOMS = value;
        }

        /**
         * Gets the value of the importContract property.
         * 
         * @return
         *     possible object is
         *     {@link ExportStatusCAChResultType }
         *     
         */
        public ExportStatusCAChResultType getImportContract() {
            return importContract;
        }

        /**
         * Sets the value of the importContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportStatusCAChResultType }
         *     
         */
        public void setImportContract(ExportStatusCAChResultType value) {
            this.importContract = value;
        }

        /**
         * Gets the value of the importCharter property.
         * 
         * @return
         *     possible object is
         *     {@link ExportStatusCAChResultType }
         *     
         */
        public ExportStatusCAChResultType getImportCharter() {
            return importCharter;
        }

        /**
         * Sets the value of the importCharter property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportStatusCAChResultType }
         *     
         */
        public void setImportCharter(ExportStatusCAChResultType value) {
            this.importCharter = value;
        }

    }

}

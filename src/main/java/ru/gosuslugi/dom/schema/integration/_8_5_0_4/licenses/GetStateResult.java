
package ru.gosuslugi.dom.schema.integration._8_5_0_4.licenses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}ErrorMessage"/>
 *         &lt;element name="ImportResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}CommonResultType" maxOccurs="unbounded"/>
 *         &lt;element name="License" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/licenses/}LicenseType">
 *                 &lt;sequence>
 *                   &lt;element name="LicenseGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *                   &lt;element name="LicenseVersion" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DisqualifiedPersonResult" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/licenses/}DisqualifiedPersonType" maxOccurs="unbounded"/>
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
    "license",
    "disqualifiedPersonResult"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "ImportResult")
    protected List<CommonResultType> importResult;
    @XmlElement(name = "License")
    protected List<GetStateResult.License> license;
    @XmlElement(name = "DisqualifiedPersonResult")
    protected List<DisqualifiedPersonType> disqualifiedPersonResult;

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
     * Gets the value of the license property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the license property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLicense().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetStateResult.License }
     * 
     * 
     */
    public List<GetStateResult.License> getLicense() {
        if (license == null) {
            license = new ArrayList<GetStateResult.License>();
        }
        return this.license;
    }

    /**
     * Gets the value of the disqualifiedPersonResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the disqualifiedPersonResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDisqualifiedPersonResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DisqualifiedPersonType }
     * 
     * 
     */
    public List<DisqualifiedPersonType> getDisqualifiedPersonResult() {
        if (disqualifiedPersonResult == null) {
            disqualifiedPersonResult = new ArrayList<DisqualifiedPersonType>();
        }
        return this.disqualifiedPersonResult;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/licenses/}LicenseType">
     *       &lt;sequence>
     *         &lt;element name="LicenseGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
     *         &lt;element name="LicenseVersion" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
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
        "licenseGUID",
        "licenseVersion"
    })
    public static class License
        extends LicenseType
    {

        @XmlElement(name = "LicenseGUID", required = true)
        protected String licenseGUID;
        @XmlElement(name = "LicenseVersion", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger licenseVersion;

        /**
         * Gets the value of the licenseGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLicenseGUID() {
            return licenseGUID;
        }

        /**
         * Sets the value of the licenseGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLicenseGUID(String value) {
            this.licenseGUID = value;
        }

        /**
         * Gets the value of the licenseVersion property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getLicenseVersion() {
            return licenseVersion;
        }

        /**
         * Sets the value of the licenseVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setLicenseVersion(BigInteger value) {
            this.licenseVersion = value;
        }

    }

}


package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for exportStatusPublicPropertyContractResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportStatusPublicPropertyContractResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PublicPropertyContract" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PublicPropertyContractType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ContractGUID"/>
 *                   &lt;element name="StatusContract" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ContractVersionGUID"/>
 *                   &lt;element name="VersionNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="StatusVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportStatusPublicPropertyContractResultType", propOrder = {
    "publicPropertyContract"
})
public class ExportStatusPublicPropertyContractResultType {

    @XmlElement(name = "PublicPropertyContract", required = true)
    protected List<ExportStatusPublicPropertyContractResultType.PublicPropertyContract> publicPropertyContract;

    /**
     * Gets the value of the publicPropertyContract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the publicPropertyContract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPublicPropertyContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportStatusPublicPropertyContractResultType.PublicPropertyContract }
     * 
     * 
     */
    public List<ExportStatusPublicPropertyContractResultType.PublicPropertyContract> getPublicPropertyContract() {
        if (publicPropertyContract == null) {
            publicPropertyContract = new ArrayList<ExportStatusPublicPropertyContractResultType.PublicPropertyContract>();
        }
        return this.publicPropertyContract;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PublicPropertyContractType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ContractGUID"/>
     *         &lt;element name="StatusContract" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ContractVersionGUID"/>
     *         &lt;element name="VersionNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="StatusVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "contractGUID",
        "statusContract",
        "contractVersionGUID",
        "versionNumber",
        "statusVersion"
    })
    public static class PublicPropertyContract
        extends PublicPropertyContractType
    {

        @XmlElement(name = "ContractGUID", required = true)
        protected String contractGUID;
        @XmlElement(name = "StatusContract", required = true)
        protected String statusContract;
        @XmlElement(name = "ContractVersionGUID", required = true)
        protected String contractVersionGUID;
        @XmlElement(name = "VersionNumber", required = true)
        protected BigInteger versionNumber;
        @XmlElement(name = "StatusVersion", required = true)
        protected String statusVersion;

        /**
         * Gets the value of the contractGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractGUID() {
            return contractGUID;
        }

        /**
         * Sets the value of the contractGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractGUID(String value) {
            this.contractGUID = value;
        }

        /**
         * Gets the value of the statusContract property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusContract() {
            return statusContract;
        }

        /**
         * Sets the value of the statusContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusContract(String value) {
            this.statusContract = value;
        }

        /**
         * Gets the value of the contractVersionGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractVersionGUID() {
            return contractVersionGUID;
        }

        /**
         * Sets the value of the contractVersionGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractVersionGUID(String value) {
            this.contractVersionGUID = value;
        }

        /**
         * Gets the value of the versionNumber property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getVersionNumber() {
            return versionNumber;
        }

        /**
         * Sets the value of the versionNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setVersionNumber(BigInteger value) {
            this.versionNumber = value;
        }

        /**
         * Gets the value of the statusVersion property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatusVersion() {
            return statusVersion;
        }

        /**
         * Sets the value of the statusVersion property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatusVersion(String value) {
            this.statusVersion = value;
        }

    }

}

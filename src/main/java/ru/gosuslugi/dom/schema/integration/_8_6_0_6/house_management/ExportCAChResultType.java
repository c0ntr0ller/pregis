
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * <p>Java class for exportCAChResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportCAChResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Contract">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractType">
 *                 &lt;sequence>
 *                   &lt;element name="Terminate" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
 *                           &lt;sequence>
 *                             &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractStatus"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractGUID"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractVersionGUID"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Charter">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterType">
 *                 &lt;sequence>
 *                   &lt;element name="Terminate" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
 *                           &lt;sequence>
 *                             &lt;element name="Reason">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}LongTextType">
 *                                   &lt;maxLength value="255"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterStatus"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterGUID"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterVersionGUID"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportCAChResultType", propOrder = {
    "contract",
    "charter"
})
public class ExportCAChResultType {

    @XmlElement(name = "Contract")
    protected ExportCAChResultType.Contract contract;
    @XmlElement(name = "Charter")
    protected ExportCAChResultType.Charter charter;

    /**
     * Gets the value of the contract property.
     * 
     * @return
     *     possible object is
     *     {@link ExportCAChResultType.Contract }
     *     
     */
    public ExportCAChResultType.Contract getContract() {
        return contract;
    }

    /**
     * Sets the value of the contract property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportCAChResultType.Contract }
     *     
     */
    public void setContract(ExportCAChResultType.Contract value) {
        this.contract = value;
    }

    /**
     * Gets the value of the charter property.
     * 
     * @return
     *     possible object is
     *     {@link ExportCAChResultType.Charter }
     *     
     */
    public ExportCAChResultType.Charter getCharter() {
        return charter;
    }

    /**
     * Sets the value of the charter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportCAChResultType.Charter }
     *     
     */
    public void setCharter(ExportCAChResultType.Charter value) {
        this.charter = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterType">
     *       &lt;sequence>
     *         &lt;element name="Terminate" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
     *                 &lt;sequence>
     *                   &lt;element name="Reason">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}LongTextType">
     *                         &lt;maxLength value="255"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterStatus"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterGUID"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}CharterVersionGUID"/>
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
        "terminate",
        "charterStatus",
        "charterGUID",
        "charterVersionGUID"
    })
    public static class Charter
        extends CharterType
    {

        @XmlElement(name = "Terminate")
        protected ExportCAChResultType.Charter.Terminate terminate;
        @XmlElement(name = "CharterStatus", required = true)
        protected String charterStatus;
        @XmlElement(name = "CharterGUID", required = true)
        protected String charterGUID;
        @XmlElement(name = "CharterVersionGUID", required = true)
        protected String charterVersionGUID;

        /**
         * Gets the value of the terminate property.
         * 
         * @return
         *     possible object is
         *     {@link ExportCAChResultType.Charter.Terminate }
         *     
         */
        public ExportCAChResultType.Charter.Terminate getTerminate() {
            return terminate;
        }

        /**
         * Sets the value of the terminate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportCAChResultType.Charter.Terminate }
         *     
         */
        public void setTerminate(ExportCAChResultType.Charter.Terminate value) {
            this.terminate = value;
        }

        /**
         * Gets the value of the charterStatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCharterStatus() {
            return charterStatus;
        }

        /**
         * Sets the value of the charterStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCharterStatus(String value) {
            this.charterStatus = value;
        }

        /**
         * Gets the value of the charterGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCharterGUID() {
            return charterGUID;
        }

        /**
         * Sets the value of the charterGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCharterGUID(String value) {
            this.charterGUID = value;
        }

        /**
         * Gets the value of the charterVersionGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCharterVersionGUID() {
            return charterVersionGUID;
        }

        /**
         * Sets the value of the charterVersionGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCharterVersionGUID(String value) {
            this.charterVersionGUID = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
         *       &lt;sequence>
         *         &lt;element name="Reason">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}LongTextType">
         *               &lt;maxLength value="255"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
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
            "reason"
        })
        public static class Terminate
            extends TerminateType
        {

            @XmlElement(name = "Reason", required = true)
            protected String reason;

            /**
             * Gets the value of the reason property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getReason() {
                return reason;
            }

            /**
             * Sets the value of the reason property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setReason(String value) {
                this.reason = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractType">
     *       &lt;sequence>
     *         &lt;element name="Terminate" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
     *                 &lt;sequence>
     *                   &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractStatus"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractGUID"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ContractVersionGUID"/>
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
        "terminate",
        "contractStatus",
        "contractGUID",
        "contractVersionGUID"
    })
    public static class Contract
        extends ContractType
    {

        @XmlElement(name = "Terminate")
        protected ExportCAChResultType.Contract.Terminate terminate;
        @XmlElement(name = "ContractStatus", required = true)
        protected String contractStatus;
        @XmlElement(name = "ContractGUID", required = true)
        protected String contractGUID;
        @XmlElement(name = "ContractVersionGUID", required = true)
        protected String contractVersionGUID;

        /**
         * Gets the value of the terminate property.
         * 
         * @return
         *     possible object is
         *     {@link ExportCAChResultType.Contract.Terminate }
         *     
         */
        public ExportCAChResultType.Contract.Terminate getTerminate() {
            return terminate;
        }

        /**
         * Sets the value of the terminate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportCAChResultType.Contract.Terminate }
         *     
         */
        public void setTerminate(ExportCAChResultType.Contract.Terminate value) {
            this.terminate = value;
        }

        /**
         * Gets the value of the contractStatus property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractStatus() {
            return contractStatus;
        }

        /**
         * Sets the value of the contractStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractStatus(String value) {
            this.contractStatus = value;
        }

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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}TerminateType">
         *       &lt;sequence>
         *         &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
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
            "reasonRef"
        })
        public static class Terminate
            extends TerminateType
        {

            @XmlElement(name = "ReasonRef", required = true)
            protected NsiRef reasonRef;

            /**
             * Gets the value of the reasonRef property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getReasonRef() {
                return reasonRef;
            }

            /**
             * Sets the value of the reasonRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setReasonRef(NsiRef value) {
                this.reasonRef = value;
            }

        }

    }

}

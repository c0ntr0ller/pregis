
package ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_6_0.RegOrgType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="DataProvider" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}TransportGUID"/>
 *                   &lt;choice>
 *                     &lt;element name="AllocateSenderID">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrg"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="RemoveSenderID">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="SenderID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
    "dataProvider"
})
@XmlRootElement(name = "importDataProviderRequest")
public class ImportDataProviderRequest
    extends BaseType
{

    @XmlElement(name = "DataProvider", required = true)
    protected List<ImportDataProviderRequest.DataProvider> dataProvider;

    /**
     * Gets the value of the dataProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportDataProviderRequest.DataProvider }
     * 
     * 
     */
    public List<ImportDataProviderRequest.DataProvider> getDataProvider() {
        if (dataProvider == null) {
            dataProvider = new ArrayList<ImportDataProviderRequest.DataProvider>();
        }
        return this.dataProvider;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}TransportGUID"/>
     *         &lt;choice>
     *           &lt;element name="AllocateSenderID">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrg"/>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="RemoveSenderID">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="SenderID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/choice>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transportGUID",
        "allocateSenderID",
        "removeSenderID"
    })
    public static class DataProvider {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/", required = true)
        protected String transportGUID;
        @XmlElement(name = "AllocateSenderID")
        protected ImportDataProviderRequest.DataProvider.AllocateSenderID allocateSenderID;
        @XmlElement(name = "RemoveSenderID")
        protected ImportDataProviderRequest.DataProvider.RemoveSenderID removeSenderID;

        /**
         * Gets the value of the transportGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransportGUID() {
            return transportGUID;
        }

        /**
         * Sets the value of the transportGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransportGUID(String value) {
            this.transportGUID = value;
        }

        /**
         * Gets the value of the allocateSenderID property.
         * 
         * @return
         *     possible object is
         *     {@link ImportDataProviderRequest.DataProvider.AllocateSenderID }
         *     
         */
        public ImportDataProviderRequest.DataProvider.AllocateSenderID getAllocateSenderID() {
            return allocateSenderID;
        }

        /**
         * Sets the value of the allocateSenderID property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportDataProviderRequest.DataProvider.AllocateSenderID }
         *     
         */
        public void setAllocateSenderID(ImportDataProviderRequest.DataProvider.AllocateSenderID value) {
            this.allocateSenderID = value;
        }

        /**
         * Gets the value of the removeSenderID property.
         * 
         * @return
         *     possible object is
         *     {@link ImportDataProviderRequest.DataProvider.RemoveSenderID }
         *     
         */
        public ImportDataProviderRequest.DataProvider.RemoveSenderID getRemoveSenderID() {
            return removeSenderID;
        }

        /**
         * Sets the value of the removeSenderID property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportDataProviderRequest.DataProvider.RemoveSenderID }
         *     
         */
        public void setRemoveSenderID(ImportDataProviderRequest.DataProvider.RemoveSenderID value) {
            this.removeSenderID = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrg"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "regOrg"
        })
        public static class AllocateSenderID {

            @XmlElement(name = "RegOrg", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/", required = true)
            protected RegOrgType regOrg;

            /**
             * Gets the value of the regOrg property.
             * 
             * @return
             *     possible object is
             *     {@link RegOrgType }
             *     
             */
            public RegOrgType getRegOrg() {
                return regOrg;
            }

            /**
             * Sets the value of the regOrg property.
             * 
             * @param value
             *     allowed object is
             *     {@link RegOrgType }
             *     
             */
            public void setRegOrg(RegOrgType value) {
                this.regOrg = value;
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
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="SenderID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "senderID"
        })
        public static class RemoveSenderID {

            @XmlElement(name = "SenderID", required = true)
            protected String senderID;

            /**
             * Gets the value of the senderID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSenderID() {
                return senderID;
            }

            /**
             * Sets the value of the senderID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSenderID(String value) {
                this.senderID = value;
            }

        }

    }

}

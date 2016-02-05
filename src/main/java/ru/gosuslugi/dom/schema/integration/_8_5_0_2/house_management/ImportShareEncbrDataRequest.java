
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="ShareEnc" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Share" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
 *                             &lt;choice>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToCreate"/>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToUpdate"/>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Ecnbr" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TransportGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                             &lt;choice>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToCreate"/>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToUpdate"/>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "shareEnc"
})
@XmlRootElement(name = "importShareEncbrDataRequest")
public class ImportShareEncbrDataRequest
    extends BaseType
{

    @XmlElement(name = "ShareEnc", required = true)
    protected List<ImportShareEncbrDataRequest.ShareEnc> shareEnc;

    /**
     * Gets the value of the shareEnc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shareEnc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShareEnc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportShareEncbrDataRequest.ShareEnc }
     * 
     * 
     */
    public List<ImportShareEncbrDataRequest.ShareEnc> getShareEnc() {
        if (shareEnc == null) {
            shareEnc = new ArrayList<ImportShareEncbrDataRequest.ShareEnc>();
        }
        return this.shareEnc;
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
     *         &lt;element name="Share" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
     *                   &lt;choice>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToCreate"/>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToUpdate"/>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Ecnbr" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TransportGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
     *                   &lt;choice>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToCreate"/>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToUpdate"/>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
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
    @XmlType(name = "", propOrder = {
        "share",
        "ecnbr"
    })
    public static class ShareEnc {

        @XmlElement(name = "Share")
        protected ImportShareEncbrDataRequest.ShareEnc.Share share;
        @XmlElement(name = "Ecnbr")
        protected List<ImportShareEncbrDataRequest.ShareEnc.Ecnbr> ecnbr;

        /**
         * Gets the value of the share property.
         * 
         * @return
         *     possible object is
         *     {@link ImportShareEncbrDataRequest.ShareEnc.Share }
         *     
         */
        public ImportShareEncbrDataRequest.ShareEnc.Share getShare() {
            return share;
        }

        /**
         * Sets the value of the share property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportShareEncbrDataRequest.ShareEnc.Share }
         *     
         */
        public void setShare(ImportShareEncbrDataRequest.ShareEnc.Share value) {
            this.share = value;
        }

        /**
         * Gets the value of the ecnbr property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ecnbr property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEcnbr().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImportShareEncbrDataRequest.ShareEnc.Ecnbr }
         * 
         * 
         */
        public List<ImportShareEncbrDataRequest.ShareEnc.Ecnbr> getEcnbr() {
            if (ecnbr == null) {
                ecnbr = new ArrayList<ImportShareEncbrDataRequest.ShareEnc.Ecnbr>();
            }
            return this.ecnbr;
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
         *         &lt;element name="TransportGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
         *         &lt;choice>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToCreate"/>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}EncbrDataToUpdate"/>
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
            "encbrDataToCreate",
            "encbrDataToUpdate"
        })
        public static class Ecnbr {

            @XmlElement(name = "TransportGUID", required = true)
            protected String transportGUID;
            @XmlElement(name = "EncbrDataToCreate")
            protected EncbrDataToCreate encbrDataToCreate;
            @XmlElement(name = "EncbrDataToUpdate")
            protected EncbrDataToUpdate encbrDataToUpdate;

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
             * Gets the value of the encbrDataToCreate property.
             * 
             * @return
             *     possible object is
             *     {@link EncbrDataToCreate }
             *     
             */
            public EncbrDataToCreate getEncbrDataToCreate() {
                return encbrDataToCreate;
            }

            /**
             * Sets the value of the encbrDataToCreate property.
             * 
             * @param value
             *     allowed object is
             *     {@link EncbrDataToCreate }
             *     
             */
            public void setEncbrDataToCreate(EncbrDataToCreate value) {
                this.encbrDataToCreate = value;
            }

            /**
             * Gets the value of the encbrDataToUpdate property.
             * 
             * @return
             *     possible object is
             *     {@link EncbrDataToUpdate }
             *     
             */
            public EncbrDataToUpdate getEncbrDataToUpdate() {
                return encbrDataToUpdate;
            }

            /**
             * Sets the value of the encbrDataToUpdate property.
             * 
             * @param value
             *     allowed object is
             *     {@link EncbrDataToUpdate }
             *     
             */
            public void setEncbrDataToUpdate(EncbrDataToUpdate value) {
                this.encbrDataToUpdate = value;
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
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
         *         &lt;choice>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToCreate"/>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ShareDataToUpdate"/>
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
            "shareDataToCreate",
            "shareDataToUpdate"
        })
        public static class Share {

            @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
            protected String transportGUID;
            @XmlElement(name = "ShareDataToCreate")
            protected ShareDataType shareDataToCreate;
            @XmlElement(name = "ShareDataToUpdate")
            protected ShareDataToUpdate shareDataToUpdate;

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
             * Gets the value of the shareDataToCreate property.
             * 
             * @return
             *     possible object is
             *     {@link ShareDataType }
             *     
             */
            public ShareDataType getShareDataToCreate() {
                return shareDataToCreate;
            }

            /**
             * Sets the value of the shareDataToCreate property.
             * 
             * @param value
             *     allowed object is
             *     {@link ShareDataType }
             *     
             */
            public void setShareDataToCreate(ShareDataType value) {
                this.shareDataToCreate = value;
            }

            /**
             * Gets the value of the shareDataToUpdate property.
             * 
             * @return
             *     possible object is
             *     {@link ShareDataToUpdate }
             *     
             */
            public ShareDataToUpdate getShareDataToUpdate() {
                return shareDataToUpdate;
            }

            /**
             * Sets the value of the shareDataToUpdate property.
             * 
             * @param value
             *     allowed object is
             *     {@link ShareDataToUpdate }
             *     
             */
            public void setShareDataToUpdate(ShareDataToUpdate value) {
                this.shareDataToUpdate = value;
            }

        }

    }

}

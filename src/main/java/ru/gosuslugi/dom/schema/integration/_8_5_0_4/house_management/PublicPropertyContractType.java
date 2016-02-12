
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_5_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_5_0.RegOrgType;


/**
 * <p>Java class for PublicPropertyContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublicPropertyContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="Organization" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}RegOrgType"/>
 *           &lt;element name="Entrepreneur" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}IndType"/>
 *         &lt;/choice>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *         &lt;element name="ContractObject" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ContractNumber" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}ContractNumberType"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Comments" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ContractAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
 *         &lt;element name="RentAgrConfirmationDocument" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ProtocolMeetingOwners" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ProtocolNum">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;maxLength value="30"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="ProtocolDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="TrustDocAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "PublicPropertyContractType", propOrder = {
    "organization",
    "entrepreneur",
    "fiasHouseGuid",
    "contractObject",
    "contractNumber",
    "startDate",
    "endDate",
    "comments",
    "contractAttachment",
    "rentAgrConfirmationDocument"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ExportStatusPublicPropertyContractResultType.PublicPropertyContract.class
})
public class PublicPropertyContractType {

    @XmlElement(name = "Organization")
    protected RegOrgType organization;
    @XmlElement(name = "Entrepreneur")
    protected IndType entrepreneur;
    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "ContractObject")
    protected String contractObject;
    @XmlElement(name = "ContractNumber", required = true)
    protected String contractNumber;
    @XmlElement(name = "StartDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "ContractAttachment", required = true)
    protected List<AttachmentType> contractAttachment;
    @XmlElement(name = "RentAgrConfirmationDocument", required = true)
    protected List<PublicPropertyContractType.RentAgrConfirmationDocument> rentAgrConfirmationDocument;

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link RegOrgType }
     *     
     */
    public RegOrgType getOrganization() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgType }
     *     
     */
    public void setOrganization(RegOrgType value) {
        this.organization = value;
    }

    /**
     * Gets the value of the entrepreneur property.
     * 
     * @return
     *     possible object is
     *     {@link IndType }
     *     
     */
    public IndType getEntrepreneur() {
        return entrepreneur;
    }

    /**
     * Sets the value of the entrepreneur property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndType }
     *     
     */
    public void setEntrepreneur(IndType value) {
        this.entrepreneur = value;
    }

    /**
     * Gets the value of the fiasHouseGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIASHouseGuid() {
        return fiasHouseGuid;
    }

    /**
     * Sets the value of the fiasHouseGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIASHouseGuid(String value) {
        this.fiasHouseGuid = value;
    }

    /**
     * Gets the value of the contractObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractObject() {
        return contractObject;
    }

    /**
     * Sets the value of the contractObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractObject(String value) {
        this.contractObject = value;
    }

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the contractAttachment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractAttachment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractAttachment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentType }
     * 
     * 
     */
    public List<AttachmentType> getContractAttachment() {
        if (contractAttachment == null) {
            contractAttachment = new ArrayList<AttachmentType>();
        }
        return this.contractAttachment;
    }

    /**
     * Gets the value of the rentAgrConfirmationDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rentAgrConfirmationDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRentAgrConfirmationDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PublicPropertyContractType.RentAgrConfirmationDocument }
     * 
     * 
     */
    public List<PublicPropertyContractType.RentAgrConfirmationDocument> getRentAgrConfirmationDocument() {
        if (rentAgrConfirmationDocument == null) {
            rentAgrConfirmationDocument = new ArrayList<PublicPropertyContractType.RentAgrConfirmationDocument>();
        }
        return this.rentAgrConfirmationDocument;
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
     *         &lt;element name="ProtocolMeetingOwners" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ProtocolNum">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;maxLength value="30"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="ProtocolDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="TrustDocAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" maxOccurs="unbounded" minOccurs="0"/>
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
        "protocolMeetingOwners",
        "protocolGUID"
    })
    public static class RentAgrConfirmationDocument {

        @XmlElement(name = "ProtocolMeetingOwners")
        protected List<PublicPropertyContractType.RentAgrConfirmationDocument.ProtocolMeetingOwners> protocolMeetingOwners;
        @XmlElement(name = "ProtocolGUID")
        protected List<String> protocolGUID;

        /**
         * Gets the value of the protocolMeetingOwners property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the protocolMeetingOwners property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProtocolMeetingOwners().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PublicPropertyContractType.RentAgrConfirmationDocument.ProtocolMeetingOwners }
         * 
         * 
         */
        public List<PublicPropertyContractType.RentAgrConfirmationDocument.ProtocolMeetingOwners> getProtocolMeetingOwners() {
            if (protocolMeetingOwners == null) {
                protocolMeetingOwners = new ArrayList<PublicPropertyContractType.RentAgrConfirmationDocument.ProtocolMeetingOwners>();
            }
            return this.protocolMeetingOwners;
        }

        /**
         * Gets the value of the protocolGUID property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the protocolGUID property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProtocolGUID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getProtocolGUID() {
            if (protocolGUID == null) {
                protocolGUID = new ArrayList<String>();
            }
            return this.protocolGUID;
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
         *         &lt;element name="ProtocolNum">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="30"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="ProtocolDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="TrustDocAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
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
            "protocolNum",
            "protocolDate",
            "trustDocAttachment"
        })
        public static class ProtocolMeetingOwners {

            @XmlElement(name = "ProtocolNum", required = true)
            protected String protocolNum;
            @XmlElement(name = "ProtocolDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar protocolDate;
            @XmlElement(name = "TrustDocAttachment", required = true)
            protected List<AttachmentType> trustDocAttachment;

            /**
             * Gets the value of the protocolNum property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getProtocolNum() {
                return protocolNum;
            }

            /**
             * Sets the value of the protocolNum property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setProtocolNum(String value) {
                this.protocolNum = value;
            }

            /**
             * Gets the value of the protocolDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getProtocolDate() {
                return protocolDate;
            }

            /**
             * Sets the value of the protocolDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setProtocolDate(XMLGregorianCalendar value) {
                this.protocolDate = value;
            }

            /**
             * Gets the value of the trustDocAttachment property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the trustDocAttachment property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTrustDocAttachment().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttachmentType }
             * 
             * 
             */
            public List<AttachmentType> getTrustDocAttachment() {
                if (trustDocAttachment == null) {
                    trustDocAttachment = new ArrayList<AttachmentType>();
                }
                return this.trustDocAttachment;
            }

        }

    }

}

package ru.gosuslugi.dom.schema.integration.services.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration.base.AttachmentType;
import ru.gosuslugi.dom.schema.integration.base.IndType;
import ru.gosuslugi.dom.schema.integration.base.RegOrgType;


/**
 * Устав
 * 
 * <p>Java class for CharterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CharterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}LongTextType">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DateDetails" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}DateDetailsType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MeetingProtocol">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="ProtocolMeetingOwners" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}AttachmentType" maxOccurs="unbounded"/>
 *                   &lt;element name="VotingProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" maxOccurs="100"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ChiefExecutive">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Head" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;choice>
 *                             &lt;element name="IndOwner" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
 *                             &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
 *                             &lt;element name="Org" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}RegOrgType"/>
 *                           &lt;/choice>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Managers">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}LongTextType">
 *                         &lt;maxLength value="1000"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ContractObjectList" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ContractObject">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ManageObjectType">
 *                           &lt;sequence>
 *                             &lt;element name="BaseMService" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
 *                             &lt;element name="HouseService" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
 *                                     &lt;sequence>
 *                                       &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="AddService" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
 *                                     &lt;sequence>
 *                                       &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Exclusion" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="DateExclusion" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                       &lt;element name="BaseExclusion" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="StatusObject" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}StatusMKDType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AttachmentCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}AttachmentType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CharterType", propOrder = {
    "docNum",
    "date",
    "dateDetails",
    "meetingProtocol",
    "chiefExecutive",
    "contractObjectList",
    "attachmentCharter"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.house_management.ImportCharterRequest.Charter.class,
    ru.gosuslugi.dom.schema.integration.services.house_management.ExportCAChResultType.Charter.class
})
public class CharterType {

    @XmlElement(name = "DocNum", required = true)
    protected String docNum;
    @XmlElement(name = "Date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "DateDetails")
    protected CharterType.DateDetails dateDetails;
    @XmlElement(name = "MeetingProtocol", required = true)
    protected CharterType.MeetingProtocol meetingProtocol;
    @XmlElement(name = "ChiefExecutive", required = true)
    protected CharterType.ChiefExecutive chiefExecutive;
    @XmlElement(name = "ContractObjectList", required = true)
    protected List<CharterType.ContractObjectList> contractObjectList;
    @XmlElement(name = "AttachmentCharter", required = true)
    protected List<AttachmentType> attachmentCharter;

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the dateDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CharterType.DateDetails }
     *     
     */
    public CharterType.DateDetails getDateDetails() {
        return dateDetails;
    }

    /**
     * Sets the value of the dateDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharterType.DateDetails }
     *     
     */
    public void setDateDetails(CharterType.DateDetails value) {
        this.dateDetails = value;
    }

    /**
     * Gets the value of the meetingProtocol property.
     * 
     * @return
     *     possible object is
     *     {@link CharterType.MeetingProtocol }
     *     
     */
    public CharterType.MeetingProtocol getMeetingProtocol() {
        return meetingProtocol;
    }

    /**
     * Sets the value of the meetingProtocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharterType.MeetingProtocol }
     *     
     */
    public void setMeetingProtocol(CharterType.MeetingProtocol value) {
        this.meetingProtocol = value;
    }

    /**
     * Gets the value of the chiefExecutive property.
     * 
     * @return
     *     possible object is
     *     {@link CharterType.ChiefExecutive }
     *     
     */
    public CharterType.ChiefExecutive getChiefExecutive() {
        return chiefExecutive;
    }

    /**
     * Sets the value of the chiefExecutive property.
     * 
     * @param value
     *     allowed object is
     *     {@link CharterType.ChiefExecutive }
     *     
     */
    public void setChiefExecutive(CharterType.ChiefExecutive value) {
        this.chiefExecutive = value;
    }

    /**
     * Gets the value of the contractObjectList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractObjectList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractObjectList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CharterType.ContractObjectList }
     * 
     * 
     */
    public List<CharterType.ContractObjectList> getContractObjectList() {
        if (contractObjectList == null) {
            contractObjectList = new ArrayList<CharterType.ContractObjectList>();
        }
        return this.contractObjectList;
    }

    /**
     * Gets the value of the attachmentCharter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachmentCharter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachmentCharter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentType }
     * 
     * 
     */
    public List<AttachmentType> getAttachmentCharter() {
        if (attachmentCharter == null) {
            attachmentCharter = new ArrayList<AttachmentType>();
        }
        return this.attachmentCharter;
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
     *         &lt;element name="Head" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;choice>
     *                   &lt;element name="IndOwner" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
     *                   &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
     *                   &lt;element name="Org" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}RegOrgType"/>
     *                 &lt;/choice>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Managers">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}LongTextType">
     *               &lt;maxLength value="1000"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
        "head",
        "managers"
    })
    public static class ChiefExecutive {

        @XmlElement(name = "Head")
        protected CharterType.ChiefExecutive.Head head;
        @XmlElement(name = "Managers", required = true)
        protected String managers;

        /**
         * Gets the value of the head property.
         * 
         * @return
         *     possible object is
         *     {@link CharterType.ChiefExecutive.Head }
         *     
         */
        public CharterType.ChiefExecutive.Head getHead() {
            return head;
        }

        /**
         * Sets the value of the head property.
         * 
         * @param value
         *     allowed object is
         *     {@link CharterType.ChiefExecutive.Head }
         *     
         */
        public void setHead(CharterType.ChiefExecutive.Head value) {
            this.head = value;
        }

        /**
         * Gets the value of the managers property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getManagers() {
            return managers;
        }

        /**
         * Sets the value of the managers property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setManagers(String value) {
            this.managers = value;
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
         *       &lt;choice>
         *         &lt;element name="IndOwner" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
         *         &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}IndType"/>
         *         &lt;element name="Org" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}RegOrgType"/>
         *       &lt;/choice>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "indOwner",
            "ind",
            "org"
        })
        public static class Head {

            @XmlElement(name = "IndOwner")
            protected IndType indOwner;
            @XmlElement(name = "Ind")
            protected IndType ind;
            @XmlElement(name = "Org")
            protected RegOrgType org;

            /**
             * Gets the value of the indOwner property.
             * 
             * @return
             *     possible object is
             *     {@link IndType }
             *     
             */
            public IndType getIndOwner() {
                return indOwner;
            }

            /**
             * Sets the value of the indOwner property.
             * 
             * @param value
             *     allowed object is
             *     {@link IndType }
             *     
             */
            public void setIndOwner(IndType value) {
                this.indOwner = value;
            }

            /**
             * Gets the value of the ind property.
             * 
             * @return
             *     possible object is
             *     {@link IndType }
             *     
             */
            public IndType getInd() {
                return ind;
            }

            /**
             * Sets the value of the ind property.
             * 
             * @param value
             *     allowed object is
             *     {@link IndType }
             *     
             */
            public void setInd(IndType value) {
                this.ind = value;
            }

            /**
             * Gets the value of the org property.
             * 
             * @return
             *     possible object is
             *     {@link RegOrgType }
             *     
             */
            public RegOrgType getOrg() {
                return org;
            }

            /**
             * Sets the value of the org property.
             * 
             * @param value
             *     allowed object is
             *     {@link RegOrgType }
             *     
             */
            public void setOrg(RegOrgType value) {
                this.org = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ContractObject">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ManageObjectType">
     *                 &lt;sequence>
     *                   &lt;element name="BaseMService" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
     *                   &lt;element name="HouseService" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
     *                           &lt;sequence>
     *                             &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="AddService" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
     *                           &lt;sequence>
     *                             &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Exclusion" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="DateExclusion" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                             &lt;element name="BaseExclusion" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="StatusObject" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}StatusMKDType" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "contractObject"
    })
    public static class ContractObjectList {

        @XmlElement(name = "ContractObject", required = true)
        protected CharterType.ContractObjectList.ContractObject contractObject;

        /**
         * Gets the value of the contractObject property.
         * 
         * @return
         *     possible object is
         *     {@link CharterType.ContractObjectList.ContractObject }
         *     
         */
        public CharterType.ContractObjectList.ContractObject getContractObject() {
            return contractObject;
        }

        /**
         * Sets the value of the contractObject property.
         * 
         * @param value
         *     allowed object is
         *     {@link CharterType.ContractObjectList.ContractObject }
         *     
         */
        public void setContractObject(CharterType.ContractObjectList.ContractObject value) {
            this.contractObject = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ManageObjectType">
         *       &lt;sequence>
         *         &lt;element name="BaseMService" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
         *         &lt;element name="HouseService" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
         *                 &lt;sequence>
         *                   &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="AddService" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
         *                 &lt;sequence>
         *                   &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Exclusion" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="DateExclusion" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                   &lt;element name="BaseExclusion" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="StatusObject" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}StatusMKDType" minOccurs="0"/>
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
            "baseMService",
            "houseService",
            "addService",
            "exclusion",
            "statusObject"
        })
        public static class ContractObject
            extends ManageObjectType
        {

            @XmlElement(name = "BaseMService", required = true)
            protected BaseServiceCharterType baseMService;
            @XmlElement(name = "HouseService")
            protected List<CharterType.ContractObjectList.ContractObject.HouseService> houseService;
            @XmlElement(name = "AddService")
            protected List<CharterType.ContractObjectList.ContractObject.AddService> addService;
            @XmlElement(name = "Exclusion")
            protected CharterType.ContractObjectList.ContractObject.Exclusion exclusion;
            @XmlElement(name = "StatusObject")
            @XmlSchemaType(name = "string")
            protected StatusMKDType statusObject;

            /**
             * Gets the value of the baseMService property.
             * 
             * @return
             *     possible object is
             *     {@link BaseServiceCharterType }
             *     
             */
            public BaseServiceCharterType getBaseMService() {
                return baseMService;
            }

            /**
             * Sets the value of the baseMService property.
             * 
             * @param value
             *     allowed object is
             *     {@link BaseServiceCharterType }
             *     
             */
            public void setBaseMService(BaseServiceCharterType value) {
                this.baseMService = value;
            }

            /**
             * Gets the value of the houseService property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the houseService property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getHouseService().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CharterType.ContractObjectList.ContractObject.HouseService }
             * 
             * 
             */
            public List<CharterType.ContractObjectList.ContractObject.HouseService> getHouseService() {
                if (houseService == null) {
                    houseService = new ArrayList<CharterType.ContractObjectList.ContractObject.HouseService>();
                }
                return this.houseService;
            }

            /**
             * Gets the value of the addService property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the addService property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAddService().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CharterType.ContractObjectList.ContractObject.AddService }
             * 
             * 
             */
            public List<CharterType.ContractObjectList.ContractObject.AddService> getAddService() {
                if (addService == null) {
                    addService = new ArrayList<CharterType.ContractObjectList.ContractObject.AddService>();
                }
                return this.addService;
            }

            /**
             * Gets the value of the exclusion property.
             * 
             * @return
             *     possible object is
             *     {@link CharterType.ContractObjectList.ContractObject.Exclusion }
             *     
             */
            public CharterType.ContractObjectList.ContractObject.Exclusion getExclusion() {
                return exclusion;
            }

            /**
             * Sets the value of the exclusion property.
             * 
             * @param value
             *     allowed object is
             *     {@link CharterType.ContractObjectList.ContractObject.Exclusion }
             *     
             */
            public void setExclusion(CharterType.ContractObjectList.ContractObject.Exclusion value) {
                this.exclusion = value;
            }

            /**
             * Gets the value of the statusObject property.
             * 
             * @return
             *     possible object is
             *     {@link StatusMKDType }
             *     
             */
            public StatusMKDType getStatusObject() {
                return statusObject;
            }

            /**
             * Sets the value of the statusObject property.
             * 
             * @param value
             *     allowed object is
             *     {@link StatusMKDType }
             *     
             */
            public void setStatusObject(StatusMKDType value) {
                this.statusObject = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
             *       &lt;sequence>
             *         &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
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
                "baseServiceCharter"
            })
            public static class AddService
                extends ContractServiceType
            {

                @XmlElement(name = "BaseServiceCharter", required = true)
                protected BaseServiceCharterType baseServiceCharter;

                /**
                 * Gets the value of the baseServiceCharter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public BaseServiceCharterType getBaseServiceCharter() {
                    return baseServiceCharter;
                }

                /**
                 * Sets the value of the baseServiceCharter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public void setBaseServiceCharter(BaseServiceCharterType value) {
                    this.baseServiceCharter = value;
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
             *         &lt;element name="DateExclusion" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *         &lt;element name="BaseExclusion" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
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
                "dateExclusion",
                "baseExclusion"
            })
            public static class Exclusion {

                @XmlElement(name = "DateExclusion", required = true)
                @XmlSchemaType(name = "date")
                protected XMLGregorianCalendar dateExclusion;
                @XmlElement(name = "BaseExclusion", required = true)
                protected BaseServiceCharterType baseExclusion;

                /**
                 * Gets the value of the dateExclusion property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getDateExclusion() {
                    return dateExclusion;
                }

                /**
                 * Sets the value of the dateExclusion property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setDateExclusion(XMLGregorianCalendar value) {
                    this.dateExclusion = value;
                }

                /**
                 * Gets the value of the baseExclusion property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public BaseServiceCharterType getBaseExclusion() {
                    return baseExclusion;
                }

                /**
                 * Sets the value of the baseExclusion property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public void setBaseExclusion(BaseServiceCharterType value) {
                    this.baseExclusion = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ContractServiceType">
             *       &lt;sequence>
             *         &lt;element name="BaseServiceCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}BaseServiceCharterType"/>
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
                "baseServiceCharter"
            })
            public static class HouseService
                extends ContractServiceType
            {

                @XmlElement(name = "BaseServiceCharter", required = true)
                protected BaseServiceCharterType baseServiceCharter;

                /**
                 * Gets the value of the baseServiceCharter property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public BaseServiceCharterType getBaseServiceCharter() {
                    return baseServiceCharter;
                }

                /**
                 * Sets the value of the baseServiceCharter property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BaseServiceCharterType }
                 *     
                 */
                public void setBaseServiceCharter(BaseServiceCharterType value) {
                    this.baseServiceCharter = value;
                }

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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}DateDetailsType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DateDetails
        extends DateDetailsType
    {


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
     *       &lt;choice>
     *         &lt;element name="ProtocolMeetingOwners" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}AttachmentType" maxOccurs="unbounded"/>
     *         &lt;element name="VotingProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" maxOccurs="100"/>
     *       &lt;/choice>
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
        "votingProtocolGUID"
    })
    public static class MeetingProtocol {

        @XmlElement(name = "ProtocolMeetingOwners")
        protected List<AttachmentType> protocolMeetingOwners;
        @XmlElement(name = "VotingProtocolGUID")
        protected List<String> votingProtocolGUID;

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
         * {@link AttachmentType }
         * 
         * 
         */
        public List<AttachmentType> getProtocolMeetingOwners() {
            if (protocolMeetingOwners == null) {
                protocolMeetingOwners = new ArrayList<AttachmentType>();
            }
            return this.protocolMeetingOwners;
        }

        /**
         * Gets the value of the votingProtocolGUID property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the votingProtocolGUID property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVotingProtocolGUID().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getVotingProtocolGUID() {
            if (votingProtocolGUID == null) {
                votingProtocolGUID = new ArrayList<String>();
            }
            return this.votingProtocolGUID;
        }

    }

}

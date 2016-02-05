
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_5_0.RegOrgType;


/**
 * <p>Java class for ShareDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShareDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RealEstateType">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="LivingHouse">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
 *                           &lt;sequence>
 *                             &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="NonResidentialPremises" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
 *                           &lt;sequence>
 *                             &lt;element name="NonResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PremisesAndRooms">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
 *                                     &lt;sequence>
 *                                       &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IsPrivatized" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Terminated" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="TermDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Owners">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}RegOrgType"/>
 *                   &lt;element name="Ind" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}IndType">
 *                           &lt;sequence>
 *                             &lt;element name="IsRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;element name="IsResides" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
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
@XmlType(name = "ShareDataType", propOrder = {
    "realEstateType",
    "isPrivatized",
    "terminated",
    "owners"
})
public class ShareDataType {

    @XmlElement(name = "RealEstateType", required = true)
    protected ShareDataType.RealEstateType realEstateType;
    @XmlElement(name = "IsPrivatized", defaultValue = "0")
    protected boolean isPrivatized;
    @XmlElement(name = "Terminated")
    protected ShareDataType.Terminated terminated;
    @XmlElement(name = "Owners", required = true)
    protected ShareDataType.Owners owners;

    /**
     * Gets the value of the realEstateType property.
     * 
     * @return
     *     possible object is
     *     {@link ShareDataType.RealEstateType }
     *     
     */
    public ShareDataType.RealEstateType getRealEstateType() {
        return realEstateType;
    }

    /**
     * Sets the value of the realEstateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareDataType.RealEstateType }
     *     
     */
    public void setRealEstateType(ShareDataType.RealEstateType value) {
        this.realEstateType = value;
    }

    /**
     * Gets the value of the isPrivatized property.
     * 
     */
    public boolean isIsPrivatized() {
        return isPrivatized;
    }

    /**
     * Sets the value of the isPrivatized property.
     * 
     */
    public void setIsPrivatized(boolean value) {
        this.isPrivatized = value;
    }

    /**
     * Gets the value of the terminated property.
     * 
     * @return
     *     possible object is
     *     {@link ShareDataType.Terminated }
     *     
     */
    public ShareDataType.Terminated getTerminated() {
        return terminated;
    }

    /**
     * Sets the value of the terminated property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareDataType.Terminated }
     *     
     */
    public void setTerminated(ShareDataType.Terminated value) {
        this.terminated = value;
    }

    /**
     * Gets the value of the owners property.
     * 
     * @return
     *     possible object is
     *     {@link ShareDataType.Owners }
     *     
     */
    public ShareDataType.Owners getOwners() {
        return owners;
    }

    /**
     * Sets the value of the owners property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareDataType.Owners }
     *     
     */
    public void setOwners(ShareDataType.Owners value) {
        this.owners = value;
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
     *         &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}RegOrgType"/>
     *         &lt;element name="Ind" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}IndType">
     *                 &lt;sequence>
     *                   &lt;element name="IsRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;element name="IsResides" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    @XmlType(name = "", propOrder = {
        "regOrg",
        "ind"
    })
    public static class Owners {

        @XmlElement(name = "RegOrg")
        protected RegOrgType regOrg;
        @XmlElement(name = "Ind")
        protected List<ShareDataType.Owners.Ind> ind;

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

        /**
         * Gets the value of the ind property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ind property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInd().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ShareDataType.Owners.Ind }
         * 
         * 
         */
        public List<ShareDataType.Owners.Ind> getInd() {
            if (ind == null) {
                ind = new ArrayList<ShareDataType.Owners.Ind>();
            }
            return this.ind;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}IndType">
         *       &lt;sequence>
         *         &lt;element name="IsRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *         &lt;element name="IsResides" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
            "isRegistered",
            "isResides"
        })
        public static class Ind
            extends IndType
        {

            @XmlElement(name = "IsRegistered")
            protected boolean isRegistered;
            @XmlElement(name = "IsResides")
            protected boolean isResides;

            /**
             * Gets the value of the isRegistered property.
             * 
             */
            public boolean isIsRegistered() {
                return isRegistered;
            }

            /**
             * Sets the value of the isRegistered property.
             * 
             */
            public void setIsRegistered(boolean value) {
                this.isRegistered = value;
            }

            /**
             * Gets the value of the isResides property.
             * 
             */
            public boolean isIsResides() {
                return isResides;
            }

            /**
             * Sets the value of the isResides property.
             * 
             */
            public void setIsResides(boolean value) {
                this.isResides = value;
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
     *       &lt;choice>
     *         &lt;element name="LivingHouse">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
     *                 &lt;sequence>
     *                   &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="NonResidentialPremises" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
     *                 &lt;sequence>
     *                   &lt;element name="NonResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="PremisesAndRooms">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
     *                           &lt;sequence>
     *                             &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
     *                           &lt;sequence>
     *                             &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
        "livingHouse",
        "nonResidentialPremises",
        "premisesAndRooms"
    })
    public static class RealEstateType {

        @XmlElement(name = "LivingHouse")
        protected ShareDataType.RealEstateType.LivingHouse livingHouse;
        @XmlElement(name = "NonResidentialPremises")
        protected List<ShareDataType.RealEstateType.NonResidentialPremises> nonResidentialPremises;
        @XmlElement(name = "PremisesAndRooms")
        protected ShareDataType.RealEstateType.PremisesAndRooms premisesAndRooms;

        /**
         * Gets the value of the livingHouse property.
         * 
         * @return
         *     possible object is
         *     {@link ShareDataType.RealEstateType.LivingHouse }
         *     
         */
        public ShareDataType.RealEstateType.LivingHouse getLivingHouse() {
            return livingHouse;
        }

        /**
         * Sets the value of the livingHouse property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShareDataType.RealEstateType.LivingHouse }
         *     
         */
        public void setLivingHouse(ShareDataType.RealEstateType.LivingHouse value) {
            this.livingHouse = value;
        }

        /**
         * Gets the value of the nonResidentialPremises property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the nonResidentialPremises property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNonResidentialPremises().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ShareDataType.RealEstateType.NonResidentialPremises }
         * 
         * 
         */
        public List<ShareDataType.RealEstateType.NonResidentialPremises> getNonResidentialPremises() {
            if (nonResidentialPremises == null) {
                nonResidentialPremises = new ArrayList<ShareDataType.RealEstateType.NonResidentialPremises>();
            }
            return this.nonResidentialPremises;
        }

        /**
         * Gets the value of the premisesAndRooms property.
         * 
         * @return
         *     possible object is
         *     {@link ShareDataType.RealEstateType.PremisesAndRooms }
         *     
         */
        public ShareDataType.RealEstateType.PremisesAndRooms getPremisesAndRooms() {
            return premisesAndRooms;
        }

        /**
         * Sets the value of the premisesAndRooms property.
         * 
         * @param value
         *     allowed object is
         *     {@link ShareDataType.RealEstateType.PremisesAndRooms }
         *     
         */
        public void setPremisesAndRooms(ShareDataType.RealEstateType.PremisesAndRooms value) {
            this.premisesAndRooms = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
         *       &lt;sequence>
         *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
            "fiasHouseGuid"
        })
        public static class LivingHouse
            extends PartSizeType
        {

            @XmlElement(name = "FIASHouseGuid", required = true)
            protected String fiasHouseGuid;

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

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
         *       &lt;sequence>
         *         &lt;element name="NonResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
            "nonResidentialPremisesGUID"
        })
        public static class NonResidentialPremises
            extends PartSizeType
        {

            @XmlElement(name = "NonResidentialPremisesGUID", required = true)
            protected String nonResidentialPremisesGUID;

            /**
             * Gets the value of the nonResidentialPremisesGUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNonResidentialPremisesGUID() {
                return nonResidentialPremisesGUID;
            }

            /**
             * Sets the value of the nonResidentialPremisesGUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNonResidentialPremisesGUID(String value) {
                this.nonResidentialPremisesGUID = value;
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
         *         &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
         *                 &lt;sequence>
         *                   &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
         *                 &lt;sequence>
         *                   &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
            "residentialPremises",
            "livingRoom"
        })
        public static class PremisesAndRooms {

            @XmlElement(name = "ResidentialPremises")
            protected List<ShareDataType.RealEstateType.PremisesAndRooms.ResidentialPremises> residentialPremises;
            @XmlElement(name = "LivingRoom")
            protected List<ShareDataType.RealEstateType.PremisesAndRooms.LivingRoom> livingRoom;

            /**
             * Gets the value of the residentialPremises property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the residentialPremises property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getResidentialPremises().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ShareDataType.RealEstateType.PremisesAndRooms.ResidentialPremises }
             * 
             * 
             */
            public List<ShareDataType.RealEstateType.PremisesAndRooms.ResidentialPremises> getResidentialPremises() {
                if (residentialPremises == null) {
                    residentialPremises = new ArrayList<ShareDataType.RealEstateType.PremisesAndRooms.ResidentialPremises>();
                }
                return this.residentialPremises;
            }

            /**
             * Gets the value of the livingRoom property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the livingRoom property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getLivingRoom().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ShareDataType.RealEstateType.PremisesAndRooms.LivingRoom }
             * 
             * 
             */
            public List<ShareDataType.RealEstateType.PremisesAndRooms.LivingRoom> getLivingRoom() {
                if (livingRoom == null) {
                    livingRoom = new ArrayList<ShareDataType.RealEstateType.PremisesAndRooms.LivingRoom>();
                }
                return this.livingRoom;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
             *       &lt;sequence>
             *         &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
                "livingRoomGUID"
            })
            public static class LivingRoom
                extends PartSizeType
            {

                @XmlElement(name = "LivingRoomGUID", required = true)
                protected String livingRoomGUID;

                /**
                 * Gets the value of the livingRoomGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLivingRoomGUID() {
                    return livingRoomGUID;
                }

                /**
                 * Sets the value of the livingRoomGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLivingRoomGUID(String value) {
                    this.livingRoomGUID = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}PartSizeType">
             *       &lt;sequence>
             *         &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
                "residentialPremisesGUID"
            })
            public static class ResidentialPremises
                extends PartSizeType
            {

                @XmlElement(name = "ResidentialPremisesGUID", required = true)
                protected String residentialPremisesGUID;

                /**
                 * Gets the value of the residentialPremisesGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getResidentialPremisesGUID() {
                    return residentialPremisesGUID;
                }

                /**
                 * Sets the value of the residentialPremisesGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setResidentialPremisesGUID(String value) {
                    this.residentialPremisesGUID = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="TermDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "termDate"
    })
    public static class Terminated {

        @XmlElement(name = "TermDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar termDate;

        /**
         * Gets the value of the termDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTermDate() {
            return termDate;
        }

        /**
         * Sets the value of the termDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTermDate(XMLGregorianCalendar value) {
            this.termDate = value;
        }

    }

}

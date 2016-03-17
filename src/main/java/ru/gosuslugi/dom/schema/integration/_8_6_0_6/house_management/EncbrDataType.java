
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_6_0.RegOrgType;


/**
 * <p>Java class for EncbrDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncbrDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="LivingHouse">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="NonResidentialPremises" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
 *                   &lt;sequence>
 *                     &lt;element name="NonResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *                   &lt;/sequence>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="PremisesAndRooms">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
 *                             &lt;sequence>
 *                               &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
 *                             &lt;sequence>
 *                               &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EncbrKind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *         &lt;element name="EncbrSubject">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrgType"/>
 *                   &lt;element name="Ind" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}IndType">
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
@XmlType(name = "EncbrDataType", propOrder = {
    "livingHouse",
    "nonResidentialPremises",
    "premisesAndRooms",
    "endDate",
    "encbrKind",
    "encbrSubject"
})
@XmlSeeAlso({
    EncbrDataToCreate.class
})
public class EncbrDataType {

    @XmlElement(name = "LivingHouse")
    protected EncbrDataType.LivingHouse livingHouse;
    @XmlElement(name = "NonResidentialPremises")
    protected List<EncbrDataType.NonResidentialPremises> nonResidentialPremises;
    @XmlElement(name = "PremisesAndRooms")
    protected EncbrDataType.PremisesAndRooms premisesAndRooms;
    @XmlElement(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "EncbrKind", required = true)
    protected NsiRef encbrKind;
    @XmlElement(name = "EncbrSubject", required = true)
    protected EncbrDataType.EncbrSubject encbrSubject;

    /**
     * Gets the value of the livingHouse property.
     * 
     * @return
     *     possible object is
     *     {@link EncbrDataType.LivingHouse }
     *     
     */
    public EncbrDataType.LivingHouse getLivingHouse() {
        return livingHouse;
    }

    /**
     * Sets the value of the livingHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncbrDataType.LivingHouse }
     *     
     */
    public void setLivingHouse(EncbrDataType.LivingHouse value) {
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
     * {@link EncbrDataType.NonResidentialPremises }
     * 
     * 
     */
    public List<EncbrDataType.NonResidentialPremises> getNonResidentialPremises() {
        if (nonResidentialPremises == null) {
            nonResidentialPremises = new ArrayList<EncbrDataType.NonResidentialPremises>();
        }
        return this.nonResidentialPremises;
    }

    /**
     * Gets the value of the premisesAndRooms property.
     * 
     * @return
     *     possible object is
     *     {@link EncbrDataType.PremisesAndRooms }
     *     
     */
    public EncbrDataType.PremisesAndRooms getPremisesAndRooms() {
        return premisesAndRooms;
    }

    /**
     * Sets the value of the premisesAndRooms property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncbrDataType.PremisesAndRooms }
     *     
     */
    public void setPremisesAndRooms(EncbrDataType.PremisesAndRooms value) {
        this.premisesAndRooms = value;
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
     * Gets the value of the encbrKind property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getEncbrKind() {
        return encbrKind;
    }

    /**
     * Sets the value of the encbrKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setEncbrKind(NsiRef value) {
        this.encbrKind = value;
    }

    /**
     * Gets the value of the encbrSubject property.
     * 
     * @return
     *     possible object is
     *     {@link EncbrDataType.EncbrSubject }
     *     
     */
    public EncbrDataType.EncbrSubject getEncbrSubject() {
        return encbrSubject;
    }

    /**
     * Sets the value of the encbrSubject property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncbrDataType.EncbrSubject }
     *     
     */
    public void setEncbrSubject(EncbrDataType.EncbrSubject value) {
        this.encbrSubject = value;
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
     *         &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrgType"/>
     *         &lt;element name="Ind" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}IndType">
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
    public static class EncbrSubject {

        @XmlElement(name = "RegOrg")
        protected RegOrgType regOrg;
        @XmlElement(name = "Ind")
        protected List<EncbrDataType.EncbrSubject.Ind> ind;

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
         * {@link EncbrDataType.EncbrSubject.Ind }
         * 
         * 
         */
        public List<EncbrDataType.EncbrSubject.Ind> getInd() {
            if (ind == null) {
                ind = new ArrayList<EncbrDataType.EncbrSubject.Ind>();
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}IndType">
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class LivingHouse
        extends PartSizeType
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
     *       &lt;sequence>
     *         &lt;element name="NonResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
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
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
     *                 &lt;sequence>
     *                   &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
     *                 &lt;sequence>
     *                   &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
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
        protected List<EncbrDataType.PremisesAndRooms.ResidentialPremises> residentialPremises;
        @XmlElement(name = "LivingRoom")
        protected List<EncbrDataType.PremisesAndRooms.LivingRoom> livingRoom;

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
         * {@link EncbrDataType.PremisesAndRooms.ResidentialPremises }
         * 
         * 
         */
        public List<EncbrDataType.PremisesAndRooms.ResidentialPremises> getResidentialPremises() {
            if (residentialPremises == null) {
                residentialPremises = new ArrayList<EncbrDataType.PremisesAndRooms.ResidentialPremises>();
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
         * {@link EncbrDataType.PremisesAndRooms.LivingRoom }
         * 
         * 
         */
        public List<EncbrDataType.PremisesAndRooms.LivingRoom> getLivingRoom() {
            if (livingRoom == null) {
                livingRoom = new ArrayList<EncbrDataType.PremisesAndRooms.LivingRoom>();
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
         *       &lt;sequence>
         *         &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PartSizeType">
         *       &lt;sequence>
         *         &lt;element name="ResidentialPremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
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

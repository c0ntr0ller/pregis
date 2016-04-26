
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;


/**
 * <p>Java class for exportHouseResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportHouseResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HouseUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;choice>
 *           &lt;element name="ApartmentHouse">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ApartmentHouseExportType">
 *                   &lt;sequence>
 *                     &lt;element name="Entrance" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}EntranceExportType">
 *                             &lt;sequence>
 *                               &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                               &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ResidentialPremisesExportType">
 *                                       &lt;sequence>
 *                                         &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                         &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
 *                                           &lt;complexType>
 *                                             &lt;complexContent>
 *                                               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
 *                                                 &lt;sequence>
 *                                                   &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                                   &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                                                 &lt;/sequence>
 *                                               &lt;/extension>
 *                                             &lt;/complexContent>
 *                                           &lt;/complexType>
 *                                         &lt;/element>
 *                                         &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="EntranceGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="NonResidentialPremises" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}NonResidentialPremisesExportType">
 *                             &lt;sequence>
 *                               &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                               &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                               &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="HouseManagementType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="LivingHouse">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}LivingHouseExportType">
 *                   &lt;sequence>
 *                     &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
 *                             &lt;sequence>
 *                               &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                               &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                               &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="HouseGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                   &lt;/sequence>
 *                 &lt;/extension>
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
@XmlType(name = "exportHouseResultType", propOrder = {
    "houseUniqueNumber",
    "modificationDate",
    "apartmentHouse",
    "livingHouse"
})
public class ExportHouseResultType {

    @XmlElement(name = "HouseUniqueNumber", required = true)
    protected String houseUniqueNumber;
    @XmlElement(name = "ModificationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modificationDate;
    @XmlElement(name = "ApartmentHouse")
    protected ExportHouseResultType.ApartmentHouse apartmentHouse;
    @XmlElement(name = "LivingHouse")
    protected ExportHouseResultType.LivingHouse livingHouse;

    /**
     * Gets the value of the houseUniqueNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouseUniqueNumber() {
        return houseUniqueNumber;
    }

    /**
     * Sets the value of the houseUniqueNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouseUniqueNumber(String value) {
        this.houseUniqueNumber = value;
    }

    /**
     * Gets the value of the modificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the value of the modificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModificationDate(XMLGregorianCalendar value) {
        this.modificationDate = value;
    }

    /**
     * Gets the value of the apartmentHouse property.
     * 
     * @return
     *     possible object is
     *     {@link ExportHouseResultType.ApartmentHouse }
     *     
     */
    public ExportHouseResultType.ApartmentHouse getApartmentHouse() {
        return apartmentHouse;
    }

    /**
     * Sets the value of the apartmentHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportHouseResultType.ApartmentHouse }
     *     
     */
    public void setApartmentHouse(ExportHouseResultType.ApartmentHouse value) {
        this.apartmentHouse = value;
    }

    /**
     * Gets the value of the livingHouse property.
     * 
     * @return
     *     possible object is
     *     {@link ExportHouseResultType.LivingHouse }
     *     
     */
    public ExportHouseResultType.LivingHouse getLivingHouse() {
        return livingHouse;
    }

    /**
     * Sets the value of the livingHouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportHouseResultType.LivingHouse }
     *     
     */
    public void setLivingHouse(ExportHouseResultType.LivingHouse value) {
        this.livingHouse = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ApartmentHouseExportType">
     *       &lt;sequence>
     *         &lt;element name="Entrance" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}EntranceExportType">
     *                 &lt;sequence>
     *                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ResidentialPremisesExportType">
     *                           &lt;sequence>
     *                             &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                             &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
     *                                     &lt;sequence>
     *                                       &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                                       &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="EntranceGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="NonResidentialPremises" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}NonResidentialPremisesExportType">
     *                 &lt;sequence>
     *                   &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HouseManagementType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef" minOccurs="0"/>
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
        "entrance",
        "nonResidentialPremises",
        "houseManagementType"
    })
    public static class ApartmentHouse
        extends ApartmentHouseExportType
    {

        @XmlElement(name = "Entrance")
        protected List<ExportHouseResultType.ApartmentHouse.Entrance> entrance;
        @XmlElement(name = "NonResidentialPremises")
        protected List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> nonResidentialPremises;
        @XmlElement(name = "HouseManagementType")
        protected NsiRef houseManagementType;

        /**
         * Gets the value of the entrance property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entrance property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntrance().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExportHouseResultType.ApartmentHouse.Entrance }
         * 
         * 
         */
        public List<ExportHouseResultType.ApartmentHouse.Entrance> getEntrance() {
            if (entrance == null) {
                entrance = new ArrayList<ExportHouseResultType.ApartmentHouse.Entrance>();
            }
            return this.entrance;
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
         * {@link ExportHouseResultType.ApartmentHouse.NonResidentialPremises }
         * 
         * 
         */
        public List<ExportHouseResultType.ApartmentHouse.NonResidentialPremises> getNonResidentialPremises() {
            if (nonResidentialPremises == null) {
                nonResidentialPremises = new ArrayList<ExportHouseResultType.ApartmentHouse.NonResidentialPremises>();
            }
            return this.nonResidentialPremises;
        }

        /**
         * Gets the value of the houseManagementType property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getHouseManagementType() {
            return houseManagementType;
        }

        /**
         * Sets the value of the houseManagementType property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setHouseManagementType(NsiRef value) {
            this.houseManagementType = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}EntranceExportType">
         *       &lt;sequence>
         *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="ResidentialPremises" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ResidentialPremisesExportType">
         *                 &lt;sequence>
         *                   &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                   &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
         *                           &lt;sequence>
         *                             &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *                             &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
         *                           &lt;/sequence>
         *                         &lt;/extension>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="EntranceGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
            "modificationDate",
            "residentialPremises",
            "entranceGUID"
        })
        public static class Entrance
            extends EntranceExportType
        {

            @XmlElement(name = "ModificationDate", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar modificationDate;
            @XmlElement(name = "ResidentialPremises")
            protected List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises> residentialPremises;
            @XmlElement(name = "EntranceGUID", required = true)
            protected String entranceGUID;

            /**
             * Gets the value of the modificationDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getModificationDate() {
                return modificationDate;
            }

            /**
             * Sets the value of the modificationDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setModificationDate(XMLGregorianCalendar value) {
                this.modificationDate = value;
            }

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
             * {@link ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises }
             * 
             * 
             */
            public List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises> getResidentialPremises() {
                if (residentialPremises == null) {
                    residentialPremises = new ArrayList<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises>();
                }
                return this.residentialPremises;
            }

            /**
             * Gets the value of the entranceGUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEntranceGUID() {
                return entranceGUID;
            }

            /**
             * Sets the value of the entranceGUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEntranceGUID(String value) {
                this.entranceGUID = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ResidentialPremisesExportType">
             *       &lt;sequence>
             *         &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *         &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
             *                 &lt;sequence>
             *                   &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
             *                   &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
             *                 &lt;/sequence>
             *               &lt;/extension>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
                "premisesUniqueNumber",
                "modificationDate",
                "livingRoom",
                "premisesGUID"
            })
            public static class ResidentialPremises
                extends ResidentialPremisesExportType
            {

                @XmlElement(name = "PremisesUniqueNumber", required = true)
                protected String premisesUniqueNumber;
                @XmlElement(name = "ModificationDate", required = true)
                @XmlSchemaType(name = "dateTime")
                protected XMLGregorianCalendar modificationDate;
                @XmlElement(name = "LivingRoom")
                protected List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom> livingRoom;
                @XmlElement(name = "PremisesGUID", required = true)
                protected String premisesGUID;

                /**
                 * Gets the value of the premisesUniqueNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPremisesUniqueNumber() {
                    return premisesUniqueNumber;
                }

                /**
                 * Sets the value of the premisesUniqueNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPremisesUniqueNumber(String value) {
                    this.premisesUniqueNumber = value;
                }

                /**
                 * Gets the value of the modificationDate property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public XMLGregorianCalendar getModificationDate() {
                    return modificationDate;
                }

                /**
                 * Sets the value of the modificationDate property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link XMLGregorianCalendar }
                 *     
                 */
                public void setModificationDate(XMLGregorianCalendar value) {
                    this.modificationDate = value;
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
                 * {@link ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom }
                 * 
                 * 
                 */
                public List<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom> getLivingRoom() {
                    if (livingRoom == null) {
                        livingRoom = new ArrayList<ExportHouseResultType.ApartmentHouse.Entrance.ResidentialPremises.LivingRoom>();
                    }
                    return this.livingRoom;
                }

                /**
                 * Gets the value of the premisesGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPremisesGUID() {
                    return premisesGUID;
                }

                /**
                 * Sets the value of the premisesGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPremisesGUID(String value) {
                    this.premisesGUID = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
                 *       &lt;sequence>
                 *         &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
                 *         &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
                    "livingRoomUniqueNumber",
                    "modificationDate",
                    "livingRoomGUID"
                })
                public static class LivingRoom
                    extends RoomExportType
                {

                    @XmlElement(name = "LivingRoomUniqueNumber", required = true)
                    protected String livingRoomUniqueNumber;
                    @XmlElement(name = "ModificationDate", required = true)
                    @XmlSchemaType(name = "dateTime")
                    protected XMLGregorianCalendar modificationDate;
                    @XmlElement(name = "LivingRoomGUID", required = true)
                    protected String livingRoomGUID;

                    /**
                     * Gets the value of the livingRoomUniqueNumber property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getLivingRoomUniqueNumber() {
                        return livingRoomUniqueNumber;
                    }

                    /**
                     * Sets the value of the livingRoomUniqueNumber property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setLivingRoomUniqueNumber(String value) {
                        this.livingRoomUniqueNumber = value;
                    }

                    /**
                     * Gets the value of the modificationDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getModificationDate() {
                        return modificationDate;
                    }

                    /**
                     * Sets the value of the modificationDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setModificationDate(XMLGregorianCalendar value) {
                        this.modificationDate = value;
                    }

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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}NonResidentialPremisesExportType">
         *       &lt;sequence>
         *         &lt;element name="PremisesUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="PremisesGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
            "premisesUniqueNumber",
            "modificationDate",
            "premisesGUID"
        })
        public static class NonResidentialPremises
            extends NonResidentialPremisesExportType
        {

            @XmlElement(name = "PremisesUniqueNumber", required = true)
            protected String premisesUniqueNumber;
            @XmlElement(name = "ModificationDate", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar modificationDate;
            @XmlElement(name = "PremisesGUID", required = true)
            protected String premisesGUID;

            /**
             * Gets the value of the premisesUniqueNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPremisesUniqueNumber() {
                return premisesUniqueNumber;
            }

            /**
             * Sets the value of the premisesUniqueNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPremisesUniqueNumber(String value) {
                this.premisesUniqueNumber = value;
            }

            /**
             * Gets the value of the modificationDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getModificationDate() {
                return modificationDate;
            }

            /**
             * Sets the value of the modificationDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setModificationDate(XMLGregorianCalendar value) {
                this.modificationDate = value;
            }

            /**
             * Gets the value of the premisesGUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPremisesGUID() {
                return premisesGUID;
            }

            /**
             * Sets the value of the premisesGUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPremisesGUID(String value) {
                this.premisesGUID = value;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}LivingHouseExportType">
     *       &lt;sequence>
     *         &lt;element name="LivingRoom" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
     *                 &lt;sequence>
     *                   &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                   &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HouseGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
        "livingRoom",
        "houseGUID"
    })
    public static class LivingHouse
        extends LivingHouseExportType
    {

        @XmlElement(name = "LivingRoom")
        protected List<ExportHouseResultType.LivingHouse.LivingRoom> livingRoom;
        @XmlElement(name = "HouseGUID", required = true)
        protected String houseGUID;

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
         * {@link ExportHouseResultType.LivingHouse.LivingRoom }
         * 
         * 
         */
        public List<ExportHouseResultType.LivingHouse.LivingRoom> getLivingRoom() {
            if (livingRoom == null) {
                livingRoom = new ArrayList<ExportHouseResultType.LivingHouse.LivingRoom>();
            }
            return this.livingRoom;
        }

        /**
         * Gets the value of the houseGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHouseGUID() {
            return houseGUID;
        }

        /**
         * Sets the value of the houseGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHouseGUID(String value) {
            this.houseGUID = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomExportType">
         *       &lt;sequence>
         *         &lt;element name="LivingRoomUniqueNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *         &lt;element name="LivingRoomGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
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
            "livingRoomUniqueNumber",
            "modificationDate",
            "livingRoomGUID"
        })
        public static class LivingRoom
            extends RoomExportType
        {

            @XmlElement(name = "LivingRoomUniqueNumber", required = true)
            protected String livingRoomUniqueNumber;
            @XmlElement(name = "ModificationDate", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar modificationDate;
            @XmlElement(name = "LivingRoomGUID", required = true)
            protected String livingRoomGUID;

            /**
             * Gets the value of the livingRoomUniqueNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLivingRoomUniqueNumber() {
                return livingRoomUniqueNumber;
            }

            /**
             * Sets the value of the livingRoomUniqueNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLivingRoomUniqueNumber(String value) {
                this.livingRoomUniqueNumber = value;
            }

            /**
             * Gets the value of the modificationDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getModificationDate() {
                return modificationDate;
            }

            /**
             * Sets the value of the modificationDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setModificationDate(XMLGregorianCalendar value) {
                this.modificationDate = value;
            }

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

    }

}

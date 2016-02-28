
package ru.gosuslugi.dom.schema.integration._8_6_0_4.disclosure;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}TransportGUID"/>
 *         &lt;element name="LastEditingDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;choice>
 *           &lt;element name="House" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}HouseGuid"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_1" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_2" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_3" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_4" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_5" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_6"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="NoHouses" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="ReportPeriodBeginDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="ReportPeriodEndDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
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
    "lastEditingDate",
    "house",
    "noHouses"
})
@XmlRootElement(name = "Section2")
public class Section2 {

    @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
    protected String transportGUID;
    @XmlElement(name = "LastEditingDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastEditingDate;
    @XmlElement(name = "House")
    protected List<Section2 .House> house;
    @XmlElement(name = "NoHouses")
    protected Boolean noHouses;
    @XmlAttribute(name = "ReportPeriodBeginDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reportPeriodBeginDate;
    @XmlAttribute(name = "ReportPeriodEndDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reportPeriodEndDate;

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
     * Gets the value of the lastEditingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastEditingDate() {
        return lastEditingDate;
    }

    /**
     * Sets the value of the lastEditingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastEditingDate(XMLGregorianCalendar value) {
        this.lastEditingDate = value;
    }

    /**
     * Gets the value of the house property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the house property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHouse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section2 .House }
     * 
     * 
     */
    public List<Section2 .House> getHouse() {
        if (house == null) {
            house = new ArrayList<Section2 .House>();
        }
        return this.house;
    }

    /**
     * Gets the value of the noHouses property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoHouses() {
        return noHouses;
    }

    /**
     * Sets the value of the noHouses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoHouses(Boolean value) {
        this.noHouses = value;
    }

    /**
     * Gets the value of the reportPeriodBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportPeriodBeginDate() {
        return reportPeriodBeginDate;
    }

    /**
     * Sets the value of the reportPeriodBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportPeriodBeginDate(XMLGregorianCalendar value) {
        this.reportPeriodBeginDate = value;
    }

    /**
     * Gets the value of the reportPeriodEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportPeriodEndDate() {
        return reportPeriodEndDate;
    }

    /**
     * Sets the value of the reportPeriodEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportPeriodEndDate(XMLGregorianCalendar value) {
        this.reportPeriodEndDate = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}HouseGuid"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_1" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_2" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_3" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_4" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_5" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}Section2_6"/>
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
        "houseGuid",
        "section21",
        "section22",
        "section23",
        "section24",
        "section25",
        "section26"
    })
    public static class House {

        @XmlElement(name = "HouseGuid", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
        protected String houseGuid;
        @XmlElement(name = "Section2_1")
        protected Section21 section21;
        @XmlElement(name = "Section2_2")
        protected Section22 section22;
        @XmlElement(name = "Section2_3")
        protected Section23 section23;
        @XmlElement(name = "Section2_4")
        protected Section24 section24;
        @XmlElement(name = "Section2_5")
        protected Section25 section25;
        @XmlElement(name = "Section2_6", required = true)
        protected Section26 section26;

        /**
         * Gets the value of the houseGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHouseGuid() {
            return houseGuid;
        }

        /**
         * Sets the value of the houseGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHouseGuid(String value) {
            this.houseGuid = value;
        }

        /**
         * Gets the value of the section21 property.
         * 
         * @return
         *     possible object is
         *     {@link Section21 }
         *     
         */
        public Section21 getSection21() {
            return section21;
        }

        /**
         * Sets the value of the section21 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section21 }
         *     
         */
        public void setSection21(Section21 value) {
            this.section21 = value;
        }

        /**
         * Gets the value of the section22 property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 }
         *     
         */
        public Section22 getSection22() {
            return section22;
        }

        /**
         * Sets the value of the section22 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 }
         *     
         */
        public void setSection22(Section22 value) {
            this.section22 = value;
        }

        /**
         * Gets the value of the section23 property.
         * 
         * @return
         *     possible object is
         *     {@link Section23 }
         *     
         */
        public Section23 getSection23() {
            return section23;
        }

        /**
         * Sets the value of the section23 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section23 }
         *     
         */
        public void setSection23(Section23 value) {
            this.section23 = value;
        }

        /**
         * Gets the value of the section24 property.
         * 
         * @return
         *     possible object is
         *     {@link Section24 }
         *     
         */
        public Section24 getSection24() {
            return section24;
        }

        /**
         * Sets the value of the section24 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section24 }
         *     
         */
        public void setSection24(Section24 value) {
            this.section24 = value;
        }

        /**
         * Gets the value of the section25 property.
         * 
         * @return
         *     possible object is
         *     {@link Section25 }
         *     
         */
        public Section25 getSection25() {
            return section25;
        }

        /**
         * Sets the value of the section25 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section25 }
         *     
         */
        public void setSection25(Section25 value) {
            this.section25 = value;
        }

        /**
         * Gets the value of the section26 property.
         * 
         * @return
         *     possible object is
         *     {@link Section26 }
         *     
         */
        public Section26 getSection26() {
            return section26;
        }

        /**
         * Sets the value of the section26 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section26 }
         *     
         */
        public void setSection26(Section26 value) {
            this.section26 = value;
        }

    }

}


package ru.gosuslugi.dom.schema.integration.services.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}BaseType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="MonthYearFrom">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Month"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Year"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="MonthYearTo">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Month"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Year"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/services/}WorkListStatus" minOccurs="0"/>
 *           &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
 *         &lt;/sequence>
 *         &lt;element name="WorkListGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" maxOccurs="100"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "monthYearFrom",
    "monthYearTo",
    "workListStatus",
    "fiasHouseGuid",
    "workListGUID"
})
@XmlRootElement(name = "exportWorkingListRequest")
public class ExportWorkingListRequest
    extends BaseType
{

    @XmlElement(name = "MonthYearFrom")
    protected ExportWorkingListRequest.MonthYearFrom monthYearFrom;
    @XmlElement(name = "MonthYearTo")
    protected ExportWorkingListRequest.MonthYearTo monthYearTo;
    @XmlElement(name = "WorkListStatus")
    protected String workListStatus;
    @XmlElement(name = "FIASHouseGuid")
    protected String fiasHouseGuid;
    @XmlElement(name = "WorkListGUID")
    protected List<String> workListGUID;

    /**
     * Gets the value of the monthYearFrom property.
     * 
     * @return
     *     possible object is
     *     {@link ExportWorkingListRequest.MonthYearFrom }
     *     
     */
    public ExportWorkingListRequest.MonthYearFrom getMonthYearFrom() {
        return monthYearFrom;
    }

    /**
     * Sets the value of the monthYearFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportWorkingListRequest.MonthYearFrom }
     *     
     */
    public void setMonthYearFrom(ExportWorkingListRequest.MonthYearFrom value) {
        this.monthYearFrom = value;
    }

    /**
     * Gets the value of the monthYearTo property.
     * 
     * @return
     *     possible object is
     *     {@link ExportWorkingListRequest.MonthYearTo }
     *     
     */
    public ExportWorkingListRequest.MonthYearTo getMonthYearTo() {
        return monthYearTo;
    }

    /**
     * Sets the value of the monthYearTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportWorkingListRequest.MonthYearTo }
     *     
     */
    public void setMonthYearTo(ExportWorkingListRequest.MonthYearTo value) {
        this.monthYearTo = value;
    }

    /**
     * Gets the value of the workListStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkListStatus() {
        return workListStatus;
    }

    /**
     * Sets the value of the workListStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkListStatus(String value) {
        this.workListStatus = value;
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
     * Gets the value of the workListGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workListGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkListGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWorkListGUID() {
        if (workListGUID == null) {
            workListGUID = new ArrayList<String>();
        }
        return this.workListGUID;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Month"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Year"/>
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
        "month",
        "year"
    })
    public static class MonthYearFrom {

        @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
        protected int month;
        @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
        protected short year;

        /**
         * Gets the value of the month property.
         * 
         */
        public int getMonth() {
            return month;
        }

        /**
         * Sets the value of the month property.
         * 
         */
        public void setMonth(int value) {
            this.month = value;
        }

        /**
         * Gets the value of the year property.
         * 
         */
        public short getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         */
        public void setYear(short value) {
            this.year = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Month"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}Year"/>
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
        "month",
        "year"
    })
    public static class MonthYearTo {

        @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
        protected int month;
        @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
        protected short year;

        /**
         * Gets the value of the month property.
         * 
         */
        public int getMonth() {
            return month;
        }

        /**
         * Sets the value of the month property.
         * 
         */
        public void setMonth(int value) {
            this.month = value;
        }

        /**
         * Gets the value of the year property.
         * 
         */
        public short getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         */
        public void setYear(short value) {
            this.year = value;
        }

    }

}
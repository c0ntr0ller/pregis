
package ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for exportShareEncbrDataResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportShareEncbrDataResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ShareGUID"/>
 *         &lt;element name="ShareData" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ShareDataType"/>
 *         &lt;element name="Ecnbr" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrGUID"/>
 *                   &lt;element name="EncbrData" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrDataType"/>
 *                   &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrNumber"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ShareNumber"/>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportShareEncbrDataResultType", propOrder = {
    "shareGUID",
    "shareData",
    "ecnbr",
    "updateDate",
    "shareNumber",
    "fiasHouseGuid"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management.ExportShareEncbrDataResult.ShareEncbr.class
})
public class ExportShareEncbrDataResultType {

    @XmlElement(name = "ShareGUID", required = true)
    protected String shareGUID;
    @XmlElement(name = "ShareData", required = true)
    protected ShareDataType shareData;
    @XmlElement(name = "Ecnbr")
    protected List<ExportShareEncbrDataResultType.Ecnbr> ecnbr;
    @XmlElement(name = "UpdateDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDate;
    @XmlElement(name = "ShareNumber", required = true)
    protected BigInteger shareNumber;
    @XmlElement(name = "FIASHouseGuid")
    protected String fiasHouseGuid;

    /**
     * Идентификатор доли
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShareGUID() {
        return shareGUID;
    }

    /**
     * Sets the value of the shareGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShareGUID(String value) {
        this.shareGUID = value;
    }

    /**
     * Gets the value of the shareData property.
     * 
     * @return
     *     possible object is
     *     {@link ShareDataType }
     *     
     */
    public ShareDataType getShareData() {
        return shareData;
    }

    /**
     * Sets the value of the shareData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShareDataType }
     *     
     */
    public void setShareData(ShareDataType value) {
        this.shareData = value;
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
     * {@link ExportShareEncbrDataResultType.Ecnbr }
     * 
     * 
     */
    public List<ExportShareEncbrDataResultType.Ecnbr> getEcnbr() {
        if (ecnbr == null) {
            ecnbr = new ArrayList<ExportShareEncbrDataResultType.Ecnbr>();
        }
        return this.ecnbr;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDate(XMLGregorianCalendar value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the shareNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getShareNumber() {
        return shareNumber;
    }

    /**
     * Sets the value of the shareNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setShareNumber(BigInteger value) {
        this.shareNumber = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrGUID"/>
     *         &lt;element name="EncbrData" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrDataType"/>
     *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrNumber"/>
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
        "encbrGUID",
        "encbrData",
        "updateDate",
        "encbrNumber"
    })
    public static class Ecnbr {

        @XmlElement(name = "EncbrGUID", required = true)
        protected String encbrGUID;
        @XmlElement(name = "EncbrData", required = true)
        protected EncbrDataType encbrData;
        @XmlElement(name = "UpdateDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar updateDate;
        @XmlElement(name = "EncbrNumber", required = true)
        protected BigInteger encbrNumber;

        /**
         * Идентификатор обременения (для обновления или удаления)
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEncbrGUID() {
            return encbrGUID;
        }

        /**
         * Sets the value of the encbrGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEncbrGUID(String value) {
            this.encbrGUID = value;
        }

        /**
         * Gets the value of the encbrData property.
         * 
         * @return
         *     possible object is
         *     {@link EncbrDataType }
         *     
         */
        public EncbrDataType getEncbrData() {
            return encbrData;
        }

        /**
         * Sets the value of the encbrData property.
         * 
         * @param value
         *     allowed object is
         *     {@link EncbrDataType }
         *     
         */
        public void setEncbrData(EncbrDataType value) {
            this.encbrData = value;
        }

        /**
         * Gets the value of the updateDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getUpdateDate() {
            return updateDate;
        }

        /**
         * Sets the value of the updateDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setUpdateDate(XMLGregorianCalendar value) {
            this.updateDate = value;
        }

        /**
         * Gets the value of the encbrNumber property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getEncbrNumber() {
            return encbrNumber;
        }

        /**
         * Sets the value of the encbrNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setEncbrNumber(BigInteger value) {
            this.encbrNumber = value;
        }

    }

}

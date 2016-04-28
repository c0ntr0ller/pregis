
package ru.gosuslugi.dom.schema.integration._8_7_0_6.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="HousePeriod" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}FIASHouseGUIDType"/>
 *                   &lt;choice>
 *                     &lt;element name="isUO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                     &lt;element name="isRSO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;/choice>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
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
    "housePeriod"
})
@XmlRootElement(name = "closeHousePaymentPeriodRequest")
public class CloseHousePaymentPeriodRequest
    extends BaseType
{

    @XmlElement(name = "HousePeriod", required = true)
    protected List<CloseHousePaymentPeriodRequest.HousePeriod> housePeriod;

    /**
     * Gets the value of the housePeriod property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the housePeriod property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHousePeriod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CloseHousePaymentPeriodRequest.HousePeriod }
     * 
     * 
     */
    public List<CloseHousePaymentPeriodRequest.HousePeriod> getHousePeriod() {
        if (housePeriod == null) {
            housePeriod = new ArrayList<CloseHousePaymentPeriodRequest.HousePeriod>();
        }
        return this.housePeriod;
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
     *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}FIASHouseGUIDType"/>
     *         &lt;choice>
     *           &lt;element name="isUO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *           &lt;element name="isRSO" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;/choice>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
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
        "fiasHouseGuid",
        "isUO",
        "isRSO",
        "transportGUID"
    })
    public static class HousePeriod {

        @XmlElement(name = "FIASHouseGuid", required = true)
        protected String fiasHouseGuid;
        protected Boolean isUO;
        protected Boolean isRSO;
        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
        protected String transportGUID;

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
         * Gets the value of the isUO property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsUO() {
            return isUO;
        }

        /**
         * Sets the value of the isUO property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsUO(Boolean value) {
            this.isUO = value;
        }

        /**
         * Gets the value of the isRSO property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsRSO() {
            return isRSO;
        }

        /**
         * Sets the value of the isRSO property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsRSO(Boolean value) {
            this.isRSO = value;
        }

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

    }

}

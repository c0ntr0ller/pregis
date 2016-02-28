
package ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="Criteria" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}CharterGUID"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ContractGUID"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ContractVersionGUID"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}CharterVersionGUID"/>
 *                   &lt;sequence>
 *                     &lt;element name="UOGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
 *                     &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
 *                     &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;element name="LastVersionOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/choice>
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
    "criteria"
})
@XmlRootElement(name = "exportCAChRequest")
public class ExportCAChRequest
    extends BaseType
{

    @XmlElement(name = "Criteria", required = true)
    protected List<ExportCAChRequest.Criteria> criteria;

    /**
     * Gets the value of the criteria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the criteria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCriteria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportCAChRequest.Criteria }
     * 
     * 
     */
    public List<ExportCAChRequest.Criteria> getCriteria() {
        if (criteria == null) {
            criteria = new ArrayList<ExportCAChRequest.Criteria>();
        }
        return this.criteria;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}CharterGUID"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ContractGUID"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ContractVersionGUID"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}CharterVersionGUID"/>
     *         &lt;sequence>
     *           &lt;element name="UOGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
     *           &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
     *           &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *           &lt;element name="LastVersionOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;/sequence>
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
        "charterGUID",
        "contractGUID",
        "contractVersionGUID",
        "charterVersionGUID",
        "uoguid",
        "fiasHouseGuid",
        "signingDate",
        "lastVersionOnly"
    })
    public static class Criteria {

        @XmlElement(name = "CharterGUID")
        protected String charterGUID;
        @XmlElement(name = "ContractGUID")
        protected String contractGUID;
        @XmlElement(name = "ContractVersionGUID")
        protected String contractVersionGUID;
        @XmlElement(name = "CharterVersionGUID")
        protected String charterVersionGUID;
        @XmlElement(name = "UOGUID")
        protected String uoguid;
        @XmlElement(name = "FIASHouseGuid")
        protected String fiasHouseGuid;
        @XmlElement(name = "SigningDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar signingDate;
        @XmlElement(name = "LastVersionOnly")
        protected Boolean lastVersionOnly;

        /**
         * Gets the value of the charterGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCharterGUID() {
            return charterGUID;
        }

        /**
         * Sets the value of the charterGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCharterGUID(String value) {
            this.charterGUID = value;
        }

        /**
         * Gets the value of the contractGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractGUID() {
            return contractGUID;
        }

        /**
         * Sets the value of the contractGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractGUID(String value) {
            this.contractGUID = value;
        }

        /**
         * Gets the value of the contractVersionGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractVersionGUID() {
            return contractVersionGUID;
        }

        /**
         * Sets the value of the contractVersionGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractVersionGUID(String value) {
            this.contractVersionGUID = value;
        }

        /**
         * Gets the value of the charterVersionGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCharterVersionGUID() {
            return charterVersionGUID;
        }

        /**
         * Sets the value of the charterVersionGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCharterVersionGUID(String value) {
            this.charterVersionGUID = value;
        }

        /**
         * Gets the value of the uoguid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUOGUID() {
            return uoguid;
        }

        /**
         * Sets the value of the uoguid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUOGUID(String value) {
            this.uoguid = value;
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
         * Gets the value of the signingDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSigningDate() {
            return signingDate;
        }

        /**
         * Sets the value of the signingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSigningDate(XMLGregorianCalendar value) {
            this.signingDate = value;
        }

        /**
         * Gets the value of the lastVersionOnly property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isLastVersionOnly() {
            return lastVersionOnly;
        }

        /**
         * Sets the value of the lastVersionOnly property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setLastVersionOnly(Boolean value) {
            this.lastVersionOnly = value;
        }

    }

}

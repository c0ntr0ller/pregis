
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Экспорт ЛС
 * 
 * <p>Java class for exportAccountResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportAccountResultType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}AccountType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountNumber"/>
 *         &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountUniqueNumber"/>
 *         &lt;element name="Obligations" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ContractVersionGUID"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}CharterVersionGUID"/>
 *                   &lt;/choice>
 *                   &lt;element name="IsLocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
@XmlType(name = "exportAccountResultType", propOrder = {
    "accountNumber",
    "accountGUID",
    "accountUniqueNumber",
    "obligations"
})
public class ExportAccountResultType
    extends AccountType
{

    @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected String accountNumber;
    @XmlElement(name = "AccountGUID", required = true)
    protected String accountGUID;
    @XmlElement(name = "AccountUniqueNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected String accountUniqueNumber;
    @XmlElement(name = "Obligations")
    protected List<ExportAccountResultType.Obligations> obligations;

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the accountGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountGUID() {
        return accountGUID;
    }

    /**
     * Sets the value of the accountGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountGUID(String value) {
        this.accountGUID = value;
    }

    /**
     * Gets the value of the accountUniqueNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountUniqueNumber() {
        return accountUniqueNumber;
    }

    /**
     * Sets the value of the accountUniqueNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountUniqueNumber(String value) {
        this.accountUniqueNumber = value;
    }

    /**
     * Gets the value of the obligations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the obligations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObligations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportAccountResultType.Obligations }
     * 
     * 
     */
    public List<ExportAccountResultType.Obligations> getObligations() {
        if (obligations == null) {
            obligations = new ArrayList<ExportAccountResultType.Obligations>();
        }
        return this.obligations;
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
     *         &lt;choice>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ContractVersionGUID"/>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}CharterVersionGUID"/>
     *         &lt;/choice>
     *         &lt;element name="IsLocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "contractVersionGUID",
        "charterVersionGUID",
        "isLocked"
    })
    public static class Obligations {

        @XmlElement(name = "ContractVersionGUID")
        protected String contractVersionGUID;
        @XmlElement(name = "CharterVersionGUID")
        protected String charterVersionGUID;
        @XmlElement(name = "IsLocked")
        protected Boolean isLocked;

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
         * Gets the value of the isLocked property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsLocked() {
            return isLocked;
        }

        /**
         * Sets the value of the isLocked property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsLocked(Boolean value) {
            this.isLocked = value;
        }

    }

}

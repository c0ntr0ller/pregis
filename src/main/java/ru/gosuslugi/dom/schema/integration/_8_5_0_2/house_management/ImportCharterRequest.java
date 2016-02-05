
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;sequence>
 *         &lt;sequence>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
 *           &lt;element name="CharterVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;choice>
 *           &lt;element name="Charter">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}CharterType">
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="RollOverCharter" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}RollOverType"/>
 *           &lt;element name="TerminateCharter">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}TerminateType">
 *                   &lt;sequence>
 *                     &lt;element name="Reason">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}LongTextType">
 *                           &lt;maxLength value="255"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="AnnulmentCharter">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}AnnulmentType">
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
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
    "transportGUID",
    "charterVersionGUID",
    "charter",
    "rollOverCharter",
    "terminateCharter",
    "annulmentCharter"
})
@XmlRootElement(name = "importCharterRequest")
public class ImportCharterRequest
    extends BaseType
{

    @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
    protected String transportGUID;
    @XmlElement(name = "CharterVersionGUID")
    protected String charterVersionGUID;
    @XmlElement(name = "Charter")
    protected ImportCharterRequest.Charter charter;
    @XmlElement(name = "RollOverCharter")
    protected RollOverType rollOverCharter;
    @XmlElement(name = "TerminateCharter")
    protected ImportCharterRequest.TerminateCharter terminateCharter;
    @XmlElement(name = "AnnulmentCharter")
    protected ImportCharterRequest.AnnulmentCharter annulmentCharter;

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
     * Gets the value of the charter property.
     * 
     * @return
     *     possible object is
     *     {@link ImportCharterRequest.Charter }
     *     
     */
    public ImportCharterRequest.Charter getCharter() {
        return charter;
    }

    /**
     * Sets the value of the charter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportCharterRequest.Charter }
     *     
     */
    public void setCharter(ImportCharterRequest.Charter value) {
        this.charter = value;
    }

    /**
     * Gets the value of the rollOverCharter property.
     * 
     * @return
     *     possible object is
     *     {@link RollOverType }
     *     
     */
    public RollOverType getRollOverCharter() {
        return rollOverCharter;
    }

    /**
     * Sets the value of the rollOverCharter property.
     * 
     * @param value
     *     allowed object is
     *     {@link RollOverType }
     *     
     */
    public void setRollOverCharter(RollOverType value) {
        this.rollOverCharter = value;
    }

    /**
     * Gets the value of the terminateCharter property.
     * 
     * @return
     *     possible object is
     *     {@link ImportCharterRequest.TerminateCharter }
     *     
     */
    public ImportCharterRequest.TerminateCharter getTerminateCharter() {
        return terminateCharter;
    }

    /**
     * Sets the value of the terminateCharter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportCharterRequest.TerminateCharter }
     *     
     */
    public void setTerminateCharter(ImportCharterRequest.TerminateCharter value) {
        this.terminateCharter = value;
    }

    /**
     * Gets the value of the annulmentCharter property.
     * 
     * @return
     *     possible object is
     *     {@link ImportCharterRequest.AnnulmentCharter }
     *     
     */
    public ImportCharterRequest.AnnulmentCharter getAnnulmentCharter() {
        return annulmentCharter;
    }

    /**
     * Sets the value of the annulmentCharter property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportCharterRequest.AnnulmentCharter }
     *     
     */
    public void setAnnulmentCharter(ImportCharterRequest.AnnulmentCharter value) {
        this.annulmentCharter = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}AnnulmentType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AnnulmentCharter
        extends AnnulmentType
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}CharterType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Charter
        extends CharterType
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}TerminateType">
     *       &lt;sequence>
     *         &lt;element name="Reason">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}LongTextType">
     *               &lt;maxLength value="255"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
        "reason"
    })
    public static class TerminateCharter
        extends TerminateType
    {

        @XmlElement(name = "Reason", required = true)
        protected String reason;

        /**
         * Gets the value of the reason property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReason() {
            return reason;
        }

        /**
         * Sets the value of the reason property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReason(String value) {
            this.reason = value;
        }

    }

}

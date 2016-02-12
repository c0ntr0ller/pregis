
package ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure;

import java.util.ArrayList;
import java.util.List;
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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="RKIItem" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
 *                     &lt;element name="OKIGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;choice>
 *                     &lt;element name="OKI">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/}InfrastructureType">
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="Termination" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;/choice>
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
    "rkiItem"
})
@XmlRootElement(name = "importOKIRequest")
public class ImportOKIRequest
    extends BaseType
{

    @XmlElement(name = "RKIItem", required = true)
    protected List<ImportOKIRequest.RKIItem> rkiItem;

    /**
     * Gets the value of the rkiItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rkiItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRKIItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportOKIRequest.RKIItem }
     * 
     * 
     */
    public List<ImportOKIRequest.RKIItem> getRKIItem() {
        if (rkiItem == null) {
            rkiItem = new ArrayList<ImportOKIRequest.RKIItem>();
        }
        return this.rkiItem;
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
     *         &lt;sequence>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
     *           &lt;element name="OKIGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
     *         &lt;/sequence>
     *         &lt;choice>
     *           &lt;element name="OKI">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/}InfrastructureType">
     *                 &lt;/extension>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="Termination" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    @XmlType(name = "", propOrder = {
        "transportGUID",
        "okiguid",
        "oki",
        "termination"
    })
    public static class RKIItem {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "OKIGUID")
        protected String okiguid;
        @XmlElement(name = "OKI")
        protected ImportOKIRequest.RKIItem.OKI oki;
        @XmlElement(name = "Termination")
        protected Boolean termination;

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
         * Gets the value of the okiguid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOKIGUID() {
            return okiguid;
        }

        /**
         * Sets the value of the okiguid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOKIGUID(String value) {
            this.okiguid = value;
        }

        /**
         * Gets the value of the oki property.
         * 
         * @return
         *     possible object is
         *     {@link ImportOKIRequest.RKIItem.OKI }
         *     
         */
        public ImportOKIRequest.RKIItem.OKI getOKI() {
            return oki;
        }

        /**
         * Sets the value of the oki property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportOKIRequest.RKIItem.OKI }
         *     
         */
        public void setOKI(ImportOKIRequest.RKIItem.OKI value) {
            this.oki = value;
        }

        /**
         * Gets the value of the termination property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTermination() {
            return termination;
        }

        /**
         * Sets the value of the termination property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTermination(Boolean value) {
            this.termination = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/infrastructure/}InfrastructureType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class OKI
            extends InfrastructureType
        {


        }

    }

}
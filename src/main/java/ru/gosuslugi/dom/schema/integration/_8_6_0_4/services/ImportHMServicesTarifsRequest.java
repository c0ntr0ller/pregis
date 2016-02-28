
package ru.gosuslugi.dom.schema.integration._8_6_0_4.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
 *         &lt;element name="HMServicesTarifsDoc" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/}HMServicesTarifsDocType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}TransportGUID"/>
 *                   &lt;element name="ServicesTarifDocGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
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
    "hmServicesTarifsDoc"
})
@XmlRootElement(name = "importHMServicesTarifsRequest")
public class ImportHMServicesTarifsRequest
    extends BaseType
{

    @XmlElement(name = "HMServicesTarifsDoc", required = true)
    protected List<ImportHMServicesTarifsRequest.HMServicesTarifsDoc> hmServicesTarifsDoc;

    /**
     * Gets the value of the hmServicesTarifsDoc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hmServicesTarifsDoc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHMServicesTarifsDoc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportHMServicesTarifsRequest.HMServicesTarifsDoc }
     * 
     * 
     */
    public List<ImportHMServicesTarifsRequest.HMServicesTarifsDoc> getHMServicesTarifsDoc() {
        if (hmServicesTarifsDoc == null) {
            hmServicesTarifsDoc = new ArrayList<ImportHMServicesTarifsRequest.HMServicesTarifsDoc>();
        }
        return this.hmServicesTarifsDoc;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/services/}HMServicesTarifsDocType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}TransportGUID"/>
     *         &lt;element name="ServicesTarifDocGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" minOccurs="0"/>
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
        "servicesTarifDocGUID"
    })
    public static class HMServicesTarifsDoc
        extends HMServicesTarifsDocType
    {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ServicesTarifDocGUID")
        protected String servicesTarifDocGUID;

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
         * Gets the value of the servicesTarifDocGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getServicesTarifDocGUID() {
            return servicesTarifDocGUID;
        }

        /**
         * Sets the value of the servicesTarifDocGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setServicesTarifDocGUID(String value) {
            this.servicesTarifDocGUID = value;
        }

    }

}

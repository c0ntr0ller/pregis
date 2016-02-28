
package ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management;

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
 *         &lt;sequence>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}TransportGUID"/>
 *           &lt;element name="ProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;choice>
 *           &lt;element name="Protocol">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ProtocolType">
 *                   &lt;sequence>
 *                     &lt;element name="Placing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Placing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="Revert" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="Delete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "protocolGUID",
    "protocol",
    "placing",
    "revert",
    "delete"
})
@XmlRootElement(name = "importVotingProtocolRequest")
public class ImportVotingProtocolRequest
    extends BaseType
{

    @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
    protected String transportGUID;
    @XmlElement(name = "ProtocolGUID")
    protected String protocolGUID;
    @XmlElement(name = "Protocol")
    protected ImportVotingProtocolRequest.Protocol protocol;
    @XmlElement(name = "Placing")
    protected Boolean placing;
    @XmlElement(name = "Revert")
    protected Boolean revert;
    @XmlElement(name = "Delete")
    protected Boolean delete;

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
     * Gets the value of the protocolGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolGUID() {
        return protocolGUID;
    }

    /**
     * Sets the value of the protocolGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolGUID(String value) {
        this.protocolGUID = value;
    }

    /**
     * Gets the value of the protocol property.
     * 
     * @return
     *     possible object is
     *     {@link ImportVotingProtocolRequest.Protocol }
     *     
     */
    public ImportVotingProtocolRequest.Protocol getProtocol() {
        return protocol;
    }

    /**
     * Sets the value of the protocol property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportVotingProtocolRequest.Protocol }
     *     
     */
    public void setProtocol(ImportVotingProtocolRequest.Protocol value) {
        this.protocol = value;
    }

    /**
     * Gets the value of the placing property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPlacing() {
        return placing;
    }

    /**
     * Sets the value of the placing property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPlacing(Boolean value) {
        this.placing = value;
    }

    /**
     * Gets the value of the revert property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRevert() {
        return revert;
    }

    /**
     * Sets the value of the revert property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRevert(Boolean value) {
        this.revert = value;
    }

    /**
     * Gets the value of the delete property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDelete() {
        return delete;
    }

    /**
     * Sets the value of the delete property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDelete(Boolean value) {
        this.delete = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ProtocolType">
     *       &lt;sequence>
     *         &lt;element name="Placing" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "placing"
    })
    public static class Protocol
        extends ProtocolType
    {

        @XmlElement(name = "Placing")
        protected Boolean placing;

        /**
         * Gets the value of the placing property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isPlacing() {
            return placing;
        }

        /**
         * Sets the value of the placing property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setPlacing(Boolean value) {
            this.placing = value;
        }

    }

}

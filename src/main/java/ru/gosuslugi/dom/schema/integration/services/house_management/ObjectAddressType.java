
package ru.gosuslugi.dom.schema.integration.services.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Объект жилищного фонда
 * 
 * <p>Java class for ObjectAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObjectAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
 *         &lt;element name="ApartmentNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[а-яА-ЯёЁ0-9]{1,120}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="RoomNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectAddressType", propOrder = {
    "fiasHouseGuid",
    "apartmentNumber",
    "roomNumber"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.house_management.SupplyResourceContractType.ObjectAddress.class
})
public class ObjectAddressType {

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "ApartmentNumber")
    protected String apartmentNumber;
    @XmlElement(name = "RoomNumber")
    protected Byte roomNumber;

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
     * Gets the value of the apartmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartmentNumber() {
        return apartmentNumber;
    }

    /**
     * Sets the value of the apartmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartmentNumber(String value) {
        this.apartmentNumber = value;
    }

    /**
     * Gets the value of the roomNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the value of the roomNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setRoomNumber(Byte value) {
        this.roomNumber = value;
    }

}

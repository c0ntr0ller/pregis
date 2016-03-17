
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * Комната (обновление данных для ОМС)
 * 
 * <p>Java class for RoomUpdateOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoomUpdateOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}GKNType">
 *       &lt;sequence>
 *         &lt;element name="RoomNumber" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PremisesNumType" minOccurs="0"/>
 *         &lt;element name="Square" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}RoomSquareType" minOccurs="0"/>
 *         &lt;element name="ResidentialHouseType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;element name="TerminationDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomUpdateOMSType", propOrder = {
    "roomNumber",
    "square",
    "residentialHouseType",
    "terminationDate"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseOMSRequest.ApartmentHouse.ResidentialPremises.LivingRoomToUpdate.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseOMSRequest.LivingHouse.LivingRoomToUpdate.class
})
public class RoomUpdateOMSType
    extends GKNType
{

    @XmlElement(name = "RoomNumber")
    protected String roomNumber;
    @XmlElement(name = "Square")
    protected BigDecimal square;
    @XmlElement(name = "ResidentialHouseType")
    protected NsiRef residentialHouseType;
    @XmlElement(name = "TerminationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar terminationDate;

    /**
     * Gets the value of the roomNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the value of the roomNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomNumber(String value) {
        this.roomNumber = value;
    }

    /**
     * Gets the value of the square property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSquare() {
        return square;
    }

    /**
     * Sets the value of the square property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSquare(BigDecimal value) {
        this.square = value;
    }

    /**
     * Gets the value of the residentialHouseType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getResidentialHouseType() {
        return residentialHouseType;
    }

    /**
     * Sets the value of the residentialHouseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setResidentialHouseType(NsiRef value) {
        this.residentialHouseType = value;
    }

    /**
     * Gets the value of the terminationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the value of the terminationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTerminationDate(XMLGregorianCalendar value) {
        this.terminationDate = value;
    }

}

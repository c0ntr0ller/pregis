
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Комната (для импорта от ОМС)
 * 
 * <p>Java class for RoomOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoomOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}GKN_EGRP_KeyType">
 *       &lt;sequence>
 *         &lt;element name="RoomNumber" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}PremisesNumType"/>
 *         &lt;element name="Square" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}RoomSquareType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomOMSType", propOrder = {
    "roomNumber",
    "square"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management.ImportHouseOMSRequest.ApartmentHouse.ResidentialPremises.LivingRoomToCreate.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management.ImportHouseOMSRequest.LivingHouse.LivingRoomToCreate.class
})
public class RoomOMSType
    extends GKNEGRPKeyType
{

    @XmlElement(name = "RoomNumber", required = true)
    protected String roomNumber;
    @XmlElement(name = "Square", required = true)
    protected BigDecimal square;

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

}

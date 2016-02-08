
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Базовый тип Размер доли
 * 
 * <p>Java class for PartSizeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartSizeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fraction" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IntPart">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;maxInclusive value="999"/>
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="FracPart">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
 *                         &lt;minInclusive value="1"/>
 *                         &lt;maxInclusive value="999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "PartSizeType", propOrder = {
    "fraction"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataType.LivingHouse.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataType.NonResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataType.PremisesAndRooms.ResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataType.PremisesAndRooms.LivingRoom.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataToUpdate.RealEstateType.LivingHouse.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataToUpdate.RealEstateType.NonResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataToUpdate.RealEstateType.PremisesAndRooms.ResidentialPremise.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataToUpdate.RealEstateType.PremisesAndRooms.LivingRoom.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataToUpdate.LivingHouse.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataToUpdate.NonResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataToUpdate.PremisesAndRooms.ResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.EncbrDataToUpdate.PremisesAndRooms.LivingRoom.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataType.RealEstateType.LivingHouse.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataType.RealEstateType.NonResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataType.RealEstateType.PremisesAndRooms.ResidentialPremises.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.ShareDataType.RealEstateType.PremisesAndRooms.LivingRoom.class
})
public class PartSizeType {

    @XmlElement(name = "Fraction")
    protected PartSizeType.Fraction fraction;

    /**
     * Gets the value of the fraction property.
     * 
     * @return
     *     possible object is
     *     {@link PartSizeType.Fraction }
     *     
     */
    public PartSizeType.Fraction getFraction() {
        return fraction;
    }

    /**
     * Sets the value of the fraction property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartSizeType.Fraction }
     *     
     */
    public void setFraction(PartSizeType.Fraction value) {
        this.fraction = value;
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
     *         &lt;element name="IntPart">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;maxInclusive value="999"/>
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="FracPart">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}positiveInteger">
     *               &lt;minInclusive value="1"/>
     *               &lt;maxInclusive value="999"/>
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
    @XmlType(name = "", propOrder = {
        "intPart",
        "fracPart"
    })
    public static class Fraction {

        @XmlElement(name = "IntPart")
        protected int intPart;
        @XmlElement(name = "FracPart")
        protected int fracPart;

        /**
         * Gets the value of the intPart property.
         * 
         */
        public int getIntPart() {
            return intPart;
        }

        /**
         * Sets the value of the intPart property.
         * 
         */
        public void setIntPart(int value) {
            this.intPart = value;
        }

        /**
         * Gets the value of the fracPart property.
         * 
         */
        public int getFracPart() {
            return fracPart;
        }

        /**
         * Sets the value of the fracPart property.
         * 
         */
        public void setFracPart(int value) {
            this.fracPart = value;
        }

    }

}

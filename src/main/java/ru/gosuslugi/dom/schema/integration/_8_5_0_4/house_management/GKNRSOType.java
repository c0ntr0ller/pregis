
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Сведения из ГКН
 * 
 * <p>Java class for GKNRSOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GKNRSOType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="CadastralNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NoKNRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NoKNData" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GKNRSOType", propOrder = {
    "cadastralNumber",
    "noKNRegistered",
    "noKNData"
})
@XmlSeeAlso({
    HouseBasicUpdateRSOType.class,
    RoomUpdateRSOType.class,
    HouseBasicRSOType.class,
    RoomRSOType.class,
    PremisesBasicRSOType.class,
    PremisesBasicUpdateRSOType.class
})
public class GKNRSOType {

    @XmlElement(name = "CadastralNumber")
    protected String cadastralNumber;
    @XmlElement(name = "NoKNRegistered")
    protected Boolean noKNRegistered;
    @XmlElement(name = "NoKNData")
    protected Boolean noKNData;

    /**
     * Gets the value of the cadastralNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadastralNumber() {
        return cadastralNumber;
    }

    /**
     * Sets the value of the cadastralNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadastralNumber(String value) {
        this.cadastralNumber = value;
    }

    /**
     * Gets the value of the noKNRegistered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoKNRegistered() {
        return noKNRegistered;
    }

    /**
     * Sets the value of the noKNRegistered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoKNRegistered(Boolean value) {
        this.noKNRegistered = value;
    }

    /**
     * Gets the value of the noKNData property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoKNData() {
        return noKNData;
    }

    /**
     * Sets the value of the noKNData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoKNData(Boolean value) {
        this.noKNData = value;
    }

}

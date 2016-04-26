
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Сведения из ГКН для РСО
 * 
 * <p>Java class for GKN_EGRP_KeyRSOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GKN_EGRP_KeyRSOType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}CadastralNumber"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}No_RSO_GKN_EGRP_Registered"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}No_RSO_GKN_EGRP_Data"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GKN_EGRP_KeyRSOType", propOrder = {
    "cadastralNumber",
    "noRSOGKNEGRPRegistered",
    "noRSOGKNEGRPData"
})
@XmlSeeAlso({
    HouseBasicUpdateRSOType.class,
    RoomUpdateRSOType.class,
    HouseBasicRSOType.class,
    RoomRSOType.class,
    PremisesBasicRSOType.class,
    PremisesBasicUpdateRSOType.class
})
public class GKNEGRPKeyRSOType {

    @XmlElement(name = "CadastralNumber")
    protected String cadastralNumber;
    @XmlElement(name = "No_RSO_GKN_EGRP_Registered")
    protected Boolean noRSOGKNEGRPRegistered;
    @XmlElement(name = "No_RSO_GKN_EGRP_Data")
    protected Boolean noRSOGKNEGRPData;

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
     * Gets the value of the noRSOGKNEGRPRegistered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoRSOGKNEGRPRegistered() {
        return noRSOGKNEGRPRegistered;
    }

    /**
     * Sets the value of the noRSOGKNEGRPRegistered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoRSOGKNEGRPRegistered(Boolean value) {
        this.noRSOGKNEGRPRegistered = value;
    }

    /**
     * Gets the value of the noRSOGKNEGRPData property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoRSOGKNEGRPData() {
        return noRSOGKNEGRPData;
    }

    /**
     * Sets the value of the noRSOGKNEGRPData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoRSOGKNEGRPData(Boolean value) {
        this.noRSOGKNEGRPData = value;
    }

}

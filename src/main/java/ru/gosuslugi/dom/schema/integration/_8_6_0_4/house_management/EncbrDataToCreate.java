
package ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}EncbrDataType">
 *       &lt;choice>
 *         &lt;element name="TransportGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/house-management/}ShareGUID"/>
 *       &lt;/choice>
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
    "shareGUID"
})
@XmlRootElement(name = "EncbrDataToCreate")
public class EncbrDataToCreate
    extends EncbrDataType
{

    @XmlElement(name = "TransportGUID")
    protected String transportGUID;
    @XmlElement(name = "ShareGUID")
    protected String shareGUID;

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
     * Идентификатор доли в ГИС ЖКХ (для создания обременения в запросе без доли)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShareGUID() {
        return shareGUID;
    }

    /**
     * Sets the value of the shareGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShareGUID(String value) {
        this.shareGUID = value;
    }

}


package ru.gosuslugi.dom.schema.integration.services.nsi_common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.BaseType;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.NsiItemType;


/**
 * Составной тип. Данные справочника.
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}BaseType">
 *       &lt;choice>
 *         &lt;element name="NsiItem" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}NsiItemType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ErrorMessage"/>
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
    "nsiItem",
    "errorMessage"
})
@XmlRootElement(name = "exportNsiItemResult")
public class ExportNsiItemResult
    extends BaseType
{

    @XmlElement(name = "NsiItem")
    protected NsiItemType nsiItem;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the nsiItem property.
     * 
     * @return
     *     possible object is
     *     {@link NsiItemType }
     *     
     */
    public NsiItemType getNsiItem() {
        return nsiItem;
    }

    /**
     * Sets the value of the nsiItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiItemType }
     *     
     */
    public void setNsiItem(NsiItemType value) {
        this.nsiItem = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageType }
     *     
     */
    public ErrorMessageType getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageType }
     *     
     */
    public void setErrorMessage(ErrorMessageType value) {
        this.errorMessage = value;
    }

}

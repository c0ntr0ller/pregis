
package ru.gosuslugi.dom.schema.integration._8_7_0_4.nsi_common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseAsyncResponseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiItemType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiListType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseAsyncResponseType">
 *       &lt;choice minOccurs="0">
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
 *         &lt;element name="NsiItem" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}NsiItemType"/>
 *         &lt;element name="NsiList" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}NsiListType"/>
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
    "errorMessage",
    "nsiItem",
    "nsiList"
})
@XmlRootElement(name = "getStateResult")
public class GetStateResult
    extends BaseAsyncResponseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "NsiItem")
    protected NsiItemType nsiItem;
    @XmlElement(name = "NsiList")
    protected NsiListType nsiList;

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
     * Gets the value of the nsiList property.
     * 
     * @return
     *     possible object is
     *     {@link NsiListType }
     *     
     */
    public NsiListType getNsiList() {
        return nsiList;
    }

    /**
     * Sets the value of the nsiList property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiListType }
     *     
     */
    public void setNsiList(NsiListType value) {
        this.nsiList = value;
    }

}

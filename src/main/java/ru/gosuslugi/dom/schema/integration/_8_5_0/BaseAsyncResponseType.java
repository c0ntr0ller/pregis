
package ru.gosuslugi.dom.schema.integration._8_5_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Базовый тип ответа на запрос статуса
 * 
 * <p>Java class for BaseAsyncResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseAsyncResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="RequestState" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AsyncRequestStateType"/>
 *         &lt;element name="MessageGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseAsyncResponseType", propOrder = {
    "requestState",
    "messageGUID"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.bills.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.disclosure.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.device_metering.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.payment.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.services.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.inspection.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.fas.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.licenses.GetStateResult.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.infrastructure.GetStateResult.class
})
public class BaseAsyncResponseType
    extends BaseType
{

    @XmlElement(name = "RequestState")
    protected byte requestState;
    @XmlElement(name = "MessageGUID", required = true)
    protected String messageGUID;

    /**
     * Gets the value of the requestState property.
     * 
     */
    public byte getRequestState() {
        return requestState;
    }

    /**
     * Sets the value of the requestState property.
     * 
     */
    public void setRequestState(byte value) {
        this.requestState = value;
    }

    /**
     * Gets the value of the messageGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageGUID() {
        return messageGUID;
    }

    /**
     * Sets the value of the messageGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageGUID(String value) {
        this.messageGUID = value;
    }

}

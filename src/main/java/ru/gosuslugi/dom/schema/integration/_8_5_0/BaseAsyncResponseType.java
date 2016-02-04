
package ru.gosuslugi.dom.schema.integration._8_5_0;

import javax.xml.bind.annotation.*;


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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="RequestState" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AsyncRequestStateType"/>
 *         &lt;element name="MessageGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
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
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.payment.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.inspection.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.services.GetStateResult.class,
        ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.GetStateResult.class,
        ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.infrastructure.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.device_metering.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.bills.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.licenses.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.fas.GetStateResult.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.disclosure.GetStateResult.class
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

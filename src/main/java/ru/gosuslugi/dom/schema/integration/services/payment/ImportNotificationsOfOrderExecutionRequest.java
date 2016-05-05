
package ru.gosuslugi.dom.schema.integration.services.payment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="payment-organization-guid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *         &lt;element name="NotificationOfOrderExecutionType" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}NotificationOfOrderExecutionType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "paymentOrganizationGuid",
    "notificationOfOrderExecutionType"
})
@XmlRootElement(name = "importNotificationsOfOrderExecutionRequest")
public class ImportNotificationsOfOrderExecutionRequest
    extends BaseType
{

    @XmlElement(name = "payment-organization-guid", required = true)
    protected String paymentOrganizationGuid;
    @XmlElement(name = "NotificationOfOrderExecutionType", required = true)
    protected List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> notificationOfOrderExecutionType;

    /**
     * Gets the value of the paymentOrganizationGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentOrganizationGuid() {
        return paymentOrganizationGuid;
    }

    /**
     * Sets the value of the paymentOrganizationGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentOrganizationGuid(String value) {
        this.paymentOrganizationGuid = value;
    }

    /**
     * Gets the value of the notificationOfOrderExecutionType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificationOfOrderExecutionType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotificationOfOrderExecutionType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType }
     * 
     * 
     */
    public List<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType> getNotificationOfOrderExecutionType() {
        if (notificationOfOrderExecutionType == null) {
            notificationOfOrderExecutionType = new ArrayList<ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType>();
        }
        return this.notificationOfOrderExecutionType;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}NotificationOfOrderExecutionType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "transportGUID"
    })
    public static class NotificationOfOrderExecutionType
        extends ru.gosuslugi.dom.schema.integration.base.NotificationOfOrderExecutionType
    {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
        protected String transportGUID;

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

    }

}

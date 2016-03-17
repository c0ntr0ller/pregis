
package ru.gosuslugi.dom.schema.integration._8_6_0_6.payment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NotificationOfOrderExecutionCancellationType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="NotificationOfOrderExecutionCancellation" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}NotificationOfOrderExecutionCancellationType" maxOccurs="1000"/>
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
    "notificationOfOrderExecutionCancellation"
})
@XmlRootElement(name = "importNotificationsOfOrderExecutionCancellationRequest")
public class ImportNotificationsOfOrderExecutionCancellationRequest
    extends BaseType
{

    @XmlElement(name = "NotificationOfOrderExecutionCancellation", required = true)
    protected List<NotificationOfOrderExecutionCancellationType> notificationOfOrderExecutionCancellation;

    /**
     * Gets the value of the notificationOfOrderExecutionCancellation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificationOfOrderExecutionCancellation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotificationOfOrderExecutionCancellation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NotificationOfOrderExecutionCancellationType }
     * 
     * 
     */
    public List<NotificationOfOrderExecutionCancellationType> getNotificationOfOrderExecutionCancellation() {
        if (notificationOfOrderExecutionCancellation == null) {
            notificationOfOrderExecutionCancellation = new ArrayList<NotificationOfOrderExecutionCancellationType>();
        }
        return this.notificationOfOrderExecutionCancellation;
    }

}


package ru.gosuslugi.dom.schema.integration._8_6_0_4.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" maxOccurs="100"/>
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
    "paymentDocumentGuid"
})
@XmlRootElement(name = "exportNotificationsOfOrderExecutionRequest")
public class ExportNotificationsOfOrderExecutionRequest
    extends BaseType
{

    @XmlElement(name = "PaymentDocumentGuid", required = true)
    protected List<String> paymentDocumentGuid;

    /**
     * Gets the value of the paymentDocumentGuid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDocumentGuid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDocumentGuid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPaymentDocumentGuid() {
        if (paymentDocumentGuid == null) {
            paymentDocumentGuid = new ArrayList<String>();
        }
        return this.paymentDocumentGuid;
    }

}

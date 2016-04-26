
package ru.gosuslugi.dom.schema.integration._8_7_0_4.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentID" maxOccurs="100"/>
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
    "paymentDocumentID"
})
@XmlRootElement(name = "exportNotificationsOfOrderExecutionRequest")
public class ExportNotificationsOfOrderExecutionRequest
    extends BaseType
{

    @XmlElement(name = "PaymentDocumentID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected List<String> paymentDocumentID;

    /**
     * Gets the value of the paymentDocumentID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDocumentID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDocumentID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPaymentDocumentID() {
        if (paymentDocumentID == null) {
            paymentDocumentID = new ArrayList<String>();
        }
        return this.paymentDocumentID;
    }

}

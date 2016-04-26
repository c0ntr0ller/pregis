
package ru.gosuslugi.dom.schema.integration._8_7_0_4.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;choice>
 *         &lt;element name="PaymentDocument" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/}exportPaymentDocumentResultType" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
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
    "paymentDocument",
    "errorMessage"
})
@XmlRootElement(name = "exportPaymentDocumentResult")
public class ExportPaymentDocumentResult
    extends BaseType
{

    @XmlElement(name = "PaymentDocument")
    protected List<ExportPaymentDocumentResultType> paymentDocument;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the paymentDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportPaymentDocumentResultType }
     * 
     * 
     */
    public List<ExportPaymentDocumentResultType> getPaymentDocument() {
        if (paymentDocument == null) {
            paymentDocument = new ArrayList<ExportPaymentDocumentResultType>();
        }
        return this.paymentDocument;
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

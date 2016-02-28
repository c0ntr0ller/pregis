
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
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}Month"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}Year"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}AccountGuid" maxOccurs="1000"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}PaymentDocumentNumber" maxOccurs="1000" minOccurs="0"/>
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
    "month",
    "year",
    "accountGuid",
    "paymentDocumentNumber"
})
@XmlRootElement(name = "exportPaymentDocumentRequest")
public class ExportPaymentDocumentRequest
    extends BaseType
{

    @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
    protected int month;
    @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
    protected short year;
    @XmlElement(name = "AccountGuid", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
    protected List<String> accountGuid;
    @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
    protected List<String> paymentDocumentNumber;

    /**
     * Gets the value of the month property.
     * 
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     */
    public void setMonth(int value) {
        this.month = value;
    }

    /**
     * Gets the value of the year property.
     * 
     */
    public short getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(short value) {
        this.year = value;
    }

    /**
     * Gets the value of the accountGuid property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountGuid property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountGuid().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAccountGuid() {
        if (accountGuid == null) {
            accountGuid = new ArrayList<String>();
        }
        return this.accountGuid;
    }

    /**
     * Gets the value of the paymentDocumentNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDocumentNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDocumentNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPaymentDocumentNumber() {
        if (paymentDocumentNumber == null) {
            paymentDocumentNumber = new ArrayList<String>();
        }
        return this.paymentDocumentNumber;
    }

}

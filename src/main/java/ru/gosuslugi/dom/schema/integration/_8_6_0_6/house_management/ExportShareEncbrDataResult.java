
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_6_0.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseType">
 *       &lt;choice>
 *         &lt;element name="ShareEncbr" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}exportShareEncbrDataResultType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}ErrorMessage"/>
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
    "shareEncbr",
    "errorMessage"
})
@XmlRootElement(name = "exportShareEncbrDataResult")
public class ExportShareEncbrDataResult
    extends BaseType
{

    @XmlElement(name = "ShareEncbr")
    protected List<ExportShareEncbrDataResult.ShareEncbr> shareEncbr;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/")
    protected ErrorMessageType errorMessage;

    /**
     * Gets the value of the shareEncbr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shareEncbr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShareEncbr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportShareEncbrDataResult.ShareEncbr }
     * 
     * 
     */
    public List<ExportShareEncbrDataResult.ShareEncbr> getShareEncbr() {
        if (shareEncbr == null) {
            shareEncbr = new ArrayList<ExportShareEncbrDataResult.ShareEncbr>();
        }
        return this.shareEncbr;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}exportShareEncbrDataResultType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ShareEncbr
        extends ExportShareEncbrDataResultType
    {


    }

}

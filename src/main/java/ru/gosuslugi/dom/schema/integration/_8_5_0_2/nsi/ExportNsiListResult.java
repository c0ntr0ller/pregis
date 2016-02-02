
package ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ErrorMessageType;


/**
 * Составной тип. Перечень справочников с указанием даты последнего изменения каждого из них.
 * 
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;choice>
 *         &lt;element name="NsiList" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/nsi/}NsiListType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ErrorMessage"/>
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
    "nsiList",
    "errorMessage"
})
@XmlRootElement(name = "exportNsiListResult")
public class ExportNsiListResult
    extends BaseType
{

    @XmlElement(name = "NsiList")
    protected NsiListType nsiList;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
    protected ErrorMessageType errorMessage;

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

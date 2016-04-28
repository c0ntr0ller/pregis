
package ru.gosuslugi.dom.schema.integration._8_7_0_6.nsi_common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiListType;


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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}BaseType">
 *       &lt;choice>
 *         &lt;element name="NsiList" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}NsiListType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}ErrorMessage"/>
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
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/")
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

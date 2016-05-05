
package ru.gosuslugi.dom.schema.integration.services.nsi_common;

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
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ListGroup" minOccurs="0"/>
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
    "listGroup"
})
@XmlRootElement(name = "exportNsiListRequest")
public class ExportNsiListRequest
    extends BaseType
{

    @XmlElement(name = "ListGroup", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected String listGroup;

    /**
     * Gets the value of the listGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListGroup() {
        return listGroup;
    }

    /**
     * Sets the value of the listGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListGroup(String value) {
        this.listGroup = value;
    }

}

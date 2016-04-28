
package ru.gosuslugi.dom.schema.integration._8_7_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Составной тип. Наименование и значение поля "Вложение"
 * 
 * <p>Java class for NsiElementAttachmentFieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NsiElementAttachmentFieldType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}NsiElementFieldType">
 *       &lt;sequence>
 *         &lt;element name="Document" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NsiElementAttachmentFieldType", propOrder = {
    "document"
})
public class NsiElementAttachmentFieldType
    extends NsiElementFieldType
{

    @XmlElement(name = "Document", required = true)
    protected AttachmentType document;

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentType }
     *     
     */
    public AttachmentType getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentType }
     *     
     */
    public void setDocument(AttachmentType value) {
        this.document = value;
    }

}

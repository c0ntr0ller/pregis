
package ru.gosuslugi.dom.schema.integration._8_7_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Составной тип. Наименование и значение поля для элемента справочника. Абстрактный тип.
 * 
 * <p>Java class for NsiElementFieldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NsiElementFieldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}FieldNameType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NsiElementFieldType", propOrder = {
    "name"
})
@XmlSeeAlso({
    NsiElementStringFieldType.class,
    NsiElementEnumFieldType.class,
    NsiElementBooleanFieldType.class,
    NsiElementIntegerFieldType.class,
    NsiElementNsiFieldType.class,
    NsiElementAttachmentFieldType.class,
    NsiElementNsiRefFieldType.class,
    NsiElementOkeiRefFieldType.class,
    NsiElementDateFieldType.class,
    NsiElementFiasAddressRefFieldType.class,
    NsiElementFloatFieldType.class
})
public abstract class NsiElementFieldType {

    @XmlElement(name = "Name", required = true)
    protected String name;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}


package ru.gosuslugi.dom.schema.integration._8_6_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Ссылка на справочник
 * 
 * <p>Java class for nsiRef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nsiRef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiCodeType"/>
 *         &lt;element name="GUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;element name="Name" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}LongTextType">
 *               &lt;maxLength value="1200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nsiRef", propOrder = {
    "code",
    "guid",
    "name"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional.FormOfFinancialStatement.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.services.HMServicesTarifsDocType.KU.MServiceType.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.infrastructure.InfrastructureType.OKIType.class
})
public class NsiRef {

    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "GUID", required = true)
    protected String guid;
    @XmlElement(name = "Name")
    protected String name;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGUID(String value) {
        this.guid = value;
    }

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

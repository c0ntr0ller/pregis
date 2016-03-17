
package ru.gosuslugi.dom.schema.integration._8_6_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.Attachments;


/**
 * Вложение
 * 
 * <p>Java class for AttachmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="500"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}Attachment"/>
 *         &lt;element name="AttachmentHASH">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
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
@XmlType(name = "AttachmentType", propOrder = {
    "name",
    "description",
    "attachment",
    "attachmentHASH"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ContractType.AgreementAttachment.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.Section13 .BringToJusticeFact.OffenceDoc.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.services.CompletedWorksByPeriodType.NewAct.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.services.CompletedWorksByPeriodExportType.Act.class,
    Attachments.class
})
public class AttachmentType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Attachment", required = true)
    protected Attachment attachment;
    @XmlElement(name = "AttachmentHASH", required = true)
    protected String attachmentHASH;

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

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the attachment property.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * Sets the value of the attachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setAttachment(Attachment value) {
        this.attachment = value;
    }

    /**
     * Gets the value of the attachmentHASH property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentHASH() {
        return attachmentHASH;
    }

    /**
     * Sets the value of the attachmentHASH property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentHASH(String value) {
        this.attachmentHASH = value;
    }

}

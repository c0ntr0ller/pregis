
package ru.gosuslugi.dom.schema.integration._8_5_0_4.inspection;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;


/**
 * Сведения об отмене документа с приложением подтверждающих документов.
 * 
 * <p>Java class for CancelledInfoWithAttachmentsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelledInfoWithAttachmentsType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}CancelledInfoType">
 *       &lt;sequence>
 *         &lt;element name="Attachments" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="IsJudgementDecision" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelledInfoWithAttachmentsType", propOrder = {
    "attachments",
    "isJudgementDecision"
})
public class CancelledInfoWithAttachmentsType
    extends CancelledInfoType
{

    @XmlElement(name = "Attachments")
    protected List<AttachmentType> attachments;
    @XmlElement(name = "IsJudgementDecision")
    protected boolean isJudgementDecision;

    /**
     * Gets the value of the attachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentType }
     * 
     * 
     */
    public List<AttachmentType> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<AttachmentType>();
        }
        return this.attachments;
    }

    /**
     * Gets the value of the isJudgementDecision property.
     * 
     */
    public boolean isIsJudgementDecision() {
        return isJudgementDecision;
    }

    /**
     * Sets the value of the isJudgementDecision property.
     * 
     */
    public void setIsJudgementDecision(boolean value) {
        this.isJudgementDecision = value;
    }

}

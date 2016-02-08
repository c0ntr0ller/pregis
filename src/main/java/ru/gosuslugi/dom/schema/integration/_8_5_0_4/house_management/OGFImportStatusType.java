
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Сводный статус объекта жилищного фонда в ГИС ЖКХ (для импорта)
 * 
 * <p>Java class for OGFImportStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OGFImportStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GKNRelationshipStatus">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}GKNRelationshipStatusType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "OGFImportStatusType", propOrder = {
    "gknRelationshipStatus"
})
public class OGFImportStatusType {

    @XmlElement(name = "GKNRelationshipStatus", required = true)
    protected OGFImportStatusType.GKNRelationshipStatus gknRelationshipStatus;

    /**
     * Gets the value of the gknRelationshipStatus property.
     * 
     * @return
     *     possible object is
     *     {@link OGFImportStatusType.GKNRelationshipStatus }
     *     
     */
    public OGFImportStatusType.GKNRelationshipStatus getGKNRelationshipStatus() {
        return gknRelationshipStatus;
    }

    /**
     * Sets the value of the gknRelationshipStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link OGFImportStatusType.GKNRelationshipStatus }
     *     
     */
    public void setGKNRelationshipStatus(OGFImportStatusType.GKNRelationshipStatus value) {
        this.gknRelationshipStatus = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}GKNRelationshipStatusType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class GKNRelationshipStatus
        extends GKNRelationshipStatusType
    {


    }

}

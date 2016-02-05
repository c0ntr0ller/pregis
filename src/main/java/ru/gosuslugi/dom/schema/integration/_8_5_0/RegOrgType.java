
package ru.gosuslugi.dom.schema.integration._8_5_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegOrgType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegOrgType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}orgRootEntityGUID"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegOrgType", propOrder = {
    "orgRootEntityGUID"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.inspection.ScheduledExaminationSubjectInfoType.Organization.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_2.inspection.ScheduledExaminationSubjectInfoType.Individual.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_2.inspection.UnscheduledExaminationSubjectInfoType.Organization.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_2.inspection.UnscheduledExaminationSubjectInfoType.Individual.class
})
public class RegOrgType {

    @XmlElement(required = true)
    protected String orgRootEntityGUID;

    /**
     * Gets the value of the orgRootEntityGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgRootEntityGUID() {
        return orgRootEntityGUID;
    }

    /**
     * Sets the value of the orgRootEntityGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgRootEntityGUID(String value) {
        this.orgRootEntityGUID = value;
    }

}

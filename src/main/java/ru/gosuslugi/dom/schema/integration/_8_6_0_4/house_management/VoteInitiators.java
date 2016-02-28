
package ru.gosuslugi.dom.schema.integration._8_6_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_6_0.RegOrgVersionType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}IndType"/>
 *         &lt;element name="Org" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}RegOrgVersionType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ind",
    "org"
})
@XmlRootElement(name = "VoteInitiators")
public class VoteInitiators {

    @XmlElement(name = "Ind")
    protected IndType ind;
    @XmlElement(name = "Org")
    protected RegOrgVersionType org;

    /**
     * Gets the value of the ind property.
     * 
     * @return
     *     possible object is
     *     {@link IndType }
     *     
     */
    public IndType getInd() {
        return ind;
    }

    /**
     * Sets the value of the ind property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndType }
     *     
     */
    public void setInd(IndType value) {
        this.ind = value;
    }

    /**
     * Gets the value of the org property.
     * 
     * @return
     *     possible object is
     *     {@link RegOrgVersionType }
     *     
     */
    public RegOrgVersionType getOrg() {
        return org;
    }

    /**
     * Sets the value of the org property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgVersionType }
     *     
     */
    public void setOrg(RegOrgVersionType value) {
        this.org = value;
    }

}

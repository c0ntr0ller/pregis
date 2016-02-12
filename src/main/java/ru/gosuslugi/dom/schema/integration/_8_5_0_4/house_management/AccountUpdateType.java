
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_5_0.RegOrgType;


/**
 * Характеристика ЛС
 * 
 * <p>Java class for AccountUpdateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountUpdateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LivingPersonsNumber">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="OwnerInd" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}IndType"/>
 *           &lt;element name="OwnerOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}RegOrgType"/>
 *           &lt;element name="RenterInd" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}IndType"/>
 *           &lt;element name="RenterOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}RegOrgType"/>
 *         &lt;/choice>
 *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="NonResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}ShareGUID" maxOccurs="unbounded"/>
 *         &lt;element name="Closed" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}ClosedAccountAttributesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountUpdateType", propOrder = {
    "livingPersonsNumber",
    "ownerInd",
    "ownerOrg",
    "renterInd",
    "renterOrg",
    "totalSquare",
    "residentialSquare",
    "nonResidentialSquare",
    "shareGUID",
    "closed"
})
public class AccountUpdateType {

    @XmlElement(name = "LivingPersonsNumber")
    protected byte livingPersonsNumber;
    @XmlElement(name = "OwnerInd")
    protected IndType ownerInd;
    @XmlElement(name = "OwnerOrg")
    protected RegOrgType ownerOrg;
    @XmlElement(name = "RenterInd")
    protected IndType renterInd;
    @XmlElement(name = "RenterOrg")
    protected RegOrgType renterOrg;
    @XmlElement(name = "TotalSquare")
    protected BigDecimal totalSquare;
    @XmlElement(name = "ResidentialSquare")
    protected BigDecimal residentialSquare;
    @XmlElement(name = "NonResidentialSquare")
    protected BigDecimal nonResidentialSquare;
    @XmlElement(name = "ShareGUID", required = true)
    protected List<String> shareGUID;
    @XmlElement(name = "Closed")
    protected ClosedAccountAttributesType closed;

    /**
     * Gets the value of the livingPersonsNumber property.
     * 
     */
    public byte getLivingPersonsNumber() {
        return livingPersonsNumber;
    }

    /**
     * Sets the value of the livingPersonsNumber property.
     * 
     */
    public void setLivingPersonsNumber(byte value) {
        this.livingPersonsNumber = value;
    }

    /**
     * Gets the value of the ownerInd property.
     * 
     * @return
     *     possible object is
     *     {@link IndType }
     *     
     */
    public IndType getOwnerInd() {
        return ownerInd;
    }

    /**
     * Sets the value of the ownerInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndType }
     *     
     */
    public void setOwnerInd(IndType value) {
        this.ownerInd = value;
    }

    /**
     * Gets the value of the ownerOrg property.
     * 
     * @return
     *     possible object is
     *     {@link RegOrgType }
     *     
     */
    public RegOrgType getOwnerOrg() {
        return ownerOrg;
    }

    /**
     * Sets the value of the ownerOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgType }
     *     
     */
    public void setOwnerOrg(RegOrgType value) {
        this.ownerOrg = value;
    }

    /**
     * Gets the value of the renterInd property.
     * 
     * @return
     *     possible object is
     *     {@link IndType }
     *     
     */
    public IndType getRenterInd() {
        return renterInd;
    }

    /**
     * Sets the value of the renterInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndType }
     *     
     */
    public void setRenterInd(IndType value) {
        this.renterInd = value;
    }

    /**
     * Gets the value of the renterOrg property.
     * 
     * @return
     *     possible object is
     *     {@link RegOrgType }
     *     
     */
    public RegOrgType getRenterOrg() {
        return renterOrg;
    }

    /**
     * Sets the value of the renterOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgType }
     *     
     */
    public void setRenterOrg(RegOrgType value) {
        this.renterOrg = value;
    }

    /**
     * Gets the value of the totalSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalSquare() {
        return totalSquare;
    }

    /**
     * Sets the value of the totalSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalSquare(BigDecimal value) {
        this.totalSquare = value;
    }

    /**
     * Gets the value of the residentialSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResidentialSquare() {
        return residentialSquare;
    }

    /**
     * Sets the value of the residentialSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResidentialSquare(BigDecimal value) {
        this.residentialSquare = value;
    }

    /**
     * Gets the value of the nonResidentialSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNonResidentialSquare() {
        return nonResidentialSquare;
    }

    /**
     * Sets the value of the nonResidentialSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNonResidentialSquare(BigDecimal value) {
        this.nonResidentialSquare = value;
    }

    /**
     * Gets the value of the shareGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shareGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShareGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getShareGUID() {
        if (shareGUID == null) {
            shareGUID = new ArrayList<String>();
        }
        return this.shareGUID;
    }

    /**
     * Gets the value of the closed property.
     * 
     * @return
     *     possible object is
     *     {@link ClosedAccountAttributesType }
     *     
     */
    public ClosedAccountAttributesType getClosed() {
        return closed;
    }

    /**
     * Sets the value of the closed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClosedAccountAttributesType }
     *     
     */
    public void setClosed(ClosedAccountAttributesType value) {
        this.closed = value;
    }

}
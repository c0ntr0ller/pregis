
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_6_0.RegOrgVersionType;


/**
 * <p>Java class for AccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="isUOAccount" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="isRSOAccount" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="OwnerInd" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}IndType"/>
 *           &lt;element name="OwnerOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrgVersionType"/>
 *           &lt;element name="RenterInd" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}IndType"/>
 *           &lt;element name="RenterOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}RegOrgVersionType"/>
 *         &lt;/choice>
 *         &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LivingPersonsNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType"/>
 *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="HeatedArea" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ShareGUID" maxOccurs="unbounded"/>
 *         &lt;element name="Closed" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}ClosedAccountAttributesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountType", propOrder = {
    "isUOAccount",
    "isRSOAccount",
    "ownerInd",
    "ownerOrg",
    "renterInd",
    "renterOrg",
    "creationDate",
    "livingPersonsNumber",
    "totalSquare",
    "residentialSquare",
    "heatedArea",
    "shareGUID",
    "closed"
})
@XmlSeeAlso({
    ExportAccountResultType.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportAccountRequest.Account.class
})
public class AccountType {

    protected Boolean isUOAccount;
    protected Boolean isRSOAccount;
    @XmlElement(name = "OwnerInd")
    protected IndType ownerInd;
    @XmlElement(name = "OwnerOrg")
    protected RegOrgVersionType ownerOrg;
    @XmlElement(name = "RenterInd")
    protected IndType renterInd;
    @XmlElement(name = "RenterOrg")
    protected RegOrgVersionType renterOrg;
    @XmlElement(name = "CreationDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationDate;
    @XmlElement(name = "LivingPersonsNumber")
    protected Byte livingPersonsNumber;
    @XmlElement(name = "TotalSquare", required = true)
    protected BigDecimal totalSquare;
    @XmlElement(name = "ResidentialSquare")
    protected BigDecimal residentialSquare;
    @XmlElement(name = "HeatedArea")
    protected BigDecimal heatedArea;
    @XmlElement(name = "ShareGUID", required = true)
    protected List<String> shareGUID;
    @XmlElement(name = "Closed")
    protected ClosedAccountAttributesType closed;

    /**
     * Gets the value of the isUOAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUOAccount() {
        return isUOAccount;
    }

    /**
     * Sets the value of the isUOAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUOAccount(Boolean value) {
        this.isUOAccount = value;
    }

    /**
     * Gets the value of the isRSOAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRSOAccount() {
        return isRSOAccount;
    }

    /**
     * Sets the value of the isRSOAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRSOAccount(Boolean value) {
        this.isRSOAccount = value;
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
     *     {@link RegOrgVersionType }
     *     
     */
    public RegOrgVersionType getOwnerOrg() {
        return ownerOrg;
    }

    /**
     * Sets the value of the ownerOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgVersionType }
     *     
     */
    public void setOwnerOrg(RegOrgVersionType value) {
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
     *     {@link RegOrgVersionType }
     *     
     */
    public RegOrgVersionType getRenterOrg() {
        return renterOrg;
    }

    /**
     * Sets the value of the renterOrg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegOrgVersionType }
     *     
     */
    public void setRenterOrg(RegOrgVersionType value) {
        this.renterOrg = value;
    }

    /**
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

    /**
     * Gets the value of the livingPersonsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getLivingPersonsNumber() {
        return livingPersonsNumber;
    }

    /**
     * Sets the value of the livingPersonsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setLivingPersonsNumber(Byte value) {
        this.livingPersonsNumber = value;
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
     * Gets the value of the heatedArea property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHeatedArea() {
        return heatedArea;
    }

    /**
     * Sets the value of the heatedArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHeatedArea(BigDecimal value) {
        this.heatedArea = value;
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

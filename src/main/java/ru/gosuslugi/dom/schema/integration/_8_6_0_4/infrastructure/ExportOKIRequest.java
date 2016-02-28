
package ru.gosuslugi.dom.schema.integration._8_6_0_4.infrastructure;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_6_0.OKTMORefType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="OKIType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ExpiredManagement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OKTMORefType" minOccurs="0"/>
 *         &lt;element name="RSOOrganizationGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "okiType",
    "expiredManagement",
    "oktmo",
    "rsoOrganizationGUID"
})
@XmlRootElement(name = "exportOKIRequest")
public class ExportOKIRequest
    extends BaseType
{

    @XmlElement(name = "OKIType")
    protected List<NsiRef> okiType;
    @XmlElement(name = "ExpiredManagement")
    protected Boolean expiredManagement;
    @XmlElement(name = "OKTMO")
    protected OKTMORefType oktmo;
    @XmlElement(name = "RSOOrganizationGUID")
    protected String rsoOrganizationGUID;

    /**
     * Gets the value of the okiType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the okiType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOKIType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NsiRef }
     * 
     * 
     */
    public List<NsiRef> getOKIType() {
        if (okiType == null) {
            okiType = new ArrayList<NsiRef>();
        }
        return this.okiType;
    }

    /**
     * Gets the value of the expiredManagement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExpiredManagement() {
        return expiredManagement;
    }

    /**
     * Sets the value of the expiredManagement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpiredManagement(Boolean value) {
        this.expiredManagement = value;
    }

    /**
     * Gets the value of the oktmo property.
     * 
     * @return
     *     possible object is
     *     {@link OKTMORefType }
     *     
     */
    public OKTMORefType getOKTMO() {
        return oktmo;
    }

    /**
     * Sets the value of the oktmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OKTMORefType }
     *     
     */
    public void setOKTMO(OKTMORefType value) {
        this.oktmo = value;
    }

    /**
     * Gets the value of the rsoOrganizationGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRSOOrganizationGUID() {
        return rsoOrganizationGUID;
    }

    /**
     * Sets the value of the rsoOrganizationGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRSOOrganizationGUID(String value) {
        this.rsoOrganizationGUID = value;
    }

}

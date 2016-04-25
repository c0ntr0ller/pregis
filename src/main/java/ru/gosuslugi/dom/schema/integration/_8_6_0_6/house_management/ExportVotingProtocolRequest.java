
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}BaseType">
 *       &lt;choice>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;element name="VotingProtocolGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType" maxOccurs="100"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fiasHouseGuid",
    "votingProtocolGUID"
})
@XmlRootElement(name = "exportVotingProtocolRequest")
public class ExportVotingProtocolRequest
    extends BaseType
{

    @XmlElement(name = "FIASHouseGuid")
    protected String fiasHouseGuid;
    @XmlElement(name = "VotingProtocolGUID")
    protected List<String> votingProtocolGUID;

    /**
     * Gets the value of the fiasHouseGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIASHouseGuid() {
        return fiasHouseGuid;
    }

    /**
     * Sets the value of the fiasHouseGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIASHouseGuid(String value) {
        this.fiasHouseGuid = value;
    }

    /**
     * Gets the value of the votingProtocolGUID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the votingProtocolGUID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVotingProtocolGUID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getVotingProtocolGUID() {
        if (votingProtocolGUID == null) {
            votingProtocolGUID = new ArrayList<String>();
        }
        return this.votingProtocolGUID;
    }

}
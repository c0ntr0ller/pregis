
package ru.gosuslugi.dom.schema.integration.services.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.BaseType;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}BaseType">
 *       &lt;choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ErrorMessage"/>
 *         &lt;element name="Contract" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}exportSupplyResourceContractResultType" maxOccurs="unbounded"/>
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
    "errorMessage",
    "contract"
})
@XmlRootElement(name = "exportSupplyResourceContractResult")
public class ExportSupplyResourceContractResult
    extends BaseType
{

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "Contract")
    protected List<ExportSupplyResourceContractResultType> contract;

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageType }
     *     
     */
    public ErrorMessageType getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageType }
     *     
     */
    public void setErrorMessage(ErrorMessageType value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the contract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportSupplyResourceContractResultType }
     * 
     * 
     */
    public List<ExportSupplyResourceContractResultType> getContract() {
        if (contract == null) {
            contract = new ArrayList<ExportSupplyResourceContractResultType>();
        }
        return this.contract;
    }

}

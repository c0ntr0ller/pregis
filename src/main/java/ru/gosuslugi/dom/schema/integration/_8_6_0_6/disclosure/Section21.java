
package ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WorkOrService" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *                   &lt;element name="WorkCost" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
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
@XmlType(name = "", propOrder = {
    "workOrService"
})
@XmlRootElement(name = "Section2_1")
public class Section21 {

    @XmlElement(name = "WorkOrService", required = true)
    protected List<Section21 .WorkOrService> workOrService;

    /**
     * Gets the value of the workOrService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workOrService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkOrService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section21 .WorkOrService }
     * 
     * 
     */
    public List<Section21 .WorkOrService> getWorkOrService() {
        if (workOrService == null) {
            workOrService = new ArrayList<Section21 .WorkOrService>();
        }
        return this.workOrService;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="WorkType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
     *         &lt;element name="WorkCost" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyPositiveType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "workType",
        "workCost"
    })
    public static class WorkOrService {

        @XmlElement(name = "WorkType", required = true)
        protected NsiRef workType;
        @XmlElement(name = "WorkCost", required = true)
        protected BigDecimal workCost;

        /**
         * Gets the value of the workType property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getWorkType() {
            return workType;
        }

        /**
         * Sets the value of the workType property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setWorkType(NsiRef value) {
            this.workType = value;
        }

        /**
         * Gets the value of the workCost property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getWorkCost() {
            return workCost;
        }

        /**
         * Sets the value of the workCost property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setWorkCost(BigDecimal value) {
            this.workCost = value;
        }

    }

}

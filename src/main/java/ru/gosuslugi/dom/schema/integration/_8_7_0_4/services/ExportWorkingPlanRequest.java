
package ru.gosuslugi.dom.schema.integration._8_7_0_4.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="work" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkListGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Year"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "work"
})
@XmlRootElement(name = "exportWorkingPlanRequest")
public class ExportWorkingPlanRequest
    extends BaseType
{

    @XmlElement(required = true)
    protected List<ExportWorkingPlanRequest.Work> work;

    /**
     * Gets the value of the work property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the work property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWork().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportWorkingPlanRequest.Work }
     * 
     * 
     */
    public List<ExportWorkingPlanRequest.Work> getWork() {
        if (work == null) {
            work = new ArrayList<ExportWorkingPlanRequest.Work>();
        }
        return this.work;
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
     *         &lt;element name="WorkListGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Year"/>
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
        "workListGUID",
        "year"
    })
    public static class Work {

        @XmlElement(name = "WorkListGUID", required = true)
        protected String workListGUID;
        @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
        protected short year;

        /**
         * Gets the value of the workListGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWorkListGUID() {
            return workListGUID;
        }

        /**
         * Sets the value of the workListGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWorkListGUID(String value) {
            this.workListGUID = value;
        }

        /**
         * Год в рамках периода перечня
         * 
         */
        public short getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         */
        public void setYear(short value) {
            this.year = value;
        }

    }

}

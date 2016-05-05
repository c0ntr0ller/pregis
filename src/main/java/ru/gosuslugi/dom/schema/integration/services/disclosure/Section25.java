
package ru.gosuslugi.dom.schema.integration.services.disclosure;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="OwnersMeeting" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Number">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String255Type">
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Decision">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="1"/>
 *                         &lt;maxLength value="255"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "ownersMeeting"
})
@XmlRootElement(name = "Section2_5")
public class Section25 {

    @XmlElement(name = "OwnersMeeting", required = true)
    protected List<Section25 .OwnersMeeting> ownersMeeting;

    /**
     * Gets the value of the ownersMeeting property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownersMeeting property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnersMeeting().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section25 .OwnersMeeting }
     * 
     * 
     */
    public List<Section25 .OwnersMeeting> getOwnersMeeting() {
        if (ownersMeeting == null) {
            ownersMeeting = new ArrayList<Section25 .OwnersMeeting>();
        }
        return this.ownersMeeting;
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
     *         &lt;element name="Number">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String255Type">
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Decision">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="255"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "number",
        "decision",
        "date"
    })
    public static class OwnersMeeting {

        @XmlElement(name = "Number", required = true)
        protected String number;
        @XmlElement(name = "Decision", required = true)
        protected String decision;
        @XmlElement(name = "Date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar date;

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNumber(String value) {
            this.number = value;
        }

        /**
         * Gets the value of the decision property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDecision() {
            return decision;
        }

        /**
         * Sets the value of the decision property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDecision(String value) {
            this.decision = value;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDate(XMLGregorianCalendar value) {
            this.date = value;
        }

    }

}

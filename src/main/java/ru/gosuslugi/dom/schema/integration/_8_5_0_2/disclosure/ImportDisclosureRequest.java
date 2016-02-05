
package ru.gosuslugi.dom.schema.integration._8_5_0_2.disclosure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}BaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
 *         &lt;element name="Section1">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_1"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_2"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_3" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section2"/>
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
    "transportGUID",
    "section1",
    "section2"
})
@XmlRootElement(name = "importDisclosureRequest")
public class ImportDisclosureRequest
    extends BaseType
{

    @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
    protected String transportGUID;
    @XmlElement(name = "Section1", required = true)
    protected ImportDisclosureRequest.Section1 section1;
    @XmlElement(name = "Section2", required = true)
    protected Section2 section2;

    /**
     * Gets the value of the transportGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransportGUID() {
        return transportGUID;
    }

    /**
     * Sets the value of the transportGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransportGUID(String value) {
        this.transportGUID = value;
    }

    /**
     * Gets the value of the section1 property.
     * 
     * @return
     *     possible object is
     *     {@link ImportDisclosureRequest.Section1 }
     *     
     */
    public ImportDisclosureRequest.Section1 getSection1() {
        return section1;
    }

    /**
     * Sets the value of the section1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportDisclosureRequest.Section1 }
     *     
     */
    public void setSection1(ImportDisclosureRequest.Section1 value) {
        this.section1 = value;
    }

    /**
     * Gets the value of the section2 property.
     * 
     * @return
     *     possible object is
     *     {@link Section2 }
     *     
     */
    public Section2 getSection2() {
        return section2;
    }

    /**
     * Sets the value of the section2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section2 }
     *     
     */
    public void setSection2(Section2 value) {
        this.section2 = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_1"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_2"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}Section1_3" minOccurs="0"/>
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
        "section11",
        "section12",
        "section13"
    })
    public static class Section1 {

        @XmlElement(name = "Section1_1", required = true)
        protected Section11 section11;
        @XmlElement(name = "Section1_2", required = true)
        protected Section12 section12;
        @XmlElement(name = "Section1_3")
        protected Section13 section13;

        /**
         * Gets the value of the section11 property.
         * 
         * @return
         *     possible object is
         *     {@link Section11 }
         *     
         */
        public Section11 getSection11() {
            return section11;
        }

        /**
         * Sets the value of the section11 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section11 }
         *     
         */
        public void setSection11(Section11 value) {
            this.section11 = value;
        }

        /**
         * Gets the value of the section12 property.
         * 
         * @return
         *     possible object is
         *     {@link Section12 }
         *     
         */
        public Section12 getSection12() {
            return section12;
        }

        /**
         * Sets the value of the section12 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section12 }
         *     
         */
        public void setSection12(Section12 value) {
            this.section12 = value;
        }

        /**
         * Gets the value of the section13 property.
         * 
         * @return
         *     possible object is
         *     {@link Section13 }
         *     
         */
        public Section13 getSection13() {
            return section13;
        }

        /**
         * Sets the value of the section13 property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section13 }
         *     
         */
        public void setSection13(Section13 value) {
            this.section13 = value;
        }

    }

}

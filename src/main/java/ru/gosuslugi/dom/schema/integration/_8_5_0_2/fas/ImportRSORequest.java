
package ru.gosuslugi.dom.schema.integration._8_5_0_2.fas;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_5_0.OKTMORefType;


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
 *         &lt;element name="RSO" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
 *                   &lt;element name="ActualDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;choice>
 *                     &lt;element name="RSOActualDATA">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;choice>
 *                                 &lt;element name="Legal">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
 *                                           &lt;element name="Name">
 *                                             &lt;simpleType>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                 &lt;minLength value="1"/>
 *                                                 &lt;maxLength value="255"/>
 *                                               &lt;/restriction>
 *                                             &lt;/simpleType>
 *                                           &lt;/element>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP" minOccurs="0"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                                 &lt;element name="Subsidiary">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
 *                                           &lt;element name="Name">
 *                                             &lt;simpleType>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                 &lt;minLength value="1"/>
 *                                                 &lt;maxLength value="255"/>
 *                                               &lt;/restriction>
 *                                             &lt;/simpleType>
 *                                           &lt;/element>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                                 &lt;element name="Entps">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
 *                                           &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                           &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                           &lt;element name="Patronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                               &lt;/choice>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}INN"/>
 *                               &lt;element name="ResourseType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/fas/}ResourseType" maxOccurs="unbounded"/>
 *                               &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OKTMORefType" maxOccurs="unbounded"/>
 *                               &lt;element name="RegistryOfNaturalMonopolies">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="Number">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="50"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="RegistryGuaranteeingSuppliers">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="Number">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="50"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="RemoveRSO">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;choice>
 *                               &lt;element name="Legal">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="Subsidiary">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="Entps">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/choice>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
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
    "rso"
})
@XmlRootElement(name = "importRSORequest")
public class ImportRSORequest
    extends BaseType
{

    @XmlElement(name = "RSO", required = true)
    protected List<ImportRSORequest.RSO> rso;

    /**
     * Gets the value of the rso property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rso property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRSO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportRSORequest.RSO }
     * 
     * 
     */
    public List<ImportRSORequest.RSO> getRSO() {
        if (rso == null) {
            rso = new ArrayList<ImportRSORequest.RSO>();
        }
        return this.rso;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}TransportGUID"/>
     *         &lt;element name="ActualDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;choice>
     *           &lt;element name="RSOActualDATA">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;choice>
     *                       &lt;element name="Legal">
     *                         &lt;complexType>
     *                           &lt;complexContent>
     *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                               &lt;sequence>
     *                                 &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
     *                                 &lt;element name="Name">
     *                                   &lt;simpleType>
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                       &lt;minLength value="1"/>
     *                                       &lt;maxLength value="255"/>
     *                                     &lt;/restriction>
     *                                   &lt;/simpleType>
     *                                 &lt;/element>
     *                                 &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP" minOccurs="0"/>
     *                               &lt;/sequence>
     *                             &lt;/restriction>
     *                           &lt;/complexContent>
     *                         &lt;/complexType>
     *                       &lt;/element>
     *                       &lt;element name="Subsidiary">
     *                         &lt;complexType>
     *                           &lt;complexContent>
     *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                               &lt;sequence>
     *                                 &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
     *                                 &lt;element name="Name">
     *                                   &lt;simpleType>
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                       &lt;minLength value="1"/>
     *                                       &lt;maxLength value="255"/>
     *                                     &lt;/restriction>
     *                                   &lt;/simpleType>
     *                                 &lt;/element>
     *                                 &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
     *                               &lt;/sequence>
     *                             &lt;/restriction>
     *                           &lt;/complexContent>
     *                         &lt;/complexType>
     *                       &lt;/element>
     *                       &lt;element name="Entps">
     *                         &lt;complexType>
     *                           &lt;complexContent>
     *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                               &lt;sequence>
     *                                 &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
     *                                 &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                 &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                 &lt;element name="Patronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                               &lt;/sequence>
     *                             &lt;/restriction>
     *                           &lt;/complexContent>
     *                         &lt;/complexType>
     *                       &lt;/element>
     *                     &lt;/choice>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}INN"/>
     *                     &lt;element name="ResourseType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/fas/}ResourseType" maxOccurs="unbounded"/>
     *                     &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OKTMORefType" maxOccurs="unbounded"/>
     *                     &lt;element name="RegistryOfNaturalMonopolies">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="Number">
     *                                 &lt;simpleType>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                     &lt;minLength value="1"/>
     *                                     &lt;maxLength value="50"/>
     *                                   &lt;/restriction>
     *                                 &lt;/simpleType>
     *                               &lt;/element>
     *                               &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="RegistryGuaranteeingSuppliers">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="Number">
     *                                 &lt;simpleType>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                     &lt;minLength value="1"/>
     *                                     &lt;maxLength value="50"/>
     *                                   &lt;/restriction>
     *                                 &lt;/simpleType>
     *                               &lt;/element>
     *                               &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="RemoveRSO">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;choice>
     *                     &lt;element name="Legal">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="Subsidiary">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="Entps">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/choice>
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
        "transportGUID",
        "actualDate",
        "rsoActualDATA",
        "removeRSO"
    })
    public static class RSO {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ActualDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar actualDate;
        @XmlElement(name = "RSOActualDATA")
        protected ImportRSORequest.RSO.RSOActualDATA rsoActualDATA;
        @XmlElement(name = "RemoveRSO")
        protected ImportRSORequest.RSO.RemoveRSO removeRSO;

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
         * Gets the value of the actualDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getActualDate() {
            return actualDate;
        }

        /**
         * Sets the value of the actualDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setActualDate(XMLGregorianCalendar value) {
            this.actualDate = value;
        }

        /**
         * Gets the value of the rsoActualDATA property.
         * 
         * @return
         *     possible object is
         *     {@link ImportRSORequest.RSO.RSOActualDATA }
         *     
         */
        public ImportRSORequest.RSO.RSOActualDATA getRSOActualDATA() {
            return rsoActualDATA;
        }

        /**
         * Sets the value of the rsoActualDATA property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportRSORequest.RSO.RSOActualDATA }
         *     
         */
        public void setRSOActualDATA(ImportRSORequest.RSO.RSOActualDATA value) {
            this.rsoActualDATA = value;
        }

        /**
         * Gets the value of the removeRSO property.
         * 
         * @return
         *     possible object is
         *     {@link ImportRSORequest.RSO.RemoveRSO }
         *     
         */
        public ImportRSORequest.RSO.RemoveRSO getRemoveRSO() {
            return removeRSO;
        }

        /**
         * Sets the value of the removeRSO property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportRSORequest.RSO.RemoveRSO }
         *     
         */
        public void setRemoveRSO(ImportRSORequest.RSO.RemoveRSO value) {
            this.removeRSO = value;
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
         *         &lt;choice>
         *           &lt;element name="Legal">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
         *                     &lt;element name="Name">
         *                       &lt;simpleType>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                           &lt;minLength value="1"/>
         *                           &lt;maxLength value="255"/>
         *                         &lt;/restriction>
         *                       &lt;/simpleType>
         *                     &lt;/element>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP" minOccurs="0"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="Subsidiary">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
         *                     &lt;element name="Name">
         *                       &lt;simpleType>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                           &lt;minLength value="1"/>
         *                           &lt;maxLength value="255"/>
         *                         &lt;/restriction>
         *                       &lt;/simpleType>
         *                     &lt;/element>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="Entps">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
         *                     &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                     &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                     &lt;element name="Patronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *         &lt;/choice>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}INN"/>
         *         &lt;element name="ResourseType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/fas/}ResourseType" maxOccurs="unbounded"/>
         *         &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OKTMORefType" maxOccurs="unbounded"/>
         *         &lt;element name="RegistryOfNaturalMonopolies">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Number">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="50"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="RegistryGuaranteeingSuppliers">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Number">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="50"/>
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
            "legal",
            "subsidiary",
            "entps",
            "inn",
            "resourseType",
            "oktmo",
            "registryOfNaturalMonopolies",
            "registryGuaranteeingSuppliers"
        })
        public static class RSOActualDATA {

            @XmlElement(name = "Legal")
            protected ImportRSORequest.RSO.RSOActualDATA.Legal legal;
            @XmlElement(name = "Subsidiary")
            protected ImportRSORequest.RSO.RSOActualDATA.Subsidiary subsidiary;
            @XmlElement(name = "Entps")
            protected ImportRSORequest.RSO.RSOActualDATA.Entps entps;
            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
            protected String inn;
            @XmlElement(name = "ResourseType", required = true)
            protected List<String> resourseType;
            @XmlElement(name = "OKTMO", required = true)
            protected List<OKTMORefType> oktmo;
            @XmlElement(name = "RegistryOfNaturalMonopolies", required = true)
            protected ImportRSORequest.RSO.RSOActualDATA.RegistryOfNaturalMonopolies registryOfNaturalMonopolies;
            @XmlElement(name = "RegistryGuaranteeingSuppliers", required = true)
            protected ImportRSORequest.RSO.RSOActualDATA.RegistryGuaranteeingSuppliers registryGuaranteeingSuppliers;

            /**
             * Gets the value of the legal property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Legal }
             *     
             */
            public ImportRSORequest.RSO.RSOActualDATA.Legal getLegal() {
                return legal;
            }

            /**
             * Sets the value of the legal property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Legal }
             *     
             */
            public void setLegal(ImportRSORequest.RSO.RSOActualDATA.Legal value) {
                this.legal = value;
            }

            /**
             * Gets the value of the subsidiary property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Subsidiary }
             *     
             */
            public ImportRSORequest.RSO.RSOActualDATA.Subsidiary getSubsidiary() {
                return subsidiary;
            }

            /**
             * Sets the value of the subsidiary property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Subsidiary }
             *     
             */
            public void setSubsidiary(ImportRSORequest.RSO.RSOActualDATA.Subsidiary value) {
                this.subsidiary = value;
            }

            /**
             * Gets the value of the entps property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Entps }
             *     
             */
            public ImportRSORequest.RSO.RSOActualDATA.Entps getEntps() {
                return entps;
            }

            /**
             * Sets the value of the entps property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.Entps }
             *     
             */
            public void setEntps(ImportRSORequest.RSO.RSOActualDATA.Entps value) {
                this.entps = value;
            }

            /**
             * Gets the value of the inn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getINN() {
                return inn;
            }

            /**
             * Sets the value of the inn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setINN(String value) {
                this.inn = value;
            }

            /**
             * Gets the value of the resourseType property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the resourseType property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getResourseType().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getResourseType() {
                if (resourseType == null) {
                    resourseType = new ArrayList<String>();
                }
                return this.resourseType;
            }

            /**
             * Gets the value of the oktmo property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the oktmo property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getOKTMO().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link OKTMORefType }
             * 
             * 
             */
            public List<OKTMORefType> getOKTMO() {
                if (oktmo == null) {
                    oktmo = new ArrayList<OKTMORefType>();
                }
                return this.oktmo;
            }

            /**
             * Gets the value of the registryOfNaturalMonopolies property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.RegistryOfNaturalMonopolies }
             *     
             */
            public ImportRSORequest.RSO.RSOActualDATA.RegistryOfNaturalMonopolies getRegistryOfNaturalMonopolies() {
                return registryOfNaturalMonopolies;
            }

            /**
             * Sets the value of the registryOfNaturalMonopolies property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.RegistryOfNaturalMonopolies }
             *     
             */
            public void setRegistryOfNaturalMonopolies(ImportRSORequest.RSO.RSOActualDATA.RegistryOfNaturalMonopolies value) {
                this.registryOfNaturalMonopolies = value;
            }

            /**
             * Gets the value of the registryGuaranteeingSuppliers property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.RegistryGuaranteeingSuppliers }
             *     
             */
            public ImportRSORequest.RSO.RSOActualDATA.RegistryGuaranteeingSuppliers getRegistryGuaranteeingSuppliers() {
                return registryGuaranteeingSuppliers;
            }

            /**
             * Sets the value of the registryGuaranteeingSuppliers property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RSOActualDATA.RegistryGuaranteeingSuppliers }
             *     
             */
            public void setRegistryGuaranteeingSuppliers(ImportRSORequest.RSO.RSOActualDATA.RegistryGuaranteeingSuppliers value) {
                this.registryGuaranteeingSuppliers = value;
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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
             *         &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="Patronymic" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
                "ogrnip",
                "surname",
                "firstName",
                "patronymic"
            })
            public static class Entps {

                @XmlElement(name = "OGRNIP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrnip;
                @XmlElement(name = "Surname", required = true)
                protected String surname;
                @XmlElement(name = "FirstName", required = true)
                protected String firstName;
                @XmlElement(name = "Patronymic")
                protected String patronymic;

                /**
                 * Gets the value of the ogrnip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRNIP() {
                    return ogrnip;
                }

                /**
                 * Sets the value of the ogrnip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRNIP(String value) {
                    this.ogrnip = value;
                }

                /**
                 * Gets the value of the surname property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSurname() {
                    return surname;
                }

                /**
                 * Sets the value of the surname property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSurname(String value) {
                    this.surname = value;
                }

                /**
                 * Gets the value of the firstName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFirstName() {
                    return firstName;
                }

                /**
                 * Sets the value of the firstName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFirstName(String value) {
                    this.firstName = value;
                }

                /**
                 * Gets the value of the patronymic property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPatronymic() {
                    return patronymic;
                }

                /**
                 * Sets the value of the patronymic property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPatronymic(String value) {
                    this.patronymic = value;
                }

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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
             *         &lt;element name="Name">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP" minOccurs="0"/>
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
                "ogrn",
                "name",
                "kpp"
            })
            public static class Legal {

                @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrn;
                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
                protected String kpp;

                /**
                 * Gets the value of the ogrn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRN() {
                    return ogrn;
                }

                /**
                 * Sets the value of the ogrn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRN(String value) {
                    this.ogrn = value;
                }

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the kpp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKPP() {
                    return kpp;
                }

                /**
                 * Sets the value of the kpp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKPP(String value) {
                    this.kpp = value;
                }

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
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="50"/>
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
                "date"
            })
            public static class RegistryGuaranteeingSuppliers {

                @XmlElement(name = "Number", required = true)
                protected String number;
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
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="50"/>
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
                "date"
            })
            public static class RegistryOfNaturalMonopolies {

                @XmlElement(name = "Number", required = true)
                protected String number;
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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
             *         &lt;element name="Name">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
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
                "ogrn",
                "name",
                "kpp"
            })
            public static class Subsidiary {

                @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrn;
                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String kpp;

                /**
                 * Gets the value of the ogrn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRN() {
                    return ogrn;
                }

                /**
                 * Sets the value of the ogrn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRN(String value) {
                    this.ogrn = value;
                }

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the kpp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKPP() {
                    return kpp;
                }

                /**
                 * Sets the value of the kpp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKPP(String value) {
                    this.kpp = value;
                }

            }

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
         *       &lt;choice>
         *         &lt;element name="Legal">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Subsidiary">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Entps">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
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
            "legal",
            "subsidiary",
            "entps"
        })
        public static class RemoveRSO {

            @XmlElement(name = "Legal")
            protected ImportRSORequest.RSO.RemoveRSO.Legal legal;
            @XmlElement(name = "Subsidiary")
            protected ImportRSORequest.RSO.RemoveRSO.Subsidiary subsidiary;
            @XmlElement(name = "Entps")
            protected ImportRSORequest.RSO.RemoveRSO.Entps entps;

            /**
             * Gets the value of the legal property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Legal }
             *     
             */
            public ImportRSORequest.RSO.RemoveRSO.Legal getLegal() {
                return legal;
            }

            /**
             * Sets the value of the legal property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Legal }
             *     
             */
            public void setLegal(ImportRSORequest.RSO.RemoveRSO.Legal value) {
                this.legal = value;
            }

            /**
             * Gets the value of the subsidiary property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Subsidiary }
             *     
             */
            public ImportRSORequest.RSO.RemoveRSO.Subsidiary getSubsidiary() {
                return subsidiary;
            }

            /**
             * Sets the value of the subsidiary property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Subsidiary }
             *     
             */
            public void setSubsidiary(ImportRSORequest.RSO.RemoveRSO.Subsidiary value) {
                this.subsidiary = value;
            }

            /**
             * Gets the value of the entps property.
             * 
             * @return
             *     possible object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Entps }
             *     
             */
            public ImportRSORequest.RSO.RemoveRSO.Entps getEntps() {
                return entps;
            }

            /**
             * Sets the value of the entps property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportRSORequest.RSO.RemoveRSO.Entps }
             *     
             */
            public void setEntps(ImportRSORequest.RSO.RemoveRSO.Entps value) {
                this.entps = value;
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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRNIP"/>
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
                "ogrnip"
            })
            public static class Entps {

                @XmlElement(name = "OGRNIP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrnip;

                /**
                 * Gets the value of the ogrnip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRNIP() {
                    return ogrnip;
                }

                /**
                 * Sets the value of the ogrnip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRNIP(String value) {
                    this.ogrnip = value;
                }

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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
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
                "ogrn"
            })
            public static class Legal {

                @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrn;

                /**
                 * Gets the value of the ogrn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRN() {
                    return ogrn;
                }

                /**
                 * Sets the value of the ogrn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRN(String value) {
                    this.ogrn = value;
                }

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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}OGRN"/>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}KPP"/>
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
                "ogrn",
                "kpp"
            })
            public static class Subsidiary {

                @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String ogrn;
                @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
                protected String kpp;

                /**
                 * Gets the value of the ogrn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOGRN() {
                    return ogrn;
                }

                /**
                 * Sets the value of the ogrn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOGRN(String value) {
                    this.ogrn = value;
                }

                /**
                 * Gets the value of the kpp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKPP() {
                    return kpp;
                }

                /**
                 * Sets the value of the kpp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKPP(String value) {
                    this.kpp = value;
                }

            }

        }

    }

}

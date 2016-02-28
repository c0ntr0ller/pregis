
package ru.gosuslugi.dom.schema.integration._8_6_0_4.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
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
 *         &lt;element name="MunicipalService" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Kind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
 *                   &lt;element name="TypeOfServiceProvision" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
 *                   &lt;element name="ConsumptionTariff" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}SmallMoneyPositiveType"/>
 *                   &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="TariffsDescription" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}String1500Type" minOccurs="0"/>
 *                   &lt;element name="LivingPremisesMSConsumptionRatio">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Ratio">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="10"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="OveralMSConsumptionRatio">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Ratio">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;totalDigits value="10"/>
 *                                   &lt;fractionDigits value="2"/>
 *                                   &lt;minInclusive value="0"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Supplier">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type"/>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
 *                             &lt;choice>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRN"/>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRNIP"/>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SupplyContract">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="Number">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="TariffsLegalAct">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="Number">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="AgencyName">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ConsumptionLegalAct" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="Number">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="AgencyName">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "municipalService"
})
@XmlRootElement(name = "Section2_2")
public class Section22 {

    @XmlElement(name = "MunicipalService", required = true)
    protected List<Section22 .MunicipalService> municipalService;

    /**
     * Gets the value of the municipalService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the municipalService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMunicipalService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section22 .MunicipalService }
     * 
     * 
     */
    public List<Section22 .MunicipalService> getMunicipalService() {
        if (municipalService == null) {
            municipalService = new ArrayList<Section22 .MunicipalService>();
        }
        return this.municipalService;
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
     *         &lt;element name="Kind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
     *         &lt;element name="TypeOfServiceProvision" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
     *         &lt;element name="ConsumptionTariff" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}SmallMoneyPositiveType"/>
     *         &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="TariffsDescription" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}String1500Type" minOccurs="0"/>
     *         &lt;element name="LivingPremisesMSConsumptionRatio">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Ratio">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="10"/>
     *                         &lt;fractionDigits value="2"/>
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="OveralMSConsumptionRatio">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Ratio">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;totalDigits value="10"/>
     *                         &lt;fractionDigits value="2"/>
     *                         &lt;minInclusive value="0"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Supplier">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type"/>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
     *                   &lt;choice>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRN"/>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRNIP"/>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SupplyContract">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="Number">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="TariffsLegalAct">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="Number">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="AgencyName">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ConsumptionLegalAct" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="Number">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="AgencyName">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
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
        "kind",
        "typeOfServiceProvision",
        "consumptionTariff",
        "beginDate",
        "tariffsDescription",
        "livingPremisesMSConsumptionRatio",
        "overalMSConsumptionRatio",
        "supplier",
        "supplyContract",
        "tariffsLegalAct",
        "consumptionLegalAct"
    })
    public static class MunicipalService {

        @XmlElement(name = "Kind", required = true)
        protected NsiRef kind;
        @XmlElement(name = "TypeOfServiceProvision", required = true)
        protected NsiRef typeOfServiceProvision;
        @XmlElement(name = "ConsumptionTariff", required = true)
        protected BigDecimal consumptionTariff;
        @XmlElement(name = "BeginDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar beginDate;
        @XmlElement(name = "TariffsDescription")
        protected String tariffsDescription;
        @XmlElement(name = "LivingPremisesMSConsumptionRatio", required = true)
        protected Section22 .MunicipalService.LivingPremisesMSConsumptionRatio livingPremisesMSConsumptionRatio;
        @XmlElement(name = "OveralMSConsumptionRatio", required = true)
        protected Section22 .MunicipalService.OveralMSConsumptionRatio overalMSConsumptionRatio;
        @XmlElement(name = "Supplier", required = true)
        protected Section22 .MunicipalService.Supplier supplier;
        @XmlElement(name = "SupplyContract", required = true)
        protected Section22 .MunicipalService.SupplyContract supplyContract;
        @XmlElement(name = "TariffsLegalAct", required = true)
        protected Section22 .MunicipalService.TariffsLegalAct tariffsLegalAct;
        @XmlElement(name = "ConsumptionLegalAct", required = true)
        protected List<Section22 .MunicipalService.ConsumptionLegalAct> consumptionLegalAct;

        /**
         * Gets the value of the kind property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getKind() {
            return kind;
        }

        /**
         * Sets the value of the kind property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setKind(NsiRef value) {
            this.kind = value;
        }

        /**
         * Gets the value of the typeOfServiceProvision property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getTypeOfServiceProvision() {
            return typeOfServiceProvision;
        }

        /**
         * Sets the value of the typeOfServiceProvision property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setTypeOfServiceProvision(NsiRef value) {
            this.typeOfServiceProvision = value;
        }

        /**
         * Gets the value of the consumptionTariff property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getConsumptionTariff() {
            return consumptionTariff;
        }

        /**
         * Sets the value of the consumptionTariff property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setConsumptionTariff(BigDecimal value) {
            this.consumptionTariff = value;
        }

        /**
         * Gets the value of the beginDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getBeginDate() {
            return beginDate;
        }

        /**
         * Sets the value of the beginDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setBeginDate(XMLGregorianCalendar value) {
            this.beginDate = value;
        }

        /**
         * Gets the value of the tariffsDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTariffsDescription() {
            return tariffsDescription;
        }

        /**
         * Sets the value of the tariffsDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTariffsDescription(String value) {
            this.tariffsDescription = value;
        }

        /**
         * Gets the value of the livingPremisesMSConsumptionRatio property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 .MunicipalService.LivingPremisesMSConsumptionRatio }
         *     
         */
        public Section22 .MunicipalService.LivingPremisesMSConsumptionRatio getLivingPremisesMSConsumptionRatio() {
            return livingPremisesMSConsumptionRatio;
        }

        /**
         * Sets the value of the livingPremisesMSConsumptionRatio property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 .MunicipalService.LivingPremisesMSConsumptionRatio }
         *     
         */
        public void setLivingPremisesMSConsumptionRatio(Section22 .MunicipalService.LivingPremisesMSConsumptionRatio value) {
            this.livingPremisesMSConsumptionRatio = value;
        }

        /**
         * Gets the value of the overalMSConsumptionRatio property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 .MunicipalService.OveralMSConsumptionRatio }
         *     
         */
        public Section22 .MunicipalService.OveralMSConsumptionRatio getOveralMSConsumptionRatio() {
            return overalMSConsumptionRatio;
        }

        /**
         * Sets the value of the overalMSConsumptionRatio property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 .MunicipalService.OveralMSConsumptionRatio }
         *     
         */
        public void setOveralMSConsumptionRatio(Section22 .MunicipalService.OveralMSConsumptionRatio value) {
            this.overalMSConsumptionRatio = value;
        }

        /**
         * Gets the value of the supplier property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 .MunicipalService.Supplier }
         *     
         */
        public Section22 .MunicipalService.Supplier getSupplier() {
            return supplier;
        }

        /**
         * Sets the value of the supplier property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 .MunicipalService.Supplier }
         *     
         */
        public void setSupplier(Section22 .MunicipalService.Supplier value) {
            this.supplier = value;
        }

        /**
         * Gets the value of the supplyContract property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 .MunicipalService.SupplyContract }
         *     
         */
        public Section22 .MunicipalService.SupplyContract getSupplyContract() {
            return supplyContract;
        }

        /**
         * Sets the value of the supplyContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 .MunicipalService.SupplyContract }
         *     
         */
        public void setSupplyContract(Section22 .MunicipalService.SupplyContract value) {
            this.supplyContract = value;
        }

        /**
         * Gets the value of the tariffsLegalAct property.
         * 
         * @return
         *     possible object is
         *     {@link Section22 .MunicipalService.TariffsLegalAct }
         *     
         */
        public Section22 .MunicipalService.TariffsLegalAct getTariffsLegalAct() {
            return tariffsLegalAct;
        }

        /**
         * Sets the value of the tariffsLegalAct property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section22 .MunicipalService.TariffsLegalAct }
         *     
         */
        public void setTariffsLegalAct(Section22 .MunicipalService.TariffsLegalAct value) {
            this.tariffsLegalAct = value;
        }

        /**
         * Gets the value of the consumptionLegalAct property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the consumptionLegalAct property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConsumptionLegalAct().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Section22 .MunicipalService.ConsumptionLegalAct }
         * 
         * 
         */
        public List<Section22 .MunicipalService.ConsumptionLegalAct> getConsumptionLegalAct() {
            if (consumptionLegalAct == null) {
                consumptionLegalAct = new ArrayList<Section22 .MunicipalService.ConsumptionLegalAct>();
            }
            return this.consumptionLegalAct;
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
         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="Number">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="AgencyName">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "date",
            "number",
            "agencyName"
        })
        public static class ConsumptionLegalAct {

            @XmlElement(name = "Date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            @XmlElement(name = "Number", required = true)
            protected String number;
            @XmlElement(name = "AgencyName", required = true)
            protected String agencyName;

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
             * Gets the value of the agencyName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAgencyName() {
                return agencyName;
            }

            /**
             * Sets the value of the agencyName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAgencyName(String value) {
                this.agencyName = value;
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
         *         &lt;element name="Ratio">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="10"/>
         *               &lt;fractionDigits value="2"/>
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            "ratio",
            "beginDate"
        })
        public static class LivingPremisesMSConsumptionRatio {

            @XmlElement(name = "Ratio", required = true)
            protected BigDecimal ratio;
            @XmlElement(name = "BeginDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar beginDate;

            /**
             * Gets the value of the ratio property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRatio() {
                return ratio;
            }

            /**
             * Sets the value of the ratio property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRatio(BigDecimal value) {
                this.ratio = value;
            }

            /**
             * Gets the value of the beginDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getBeginDate() {
                return beginDate;
            }

            /**
             * Sets the value of the beginDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setBeginDate(XMLGregorianCalendar value) {
                this.beginDate = value;
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
         *         &lt;element name="Ratio">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;totalDigits value="10"/>
         *               &lt;fractionDigits value="2"/>
         *               &lt;minInclusive value="0"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            "ratio",
            "beginDate"
        })
        public static class OveralMSConsumptionRatio {

            @XmlElement(name = "Ratio", required = true)
            protected BigDecimal ratio;
            @XmlElement(name = "BeginDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar beginDate;

            /**
             * Gets the value of the ratio property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getRatio() {
                return ratio;
            }

            /**
             * Sets the value of the ratio property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setRatio(BigDecimal value) {
                this.ratio = value;
            }

            /**
             * Gets the value of the beginDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getBeginDate() {
                return beginDate;
            }

            /**
             * Sets the value of the beginDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setBeginDate(XMLGregorianCalendar value) {
                this.beginDate = value;
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
         *         &lt;element name="Name" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type"/>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
         *         &lt;choice>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRN"/>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OGRNIP"/>
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
            "name",
            "inn",
            "ogrn",
            "ogrnip"
        })
        public static class Supplier {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
            protected String inn;
            @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
            protected String ogrn;
            @XmlElement(name = "OGRNIP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
            protected String ogrnip;

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
         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="Number">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "date",
            "number"
        })
        public static class SupplyContract {

            @XmlElement(name = "Date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            @XmlElement(name = "Number", required = true)
            protected String number;

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
         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="Number">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="AgencyName">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
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
            "date",
            "number",
            "agencyName"
        })
        public static class TariffsLegalAct {

            @XmlElement(name = "Date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            @XmlElement(name = "Number", required = true)
            protected String number;
            @XmlElement(name = "AgencyName", required = true)
            protected String agencyName;

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
             * Gets the value of the agencyName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAgencyName() {
                return agencyName;
            }

            /**
             * Sets the value of the agencyName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAgencyName(String value) {
                this.agencyName = value;
            }

        }

    }

}

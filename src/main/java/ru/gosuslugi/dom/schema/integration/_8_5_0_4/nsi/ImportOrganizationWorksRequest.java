
package ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi;

import java.util.ArrayList;
import java.util.List;
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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="ImportOrganizationWork" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/nsi/}ImportOrganizationWorkType" maxOccurs="1000" minOccurs="0"/>
 *         &lt;element name="RecoverOrganizationWork" maxOccurs="1000" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
 *                   &lt;element name="ElementGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *                   &lt;element name="HierarchyRecover" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DeleteOrganizationWork" maxOccurs="1000" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
 *                   &lt;element name="ElementGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
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
    "importOrganizationWork",
    "recoverOrganizationWork",
    "deleteOrganizationWork"
})
@XmlRootElement(name = "importOrganizationWorksRequest")
public class ImportOrganizationWorksRequest
    extends BaseType
{

    @XmlElement(name = "ImportOrganizationWork")
    protected List<ImportOrganizationWorkType> importOrganizationWork;
    @XmlElement(name = "RecoverOrganizationWork")
    protected List<ImportOrganizationWorksRequest.RecoverOrganizationWork> recoverOrganizationWork;
    @XmlElement(name = "DeleteOrganizationWork")
    protected List<ImportOrganizationWorksRequest.DeleteOrganizationWork> deleteOrganizationWork;

    /**
     * Gets the value of the importOrganizationWork property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importOrganizationWork property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportOrganizationWork().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportOrganizationWorkType }
     * 
     * 
     */
    public List<ImportOrganizationWorkType> getImportOrganizationWork() {
        if (importOrganizationWork == null) {
            importOrganizationWork = new ArrayList<ImportOrganizationWorkType>();
        }
        return this.importOrganizationWork;
    }

    /**
     * Gets the value of the recoverOrganizationWork property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recoverOrganizationWork property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecoverOrganizationWork().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportOrganizationWorksRequest.RecoverOrganizationWork }
     * 
     * 
     */
    public List<ImportOrganizationWorksRequest.RecoverOrganizationWork> getRecoverOrganizationWork() {
        if (recoverOrganizationWork == null) {
            recoverOrganizationWork = new ArrayList<ImportOrganizationWorksRequest.RecoverOrganizationWork>();
        }
        return this.recoverOrganizationWork;
    }

    /**
     * Gets the value of the deleteOrganizationWork property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deleteOrganizationWork property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeleteOrganizationWork().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportOrganizationWorksRequest.DeleteOrganizationWork }
     * 
     * 
     */
    public List<ImportOrganizationWorksRequest.DeleteOrganizationWork> getDeleteOrganizationWork() {
        if (deleteOrganizationWork == null) {
            deleteOrganizationWork = new ArrayList<ImportOrganizationWorksRequest.DeleteOrganizationWork>();
        }
        return this.deleteOrganizationWork;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
     *         &lt;element name="ElementGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
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
        "elementGuid"
    })
    public static class DeleteOrganizationWork {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ElementGuid", required = true)
        protected String elementGuid;

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
         * Gets the value of the elementGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getElementGuid() {
            return elementGuid;
        }

        /**
         * Sets the value of the elementGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setElementGuid(String value) {
            this.elementGuid = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
     *         &lt;element name="ElementGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
     *         &lt;element name="HierarchyRecover" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "elementGuid",
        "hierarchyRecover"
    })
    public static class RecoverOrganizationWork {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ElementGuid", required = true)
        protected String elementGuid;
        @XmlElement(name = "HierarchyRecover")
        protected Boolean hierarchyRecover;

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
         * Gets the value of the elementGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getElementGuid() {
            return elementGuid;
        }

        /**
         * Sets the value of the elementGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setElementGuid(String value) {
            this.elementGuid = value;
        }

        /**
         * Gets the value of the hierarchyRecover property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isHierarchyRecover() {
            return hierarchyRecover;
        }

        /**
         * Sets the value of the hierarchyRecover property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setHierarchyRecover(Boolean value) {
            this.hierarchyRecover = value;
        }

    }

}

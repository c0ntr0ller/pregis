
package ru.gosuslugi.dom.schema.integration.services.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Подъезд (для экспорта)
 * 
 * <p>Java class for EntranceExportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntranceExportType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}EntranceOMSType">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntranceExportType")
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.house_management.ExportHouseResultType.ApartmentHouse.Entrance.class
})
public class EntranceExportType
    extends EntranceOMSType
{


}

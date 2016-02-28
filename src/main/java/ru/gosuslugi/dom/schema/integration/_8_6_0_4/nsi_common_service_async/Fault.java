
package ru.gosuslugi.dom.schema.integration._8_6_0_4.nsi_common_service_async;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Fault", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/")
public class Fault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ru.gosuslugi.dom.schema.integration._8_6_0.Fault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public Fault(String message, ru.gosuslugi.dom.schema.integration._8_6_0.Fault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public Fault(String message, ru.gosuslugi.dom.schema.integration._8_6_0.Fault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ru.gosuslugi.dom.schema.integration._8_6_0.Fault
     */
    public ru.gosuslugi.dom.schema.integration._8_6_0.Fault getFaultInfo() {
        return faultInfo;
    }

}

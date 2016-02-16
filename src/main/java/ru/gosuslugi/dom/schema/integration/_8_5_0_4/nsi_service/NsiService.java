
package ru.gosuslugi.dom.schema.integration._8_5_0_4.nsi_service;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Сервис получения и импорта данных НСИ
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NsiService", targetNamespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/nsi-service/", wsdlLocation = "https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi")
@HandlerChain(file = "handler/ClientMessage_handler.xml")
public class NsiService
    extends Service
{

    private final static URL NSISERVICE_WSDL_LOCATION;
    private final static WebServiceException NSISERVICE_EXCEPTION;
    private final static QName NSISERVICE_QNAME = new QName("http://dom.gosuslugi.ru/schema/integration/8.5.0.4/nsi-service/", "NsiService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NSISERVICE_WSDL_LOCATION = url;
        NSISERVICE_EXCEPTION = e;
    }

    public NsiService() {
        super(__getWsdlLocation(), NSISERVICE_QNAME);
    }

    public NsiService(WebServiceFeature... features) {
        super(__getWsdlLocation(), NSISERVICE_QNAME, features);
    }

    public NsiService(URL wsdlLocation) {
        super(wsdlLocation, NSISERVICE_QNAME);
    }

    public NsiService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NSISERVICE_QNAME, features);
    }

    public NsiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NsiService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns NsiPortsType
     */
    @WebEndpoint(name = "NsiPort")
    public NsiPortsType getNsiPort() {
        return super.getPort(new QName("http://dom.gosuslugi.ru/schema/integration/8.5.0.4/nsi-service/", "NsiPort"), NsiPortsType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NsiPortsType
     */
    @WebEndpoint(name = "NsiPort")
    public NsiPortsType getNsiPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://dom.gosuslugi.ru/schema/integration/8.5.0.4/nsi-service/", "NsiPort"), NsiPortsType.class, features);
    }

    public static URL __getWsdlLocation() {
        if (NSISERVICE_EXCEPTION!= null) {
            throw NSISERVICE_EXCEPTION;
        }
        return NSISERVICE_WSDL_LOCATION;
    }

}

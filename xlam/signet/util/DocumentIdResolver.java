/**
 * Copyright 2004-2012 Crypto-Pro. All rights reserved.
 * Этот файл содержит информацию, являющуюся
 * собственностью компании Крипто-Про.
 *
 * Любая часть этого файла не может быть скопирована,
 * исправлена, переведена на другие языки,
 * локализована или модифицирована любым способом,
 * откомпилирована, передана по сети с или на
 * любую компьютерную систему без предварительного
 * заключения соглашения с компанией Крипто-Про.
 */
package ru.prog_matik.java.pregis.signet.util;

import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.CryptoPro.JCP.tools.JCPLogger;
import ru.CryptoPro.JCPxml.utility.XMLIdResolver;


/**
 * This resolver is used for resolving same-document URIs like URI="" of URI="#id".
 *
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-ReferenceProcessingModel">The Reference processing model in the XML Signature spec</A>
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-Same-Document">Same-Document URI-References in the XML Signature spec</A>
 * @see <A HREF="http://www.ietf.org/rfc/rfc2396.txt">Section 4.2 of RFC 2396</A>
 */
public class DocumentIdResolver extends ResourceResolverSpi {

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    /**
     * Method engineResolve
     *
     * @inheritDoc
     * @param uri
     * @param baseURI
     */
    @Override
    public XMLSignatureInput engineResolve(Attr uri, String baseURI)
            throws ResourceResolverException {

        String uriNodeValue = uri.getNodeValue();
        Document doc = uri.getOwnerElement().getOwnerDocument();

        Node selectedElem = null;
        if (uriNodeValue.equals("")) {
            /*
             * Identifies the node-set (minus any comment nodes) of the XML
             * resource containing the signature
             */
            JCPLogger.subTrace("ResolverFragment with empty URI (means complete document)");
            selectedElem = doc;

        } else {
            /*
             * URI="#chapter1"
             * Identifies a node-set containing the element with ID attribute
             * value 'chapter1' of the XML resource containing the signature.
             * XML Signature (and its applications) modify this node-set to
             * include the element plus all descendants including namespaces and
             * attributes -- but not comments.
             */
            String id = uriNodeValue.substring(1);

            // Fixed this!
            selectedElem = XMLIdResolver.getElementById(doc, id);

            if (selectedElem == null) {
                Object exArgs[] = { id };
                throw new ResourceResolverException(
                        "signature.Verification.MissingID", exArgs, uri, baseURI
                );
            }
            /* xmlsec >= 1.5.0 , но < 2.0 (в нем другой интерфейс ResourceResolverSpi)
            if (secureValidation) {

                Element start = uri.getOwnerDocument().getDocumentElement();

                if (!XMLUtils.protectAgainstWrappingAttack(start, id)) {
                    Object exArgs[] = { id };
                    throw new ResourceResolverException(
                            "signature.Verification.MultipleIDs", exArgs, uri, baseURI
                    );
                }
            }
            */
            JCPLogger.subTrace("Try to catch an Element with ID " + id + " and Element was " + selectedElem);
        }

        XMLSignatureInput result = new XMLSignatureInput(selectedElem);
        result.setExcludeComments(true);

        result.setMIMEType("text/xml");

        if (baseURI != null && baseURI.length() > 0) {
            result.setSourceURI(baseURI.concat(uri.getNodeValue()));
        } else {
            result.setSourceURI(uri.getNodeValue());
        }

        return result;
    }

    /**
     * Method engineCanResolve
     * @inheritDoc
     * @param uri
     * @param baseURI
     */
    @Override
    public boolean engineCanResolve(Attr uri, String baseURI) {

        if (uri == null) {
            JCPLogger.subTrace("Quick fail for null uri");
            return false;
        }

        String uriNodeValue = uri.getNodeValue();
        if (uriNodeValue.equals("") || ((uriNodeValue.charAt(0) == '#')
            && !((uriNodeValue.charAt(1) == 'x') && uriNodeValue.startsWith("#xpointer("))) ) {
            JCPLogger.subTrace("State I can resolve reference: \"" + uriNodeValue + "\"");
            return true;
        }

        JCPLogger.subTrace("Do not seem to be able to resolve reference: \"" + uriNodeValue + "\"");
        return false;
    }

}


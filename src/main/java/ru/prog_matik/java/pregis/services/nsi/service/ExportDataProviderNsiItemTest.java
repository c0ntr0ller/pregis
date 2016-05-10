package ru.prog_matik.java.pregis.services.nsi.service;


import org.junit.Test;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.prog_matik.java.pregis.other.OtherFormat;

/**
 * Created by andryha on 10.05.2016.
 */
public class ExportDataProviderNsiItemTest {
    @Test
    public void callExportDataProviderNsiItem() throws Exception {
        RequestHeader requestHeader = OtherFormat.getRequestHeader();
        System.out.println(requestHeader.getSenderID());
        System.out.println(requestHeader.getDate());
        System.out.println(requestHeader.getMessageGUID());
    }

}
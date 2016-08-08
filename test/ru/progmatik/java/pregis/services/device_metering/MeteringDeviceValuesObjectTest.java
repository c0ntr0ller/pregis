package ru.progmatik.java.pregis.services.device_metering;

import org.junit.Test;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Класс, тестирует eqauls & hashcode.
 */
public class MeteringDeviceValuesObjectTest {
    @Test
    public void equalsTest() throws Exception {
        ReferenceNSI nsi = new ReferenceNSI(new AnswerProcessing(new ClientService()));
        SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
        MeteringDeviceValuesObject one = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"),
                nsi.getNsiRef("2", "Электрическая энергия"));

        MeteringDeviceValuesObject two = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"),
                nsi.getNsiRef("2", "Электрическая энергия"));

        MeteringDeviceValuesObject three = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                new BigDecimal("127.1201"),
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"),
                nsi.getNsiRef("2", "Электрическая энергия"));
        assertEquals(one, two);
        assertNotEquals(three, two);
    }

}
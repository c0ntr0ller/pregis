package ru.progmatik.java.pregis.services.device_metering;

import org.junit.Test;

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
        SimpleDateFormat dateFromSQL = new SimpleDateFormat("yyyy-MM-dd");
        MeteringDeviceValuesObject one = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"));

        MeteringDeviceValuesObject two = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"));

        MeteringDeviceValuesObject three = new MeteringDeviceValuesObject("dbd3f20a-b521-49ff-af0e-be7dac00fe9c",
                new BigDecimal("127.1201"),
                new BigDecimal("127.1201"),
                new BigDecimal("127.1201"),
                dateFromSQL.parse("2016-08-05"));
        assertEquals(one, two);
        assertNotEquals(three, two);
    }

}
package ru.progmatik.java.pregis.other;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * класс-утилита для преобразования данных из БД в json-like формате в мапу с данными
 */
public class UtilString2Map {
    /**
     * метод разбирает строку с данными в виде element_name=element_value и превращает её в мапу с именованными параметрами
     * @param data - данные в виде element_name=element_value|element_name=element_value
     * @param elementDivider - строка-разделитель для разных элементов
     * @param dataDivider - строка-разделитель данных в элементе
     * @return
     */
    private static Map<String, String> splitString2Map(final String data, final String elementDivider, final String  dataDivider){
        Map<String, String> result = new HashMap<>();

        for (String element: data.split(Pattern.quote(elementDivider))){
            String[] elementData = element.split(Pattern.quote(dataDivider));

            if (elementData.length > 1){
                result.put(elementData[0], elementData[1]);
            }
        }
        return result;
    }

    /**
     * дефолтная версия splitString2Map
     * @param data
     * @return
     */
    private static Map<String, String> splitString2Map(final String data){
        return splitString2Map(data, "|", "=");
    }
}

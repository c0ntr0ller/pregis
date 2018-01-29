package ru.progmatik.java.pregis.other;

import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.Organization;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, содержащий набор организаций, по которым работает приложение. Если просто УО - то тут одна организация,
 * если РКЦ - все организации из ГРАД, занесенные в таблицу EX_GIS_ORGANIZATIONS как его подчиненные
 */
public class OrgsSettings {
    private static Integer mainOrg;
    private static Map<Integer, Organization> orgsMap;
    private static OrgsSettings orgsSettings;

    public static OrgsSettings instance() throws PreGISException {
        if(orgsSettings == null) {
            orgsSettings = new OrgsSettings();
        }
        return orgsSettings;
    }

    private OrgsSettings() throws PreGISException {
        orgsMap = new HashMap<>();
        mainOrg = ResourcesUtil.instance().getCompanyGradId();
    }

    public static String getOrgPPAGUID() {
        return orgsMap.get(mainOrg).getOrgPPAGUID();
    }

    public static Integer getMainOrg() {
        return mainOrg;
    }

    public static void setMainOrg(Integer mainOrg) throws PreGISException {
        if(orgsSettings == null) orgsSettings = new OrgsSettings();
        OrgsSettings.mainOrg = mainOrg;
    }

    public static Map<Integer, Organization> getOrgsMap() {
        return orgsMap;
    }

    public static void setOrgsMap(Map<Integer, Organization> orgsMap) throws PreGISException {
        if(orgsSettings == null) orgsSettings = new OrgsSettings();
        OrgsSettings.orgsMap.clear();
        OrgsSettings.orgsMap.putAll(orgsMap);
        setMainOrg(ResourcesUtil.instance().getCompanyGradId());
    }
}

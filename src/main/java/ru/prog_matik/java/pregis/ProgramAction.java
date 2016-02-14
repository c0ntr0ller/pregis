package ru.prog_matik.java.pregis;

import ru.prog_matik.java.pregis.connectiondb.ConnectionDB;
import ru.prog_matik.java.pregis.services.organizations.ExportOrgRegistry;

/**
 * Класс будет обращаться ко всем объектам.
 * Created by andryha on 12.02.2016.
 */
public class ProgramAction {

    public void callExportOrgRegistry() {
        ExportOrgRegistry reg = new ExportOrgRegistry();
        try {
            reg.callExportOrgRegistry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

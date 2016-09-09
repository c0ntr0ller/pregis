package ru.progmatik.java.pregis.connectiondb.localdb.reference;

/**
 * Класс, описывает, объект связи перечня услуг (Коммунальных, жилищные и т.д.), для хранения в локальной базе данных.
 */
public class ServicesGisJkhForGradDataSet {

    private final int id;
    private final int serviceGisJkh;
    private final String serviceGrad;

    public ServicesGisJkhForGradDataSet(int id, int serviceGisJkh, String serviceGrad) {
        this.id = id;
        this.serviceGisJkh = serviceGisJkh;
        this.serviceGrad = serviceGrad;
    }

    public ServicesGisJkhForGradDataSet(int serviceGisJkh, String serviceGrad) {
        this.id = -1;
        this.serviceGisJkh = serviceGisJkh;
        this.serviceGrad = serviceGrad;
    }

    public int getId() {
        return id;
    }

    public int getServiceGisJkh() {
        return serviceGisJkh;
    }

    public String getServiceGrad() {
        return serviceGrad;
    }
}

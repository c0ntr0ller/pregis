package ru.progmatik.java.pregis.model;


import java.math.BigDecimal;

/**
 * Класс описывает строку, возвращаемую процедурой заполнения второго листа УПД INVOCE02
 * Created by Администратор on 05.06.2017.
 */
public class Invoice02 {
    private String pd_no;
    private String gis_service;
    private String gis_service_uiid;
    private String ipu;
    private double amount_personal;
    private String odn_pu;
    private double amount_shared;
    private BigDecimal tariff;
    private BigDecimal repays;
    private BigDecimal exempts;
    private String charge_legend;
    private double norm_personal;
    private double norm_shared;
    private String meters_personal;
    private String meters_shared;
    private double summ_amount_personal;
    private double summ_amount_shared;
    private String repays_text;
    private BigDecimal repays_sum;
    private double rassroch_current;
    private double rassroch_other;
    private double rassroch_percentsum;
    private double rassroch_percent;
    private BigDecimal forpay;
    private String code;
    private String code_parent;
    private BigDecimal charge_total;
    private int payee_id;

    public int getPayee_id() {
        return payee_id;
    }

    public void setPayee_id(int payee_id) {
        this.payee_id = payee_id;
    }

    public BigDecimal getCharge_total() {
        return charge_total;
    }

    public void setCharge_total(BigDecimal charge_total) {
        this.charge_total = charge_total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_parent() {
        return code_parent;
    }

    public void setCode_parent(String code_parent) {
        this.code_parent = code_parent;
    }

    public String getPd_no() {
        return pd_no;
    }

    public void setPd_no(String pd_no) {
        this.pd_no = pd_no;
    }

    public String getGis_service() {
        return gis_service;
    }

    public void setGis_service(String gis_service) {
        this.gis_service = gis_service;
    }

    public String getGis_service_uiid() {
        return gis_service_uiid;
    }

    public void setGis_service_uiid(String gis_service_uiid) {
        this.gis_service_uiid = gis_service_uiid;
    }

    public String getIpu() {
        return ipu;
    }

    public void setIpu(String ipu) {
        this.ipu = ipu;
    }

    public double getAmount_personal() {
        return amount_personal;
    }

    public void setAmount_personal(double amount_personal) {
        this.amount_personal = amount_personal;
    }

    public String getOdn_pu() {
        return odn_pu;
    }

    public void setOdn_pu(String odn_pu) {
        this.odn_pu = odn_pu;
    }

    public double getAmount_shared() {
        return amount_shared;
    }

    public void setAmount_shared(double amount_shared) {
        this.amount_shared = amount_shared;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public BigDecimal getRepays() {
        return repays;
    }

    public void setRepays(BigDecimal repays) {
        this.repays = repays;
    }

    public BigDecimal getExempts() {
        return exempts;
    }

    public void setExempts(BigDecimal exempts) {
        this.exempts = exempts;
    }

    public String getCharge_legend() {
        return charge_legend;
    }

    public void setCharge_legend(String charge_legend) {
        this.charge_legend = charge_legend;
    }

    public double getNorm_personal() {
        return norm_personal;
    }

    public void setNorm_personal(double norm_personal) {
        this.norm_personal = norm_personal;
    }

    public double getNorm_shared() {
        return norm_shared;
    }

    public void setNorm_shared(double norm_shared) {
        this.norm_shared = norm_shared;
    }

    public String getMeters_personal() {
        return meters_personal;
    }

    public void setMeters_personal(String meters_personal) {
        this.meters_personal = meters_personal;
    }

    public String getMeters_shared() {
        return meters_shared;
    }

    public void setMeters_shared(String meters_shared) {
        this.meters_shared = meters_shared;
    }

    public double getSumm_amount_personal() {
        return summ_amount_personal;
    }

    public void setSumm_amount_personal(double summ_amount_personal) {
        this.summ_amount_personal = summ_amount_personal;
    }

    public double getSumm_amount_shared() {
        return summ_amount_shared;
    }

    public void setSumm_amount_shared(double summ_amount_shared) {
        this.summ_amount_shared = summ_amount_shared;
    }

    public String getRepays_text() {
        return repays_text;
    }

    public void setRepays_text(String repays_text) {
        this.repays_text = repays_text;
    }

    public BigDecimal getRepays_sum() {
        return repays_sum;
    }

    public void setRepays_sum(BigDecimal repays_sum) {
        this.repays_sum = repays_sum;
    }

    public double getRassroch_current() {
        return rassroch_current;
    }

    public void setRassroch_current(double rassroch_current) {
        this.rassroch_current = rassroch_current;
    }

    public double getRassroch_other() {
        return rassroch_other;
    }

    public void setRassroch_other(double rassroch_other) {
        this.rassroch_other = rassroch_other;
    }

    public double getRassroch_percentsum() {
        return rassroch_percentsum;
    }

    public void setRassroch_percentsum(double rassroch_percentsum) {
        this.rassroch_percentsum = rassroch_percentsum;
    }

    public double getRassroch_percent() {
        return rassroch_percent;
    }

    public void setRassroch_percent(double rassroch_percent) {
        this.rassroch_percent = rassroch_percent;
    }

    public BigDecimal getForpay() {
        return forpay;
    }

    public void setForpay(BigDecimal forpay) {
        this.forpay = forpay;
    }
}
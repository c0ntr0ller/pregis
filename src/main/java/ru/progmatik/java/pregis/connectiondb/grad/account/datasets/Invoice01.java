package ru.progmatik.java.pregis.connectiondb.grad.account.datasets;

import java.math.BigDecimal;

/**
 * Класс описывает строку, возвращаемую из Града процедурой первого листа ЕПД INVOCE01
 * Created by Администратор on 05.06.2017.
 */
public class Invoice01 {
    private String accountguid;
    private String pd_type;
    private String pd_no;
    private BigDecimal sq_total;
    private BigDecimal sq_live;
    private BigDecimal sq_heat;
    private byte tencount;
    private double debt;
    private double avans;
    private double pays_advance;
    private String bik;
    private String bank_account;

    public String getAccountguid() {
        return accountguid;
    }

    public void setAccountguid(String accountguid) {
        this.accountguid = accountguid;
    }

    public String getPd_type() {
        return pd_type;
    }

    public void setPd_type(String pd_type) {
        this.pd_type = pd_type;
    }

    public String getPd_no() {
        return pd_no;
    }

    public void setPd_no(String pd_no) {
        this.pd_no = pd_no;
    }

    public BigDecimal getSq_total() {
        return sq_total;
    }

    public void setSq_total(BigDecimal sq_total) {
        this.sq_total = sq_total;
    }

    public BigDecimal getSq_live() {
        return sq_live;
    }

    public void setSq_live(BigDecimal sq_live) {
        this.sq_live = sq_live;
    }

    public BigDecimal getSq_heat() {
        return sq_heat;
    }

    public void setSq_heat(BigDecimal sq_heat) {
        this.sq_heat = sq_heat;
    }

    public byte getTencount() {
        return tencount;
    }

    public void setTencount(byte tencount) {
        this.tencount = tencount;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getAvans() {
        return avans;
    }

    public void setAvans(double avans) {
        this.avans = avans;
    }

    public double getPays_advance() {
        return pays_advance;
    }

    public void setPays_advance(double pays_advance) {
        this.pays_advance = pays_advance;
    }

    public String getBik() {
        return bik;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }
}

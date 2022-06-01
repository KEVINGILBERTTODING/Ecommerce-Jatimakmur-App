package com.kevin.jatimakmur;

public class Pembelian {
    private String no_nota, due_date, status;
    private double total_biaya;


    public Pembelian(String no_nota, String due_date, String status, double total_biaya) {
        this.no_nota = no_nota;
        this.due_date = due_date;
        this.status = status;
        this.total_biaya = total_biaya;
    }

    public String getNo_nota() {
        return no_nota;
    }

    public void setNo_nota(String no_nota) {
        this.no_nota = no_nota;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(double total_biaya) {
        this.total_biaya = total_biaya;
    }
}

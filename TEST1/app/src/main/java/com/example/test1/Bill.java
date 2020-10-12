package com.example.test1;

import java.io.Serializable;
import java.util.ArrayList;

public class Bill implements Serializable {

    public Bill(){

    }

    public Bill(String processed,String billName, String ph,String storeName, String date, ArrayList<String> products, ArrayList<String> quantities) {
        this.billName = billName;
        this.ph = ph;
        this.storeName = storeName;
        this.date = date;
        this.products = products;
        this.quantities = quantities;
        this.processed = processed;
    }


    private String processed;

    private String billName;
    private String ph;

    private String storeName;
    private String date;

    private ArrayList<String> products;
    private ArrayList<String> quantities;

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public ArrayList<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(ArrayList<String> quantities) {
        this.quantities = quantities;
    }


    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }






}

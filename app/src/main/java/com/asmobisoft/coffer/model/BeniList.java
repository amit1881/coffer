package com.asmobisoft.coffer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/17/17.
 */

public class BeniList implements Serializable{

    private String status;
    private List<Beneficierymodel> data;

    public BeniList(String status, List<Beneficierymodel> data) {
        this.status = status;
        this.data = new ArrayList<Beneficierymodel>();
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Beneficierymodel> getData() {
        return data;
    }

    public void setData(List<Beneficierymodel> data) {
        this.data = data;
    }



}

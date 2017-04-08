package com.asmobisoft.coffer.responceparser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class HistroryParser implements Serializable{

    private List<Reports> reports;

    @SerializedName("state")
    private List<Monet_Wallet_History> wallet_histories;

    public HistroryParser(List<Reports> reports) {
        this.reports = reports;
    }

    public List<Reports> getReports() {
        return reports;
    }

    public void setReports(List<Reports> reports) {
        this.reports = reports;
    }

    public List<Monet_Wallet_History> getWallet_histories() {
        return wallet_histories;
    }

    public void setWallet_histories(List<Monet_Wallet_History> wallet_histories) {
        this.wallet_histories = wallet_histories;
    }
}

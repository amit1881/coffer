package com.asmobisoft.coffer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 10/3/16.
 */
public class AllProviders {

    public List<ProvidersData> getProviders() {
        return providers;
    }

    public void setProviders(List<ProvidersData> providers) {
        this.providers = providers;
    }

    @SerializedName("results")
    private List<ProvidersData> providers;

}

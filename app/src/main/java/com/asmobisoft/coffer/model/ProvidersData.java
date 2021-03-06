package com.asmobisoft.coffer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 10/3/16.
 */
public class ProvidersData {

    @SerializedName("provider_id")
    private int provider_id;

    @SerializedName("provider_name")
    private String provider_name;

    @SerializedName("provider_code")
    private String provider_code;

    @SerializedName("service")
    private String service;

    @SerializedName("provider_image")
    private String provider_image;

    @SerializedName("status")
    private String status;

    public ProvidersData(int provider_id, String provider_name, String provider_code, String service, String provider_image, String status) {
        this.provider_id = provider_id;
        this.provider_name = provider_name;
        this.provider_code = provider_code;
        this.service = service;
        this.provider_image = provider_image;
        this.status = status;
    }

    public ProvidersData() {

    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_code() {
        return provider_code;
    }

    public void setProvider_code(String provider_code) {
        this.provider_code = provider_code;
    }

    public String getProvider_image() {
        return provider_image;
    }

    public void setProvider_image(String provider_image) {
        this.provider_image = provider_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

package com.asmobisoft.coffer.responceparser;

import com.asmobisoft.coffer.commonmethod.Keys;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 1/18/17.
 */

public class AddBeniResponce {

    @SerializedName(Keys.STATUS)
    public String status;

    @SerializedName("message")
    public String message;

    @SerializedName(Keys.BENIFICIARY_CODE)
    public String BeneficiaryCode;

    @SerializedName(Keys.NAME)
    public String name;

    @SerializedName("limit")
    public String limit;



}

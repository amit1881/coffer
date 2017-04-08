package com.asmobisoft.coffer.requestparser;

import com.asmobisoft.coffer.commonmethod.Keys;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 1/17/17.
 */

public class RequestAll {

    @SerializedName(Keys.API_TOKEN)
    public String api_token;

    @SerializedName(Keys.MOBILE_NUMBER)
    public String mobile_number;

    @SerializedName(Keys.F_NAME)
    public String fname;

    @SerializedName(Keys.L_NAME)
    public String lname;

    @SerializedName(Keys.OTP)
    public String otp;

    @SerializedName(Keys.BANK_CODE)
    public String bank_code;

    @SerializedName(Keys.ACCOUNT_NUMBER)
    public String account_number;

    @SerializedName(Keys.IFSC)
    public String ifsc;

    @SerializedName(Keys.NAME)
    public String name;

    @SerializedName(Keys.BENIFICIARY_CODE)
    public String BeneficiaryCode;

    @SerializedName(Keys.CHANNEL)
    public String channel;

    @SerializedName(Keys.CLIENT_ID)
    public String client_id;

    @SerializedName(Keys.PAY_ID)
    public String payid;

    @SerializedName("bank_account")
    public String bank_account;

    @SerializedName("amount")
    public String amount;

}

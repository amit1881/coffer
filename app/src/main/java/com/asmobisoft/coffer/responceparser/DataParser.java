package com.asmobisoft.coffer.responceparser;

import com.asmobisoft.coffer.commonmethod.Keys;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 1/17/17.
 */

public class DataParser {

    @SerializedName(Keys.RECIPIENT_ID)
    public String recipient_id;

    @SerializedName(Keys.STATUS)
    public String status;

    @SerializedName(Keys.RECIPIENT_NAME)
    public String recipient_name;

    @SerializedName(Keys.BANK_NAME)
    public String BankName;

    @SerializedName(Keys.BENIFICIARY_CODE)
    public String BeneficiaryCode;

    @SerializedName(Keys.ACCOUNT)
    public String account;

    @SerializedName(Keys.IFSC)
    public String ifsc;

    @SerializedName(Keys.ORG_ACK_NO)
    public String OrgAckNo;

    @SerializedName(Keys.ORG_TRANS_REF_NUM)
    public String OrgTransRefNum;


}

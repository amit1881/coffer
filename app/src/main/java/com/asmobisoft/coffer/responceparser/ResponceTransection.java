package com.asmobisoft.coffer.responceparser;

import com.asmobisoft.coffer.commonmethod.Keys;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root on 1/17/17.
 */

public class ResponceTransection {

    @SerializedName(Keys.PAY_ID)
    public String payid;

    @SerializedName(Keys.STATUS)
    public String status;

    @SerializedName(Keys.MESSAGE)
    public String message;

    @SerializedName(Keys.TRANSECTION_REF_NO)
    public String TransactionRefNo;

    @SerializedName(Keys.ACK_NO)
    public String AckNo;

    @SerializedName(Keys.BANK_REF)
    public String bank_ref;

   }

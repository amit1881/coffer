package com.asmobisoft.coffer.responceparser;

import com.asmobisoft.coffer.commonmethod.Keys;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/17/17.
 */

public class ResponceBeniAll {

    @SerializedName(Keys.STATUS)
    public String status;

    @SerializedName("message")
    public String message;

    public List<DataParser> data;

   }

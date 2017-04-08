package com.asmobisoft.coffer.model;

/**
 * Created by root on 1/23/17.
 */

public class BeniListForSpinner  {

    private String benicode;
    private String beniName;
    private String beniId;
    private String beniBank;



    private String beniAccount;

    public BeniListForSpinner(String benicode, String beniName, String beniId) {
        this.benicode = benicode;
        this.beniName = beniName;
        this.beniId = beniId;
        this.beniId = beniId;
    }

    public BeniListForSpinner() {
    }

    public String getBeniAccount() {
        return beniAccount;
    }

    public void setBeniAccount(String beniAccount) {
        this.beniAccount = beniAccount;
    }

    public String getBenicode() {
        return benicode;
    }

    public void setBenicode(String benicode) {
        this.benicode = benicode;
    }

    public String getBeniName() {
        return beniName;
    }

    public void setBeniName(String beniName) {
        this.beniName = beniName;
    }

    public String getBeniId() {
        return beniId;
    }

    public void setBeniId(String beniId) {
        this.beniId = beniId;
    }


    public String getBeniBank() {
        return beniBank;
    }

    public void setBeniBank(String beniBank) {
        this.beniBank = beniBank;
    }




}

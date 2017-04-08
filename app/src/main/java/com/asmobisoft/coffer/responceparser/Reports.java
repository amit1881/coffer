package com.asmobisoft.coffer.responceparser;

/**
 * Created by root on 12/28/16.
 */
public class Reports {


    private String id;
    private String provider;

    public Reports() {

    }

    public Reports(String id, String provider, String number, String amount, String profit, String total_balance, String status) {
        this.id = id;
        this.provider = provider;
        this.number = number;
        this.amount = amount;
        this.profit = profit;
        this.total_balance = total_balance;
        this.status = status;
    }

    private String number;
    private String amount;
    private String profit;
    private String total_balance;
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(String total_balance) {
        this.total_balance = total_balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


/*
    {
        "id": "1482915667",
            "provider": "VODAFONE POSTPAID",
            "number": "9899077656",
            "amount": 20,
            "profit": 0.04,
            "total_balance": 70.625,
            "status": "Success"
    },*/



}

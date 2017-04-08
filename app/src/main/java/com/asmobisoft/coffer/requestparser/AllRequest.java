package com.asmobisoft.coffer.requestparser;

/**
 * Created by root on 1/16/17.
 */

public class AllRequest {

    private String api_token;
    private String mobile_number;
    private String fname;
    private String lname;
    private String otp;
    private String bank_code;
    private String account_number;
    private String ifsc;
    private String name;
    private String BeneficiaryCode;
    private String channel;
    private String client_id;
    private String payid;

    public AllRequest() {
//        this.api_token = api_token;
    }

    public AllRequest(Object api_token, Object mobile_number, Object fname, Object lname, Object otp, Object bank_code,
                      Object account_number, Object ifsc, Object name, Object beneficiaryCode, Object channel,
                      Object client_id, Object payid) {
        this.api_token = api_token.toString();
        this. mobile_number = mobile_number.toString();
        this.fname = fname.toString();
        this.lname = lname.toString();
        this.otp = otp.toString();
        this.bank_code = bank_code.toString();
        this.account_number = account_number.toString();
        this.ifsc = ifsc.toString();
        this.name = name.toString();
        this.BeneficiaryCode = beneficiaryCode.toString();
        this.channel = channel.toString();
        this.client_id = client_id.toString();
        this.payid = payid.toString();
    }

    
    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        api_token = api_token;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        mobile_number = mobile_number;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        lname = lname;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        otp = otp;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        bank_code = bank_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        account_number = account_number;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        ifsc = ifsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getBeneficiaryCode() {
        return BeneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        BeneficiaryCode = beneficiaryCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        channel = channel;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        client_id = client_id;
    }

    public String getPayid() {
        return payid;
    }

    public void setPayid(String payid) {
        payid = payid;
    }

    
    
}

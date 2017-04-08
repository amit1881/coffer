package com.asmobisoft.coffer.model;

import java.io.Serializable;

/**
 * Created by root on 1/17/17.
 */

public class Beneficierymodel implements Serializable{

    private String recipient_id;
    private String status;
    private String recipient_name;
    private String BankName;
    private String BeneficiaryCode;
    private String account;
    private String ifsc;
    private String OrgAckNo;
    private String OrgTransRefNum;

    public Beneficierymodel(){

    }

    public Beneficierymodel(Object recipient_id, Object status, Object recipient_name, Object bankName,
                            Object beneficiaryCode, Object account, Object ifsc, Object orgAckNo,
                            Object orgTransRefNum) {
        this.recipient_id = recipient_id.toString();
        this.status = status.toString();
        this.recipient_name = recipient_name.toString();
        BankName = bankName.toString();
        BeneficiaryCode = beneficiaryCode.toString();
        this.account = account.toString();
        this.ifsc = ifsc.toString();
        OrgAckNo = orgAckNo.toString();
        OrgTransRefNum = orgTransRefNum.toString();
    }

    public String getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(String recipient_id) {
        this.recipient_id = recipient_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBeneficiaryCode() {
        return BeneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        BeneficiaryCode = beneficiaryCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getOrgAckNo() {
        return OrgAckNo;
    }

    public void setOrgAckNo(String orgAckNo) {
        OrgAckNo = orgAckNo;
    }

    public String getOrgTransRefNum() {
        return OrgTransRefNum;
    }

    public void setOrgTransRefNum(String orgTransRefNum) {
        OrgTransRefNum = orgTransRefNum;
    }


}

package com.asmobisoft.coffer.pojo;

/**
 * Created by root on 12/13/16.
 */

public class UserListPojo {
    private String mobile;
    private String first_name;
    private String last_name;
    private String email;
    public UserListPojo() {

    }

    public UserListPojo(String mobile, String first_name, String last_name, String email) {
        this.mobile = mobile;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}

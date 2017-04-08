package com.asmobisoft.coffer.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.Beneficierymodel;
import com.asmobisoft.coffer.requestparser.RequestAll;
import com.asmobisoft.coffer.responceparser.AddBeniResponce;
import com.asmobisoft.coffer.webservices.CallPostService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 1/17/17.
 */
public class AddBeniActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText et_mobile_number_beni, et_beni_name, et_ifsc, et_bank_account;
    private Button btn_add;
    private Gson gson;
    private AddBeniResponce mAddBeniParser;
    private ProgressDialog mProgressDialog;
    private EditText etOTP;
    private EditText etFname;
    private EditText etLname;
    private Button btn_create_customer;
    private Button btn_add1;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beni);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Beneficiary");
        mobileNumber = Utility.getPrefsData(AddBeniActivity.this, "mobile", "");
        Log.e("BeniActivity", "mobileNumber : " + mobileNumber);
        getID();

    }

    private void getID() {

        et_mobile_number_beni = (EditText) findViewById(R.id.et_mobile_number_beni);
        et_beni_name = (EditText) findViewById(R.id.et_beni_name);
        et_ifsc = (EditText) findViewById(R.id.et_ifsc);
        et_bank_account = (EditText) findViewById(R.id.et_bank_account);

        btn_add1 = (Button) findViewById(R.id.btn_add1);
        etFname = (EditText) findViewById(R.id.et_fname);
        etLname = (EditText) findViewById(R.id.et_lname);
        etOTP = (EditText) findViewById(R.id.et_otp);
        btn_create_customer = (Button) findViewById(R.id.btn_create_customer);

        btn_add1.setVisibility(View.GONE);
        et_beni_name.setVisibility(View.GONE);
        et_ifsc.setVisibility(View.GONE);
        et_bank_account.setVisibility(View.GONE);
        btn_add = (Button) findViewById(R.id.btn_add);

        etOTP.setVisibility(View.GONE);
        etFname.setVisibility(View.GONE);
        etLname.setVisibility(View.GONE);
        btn_create_customer.setVisibility(View.GONE);

      //  et_mobile_number_beni.setText("9711097287");
        btn_add.setText("Add Customer");

        btn_create_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateCustomerAsync().execute();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((et_mobile_number_beni.getText().toString().length() != 0)) {
                    new GetCustomerAsync().execute();
                } else {
                    Toast.makeText(AddBeniActivity.this, "Please enter mobile number !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidate()) {
                    new BenificiaryAsync().execute();
                }
            }
        });

    }

    private boolean isValidate() {

        if (et_mobile_number_beni.getText().toString().trim().length() == 0) {
            Toast.makeText(AddBeniActivity.this, "Please enter mobile number !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_beni_name.getText().toString().trim().length() == 0) {
            Toast.makeText(AddBeniActivity.this, "Please enter name !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_ifsc.getText().toString().trim().length() == 0) {
            Toast.makeText(AddBeniActivity.this, "Please enter IFSC Code !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (et_bank_account.getText().toString().trim().length() == 0) {
            Toast.makeText(AddBeniActivity.this, "Please enter Account number !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private ArrayList<Beneficierymodel> mBeniList;
    private EditText etOTPField;

    private class BenificiaryAsync extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(AddBeniActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callBeniList();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (mAddBeniParser != null) {
                if (mAddBeniParser.status.equals("0")) {

                    final Dialog dialog1 = new Dialog(AddBeniActivity.this);
                    Typeface externalFont = Typeface.createFromAsset(AddBeniActivity.this.getAssets(), "fonts/Frank.ttf");
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.alert_dialog_otp);
                    dialog1.setCancelable(true);
                    TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
                    text1.setText("OOPS !");
                    text1.setTypeface(externalFont, Typeface.BOLD);
                    etOTPField = (EditText) dialog1.findViewById(R.id.et_otp);

                    Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);
                    Button buttonOk = (Button) dialog1.findViewById(R.id.buttonOk);


                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            et_mobile_number_beni.setText("");
                            et_beni_name.setText("");
                            et_ifsc.setText("");
                            et_bank_account.setText("");

                            et_mobile_number_beni.requestFocus();
                            dialog1.dismiss();

                        }
                    });
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e("SignupActivity", "OTP :" + mAddBeniParser.BeneficiaryCode);
                            if (Utility.isOnline(AddBeniActivity.this)) {
                                if (etOTPField.getText().toString().trim().length() != 0) {
                                    new BeniConformAsync(etOTPField.getText().toString(), mAddBeniParser.BeneficiaryCode).execute();
                                } else {
                                    Toast.makeText(AddBeniActivity.this, "Please Enter OTP !", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Utility.InternetSetting(AddBeniActivity.this);
                            }

                        }
                    });
                    dialog1.show();

                } else {
                    Toast.makeText(AddBeniActivity.this, mAddBeniParser.message, Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(AddBeniActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_SHORT).show();


            }

        }
    }

    private String callBeniList() throws IOException {

        gson = new Gson();
        String responce = "";
        /*api_token,mobile_number,bank_account,ifsc,name*/
        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = mobileNumber;
        mAllRequest.bank_account = et_bank_account.getText().toString().trim();
        mAllRequest.ifsc = et_ifsc.getText().toString().trim();
        mAllRequest.name = et_beni_name.getText().toString().trim();

        Log.e("ADDBENI", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(AddBeniActivity.this);
        responce = mPostService.getJsonResponce(Keys.ADD_BENI, gson.toJson(mAllRequest));

        Log.e("AddBeni", "responce : " + responce);

        if (responce != null) {
            mAddBeniParser = gson.fromJson(responce, AddBeniResponce.class);
        } else {
            responce = null;
        }
        return responce;
    }

    private class GetCustomerAsync extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AddBeniActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callGetCustomer(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (mAddBeniParser != null) {
                if (mAddBeniParser.message.equals("OTP Sent Successfully")) {

                    btn_add.setVisibility(View.GONE);
                    et_mobile_number_beni.setVisibility(View.GONE);
                    etFname.setVisibility(View.VISIBLE);
                    etLname.setVisibility(View.VISIBLE);
                    etOTP.setVisibility(View.VISIBLE);
                    btn_create_customer.setVisibility(View.VISIBLE);

                    Log.e("AddBeni", "GetCustomerAsync : " + mAddBeniParser.message);

                } else if (mAddBeniParser.message.equals("Mobile Number already in use")) {
                    // Log.e("AddBeni",""+mAddBeniParser.message);
                    etFname.setVisibility(View.GONE);
                    btn_add.setVisibility(View.GONE);
                    etLname.setVisibility(View.GONE);
                    etOTP.setVisibility(View.GONE);
                    btn_create_customer.setVisibility(View.GONE);
                    et_bank_account.setVisibility(View.VISIBLE);
                    et_beni_name.setVisibility(View.VISIBLE);
                    et_ifsc.setVisibility(View.VISIBLE);
                    btn_add1.setVisibility(View.VISIBLE);


                }
            }
        }
    }

    private class CreateCustomerAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(AddBeniActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callGetCustomer(1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (mAddBeniParser != null) {
                if (mAddBeniParser.message.equals("Customer Registered Successfully")) {

                    etFname.setVisibility(View.GONE);
                    etLname.setVisibility(View.GONE);
                    etOTP.setVisibility(View.GONE);
                    btn_create_customer.setVisibility(View.GONE);
                    et_bank_account.setVisibility(View.VISIBLE);
                    et_beni_name.setVisibility(View.VISIBLE);
                    et_ifsc.setVisibility(View.VISIBLE);
                    btn_add1.setVisibility(View.VISIBLE);

                    Log.e("AddBeni", "" + mAddBeniParser.message);
                } else {
                    Toast.makeText(AddBeniActivity.this, mAddBeniParser.message, Toast.LENGTH_SHORT).show();
                    Log.e("AddBeni",""+mAddBeniParser.message);
                }
            }
        }
    }

    private String callGetCustomer(int value) throws IOException {

        gson = new Gson();
        String responce = "";
        /*api_token,mobile_number,bank_account,ifsc,name*/
        RequestAll mAllRequest = new RequestAll();
        CallPostService mPostService = new CallPostService(AddBeniActivity.this);

        if (value == 0) {
            mAllRequest.api_token = Keys.API_KEY;
            mAllRequest.mobile_number = mobileNumber;
            Log.e("ADDBENI", "Request : " + gson.toJson(mAllRequest));
            responce = mPostService.getJsonResponce(Keys.GENRATE_OTP, gson.toJson(mAllRequest));
            Log.e("AddBeni", "responce : " + responce);

        } else if (value == 1) {
            mAllRequest.api_token = Keys.API_KEY;
            mAllRequest.mobile_number = mobileNumber;
            mAllRequest.fname = etFname.getText().toString();
            mAllRequest.lname = etLname.getText().toString();
            mAllRequest.otp = etOTP.getText().toString();
            Log.e("ADDBENI", "Request : " + gson.toJson(mAllRequest));
            responce = mPostService.getJsonResponce(Keys.CREATE_CUSTOMER, gson.toJson(mAllRequest));
            Log.e("AddBeni", "responce : " + responce);

        }
        if (responce != null) {
            mAddBeniParser = gson.fromJson(responce, AddBeniResponce.class);
        } else {
            responce = null;
        }
        return responce;
    }


    private class BeniConformAsync extends AsyncTask<String, String, String> {

        private String opt;
        private String beniCode;

        public BeniConformAsync(String opt, String beniCode) {
            this.opt = opt;
            this.beniCode = beniCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(AddBeniActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callBeniConform(opt, beniCode);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (mAddBeniParser != null) {
                if (mAddBeniParser.message.equals("Your Verification is Successful")) {

                    Toast.makeText(AddBeniActivity.this, mAddBeniParser.message, Toast.LENGTH_SHORT).show();

                    Log.e("AddBeni", "" + mAddBeniParser.message);
                } else {
                    Log.e("AddBeni", "" + mAddBeniParser.message);
                    Toast.makeText(AddBeniActivity.this, mAddBeniParser.message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String callBeniConform(String otpStr, String BeneficiaryCode) throws IOException {

        gson = new Gson();
        String responce = "";
        /*api_token,mobile_number,bank_account,ifsc,name*/
        RequestAll mAllRequest = new RequestAll();
        CallPostService mPostService = new CallPostService(AddBeniActivity.this);
        /*api_token,mobile_number,otp,BeneficiaryCode*/
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = mobileNumber;
        mAllRequest.BeneficiaryCode = BeneficiaryCode;
        mAllRequest.otp = otpStr;
        Log.e("ADDBENI", "Request : " + gson.toJson(mAllRequest));
        responce = mPostService.getJsonResponce(Keys.CREATE_CUSTOMER, gson.toJson(mAllRequest));
        Log.e("AddBeni", "responce : " + responce);


        if (responce != null) {
            mAddBeniParser = gson.fromJson(responce, AddBeniResponce.class);
        } else {
            responce = null;
        }
        return responce;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}

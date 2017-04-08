package com.asmobisoft.coffer.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.CardViewDataAdapter;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.Beneficierymodel;
import com.asmobisoft.coffer.registration.LoginActivity;
import com.asmobisoft.coffer.registration.SignupActivity;
import com.asmobisoft.coffer.requestparser.RequestAll;
import com.asmobisoft.coffer.responceparser.AddBeniResponce;
import com.asmobisoft.coffer.responceparser.ResponceBeniAll;
import com.asmobisoft.coffer.webservices.CallPostService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by root on 1/17/17.
 */
public class BeniDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView recipient_id;
    private TextView status;
    private TextView recipient_name;
    private TextView BankName;
    private TextView BeneficiaryCode;
    private TextView account;
    private TextView ifsc;
    private Button btnDelete;
    private String mobileNumber;
    private Gson gson;
    private ResponceBeniAll mResponceBeniParser;
    private String otpStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beni_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Beneficiary Detail");
        mobileNumber = Utility.getPrefsData(BeniDetailActivity.this, "mobile", "");
        Log.e("BeniActivity", "mobileNumber : " + mobileNumber);

        getID();

    }

    private void getID() {
        recipient_id = (TextView)findViewById(R.id.tv_recipient_id);
        status = (TextView)findViewById(R.id.tv_status);
        recipient_name = (TextView)findViewById(R.id.tv_recipient_name);
        BankName = (TextView)findViewById(R.id.tv_bank_name);
        BeneficiaryCode = (TextView)findViewById(R.id.tv_beni_code);
        account = (TextView)findViewById(R.id.tv_acc_number);
        ifsc = (TextView)findViewById(R.id.tv_ifsc);
        btnDelete = (Button)findViewById(R.id.btn_delete);


        recipient_id.setText(getIntent().getExtras().get("recipient_id").toString());
        status.setText(getIntent().getExtras().get("status").toString());
        recipient_name.setText(getIntent().getExtras().get("recipient_name").toString());
        BankName.setText(getIntent().getExtras().get("BankName").toString());
        BeneficiaryCode.setText(getIntent().getExtras().get("BeneficiaryCode").toString());
        account.setText(getIntent().getExtras().get("account").toString());
        ifsc.setText(getIntent().getExtras().get("ifsc").toString());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.isOnline(BeniDetailActivity.this)){
                    new BenificiaryDeleteAsync().execute();
                }else{

                }

            }
        });


    }

    private EditText etOTPField;
    private ProgressDialog mProgressDialog;
    private class BenificiaryDeleteAsync extends AsyncTask<String, String, String> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(BeniDetailActivity.this);
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

            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
            }

            if(mResponceBeniParser!=null){
                if(mResponceBeniParser.message.equals("OTP has been sent for validation")){

                    final Dialog dialog1 = new Dialog(BeniDetailActivity.this);
                    Typeface externalFont = Typeface.createFromAsset(BeniDetailActivity.this.getAssets(), "fonts/Frank.ttf");
                    // String mess = SignupActivity.this.getResources().getString(R.string.internet_connection_dialog);
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

                            dialog1.dismiss();

                        }
                    });
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                           // Log.e("SignupActivity", "OTP :" + otpStr);
                            if (Utility.isOnline(BeniDetailActivity.this)) {
                                if (etOTPField.getText().toString().trim().length() != 0) {
                                    Toast.makeText(BeniDetailActivity.this, mResponceBeniParser.message, Toast.LENGTH_LONG).show();

                                    BenificiaryDeleteConformAsync mSignupAsync = new BenificiaryDeleteConformAsync();
                                    mSignupAsync.execute();
                                    dialog1.dismiss();
                                } else {
                                    Toast.makeText(BeniDetailActivity.this, mResponceBeniParser.message, Toast.LENGTH_LONG).show();
                                }

                            }  else {
                                Utility.InternetSetting(BeniDetailActivity.this);
                            }

                        }
                    });
                    dialog1.show();

                }else{
                    Toast.makeText(BeniDetailActivity.this, mResponceBeniParser.message, Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(BeniDetailActivity.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
            }



        }
    }


    private String callBeniList() throws IOException {

        gson = new Gson();
        String responce = "";

        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = mobileNumber;
        if(!getIntent().getExtras().get("BeneficiaryCode").toString().equals("")){
            mAllRequest.BeneficiaryCode = getIntent().getExtras().get("BeneficiaryCode").toString();
        }else{
            mAllRequest.BeneficiaryCode = "0";
        }

        Log.e("BeniList", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(BeniDetailActivity.this);
        responce = mPostService.getJsonResponce(Keys.DELETE_BENI, gson.toJson(mAllRequest));

        Log.e("benilist", "responce : " + responce);

        if (responce != null) {
            mResponceBeniParser = gson.fromJson(responce, ResponceBeniAll.class);
        } else {
            responce = null;
        }
        return responce;
    }

    private class BenificiaryDeleteConformAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(BeniDetailActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callBeniDeleteCONFORM();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
            }

            if(mResponceBeniParser!=null){

                //Toast.makeText(BeniDetailActivity.this, mResponceBeniParser.message, Toast.LENGTH_SHORT).show();

                finish();

            }else{
                Toast.makeText(BeniDetailActivity.this, "Something Went Wrong !", Toast.LENGTH_SHORT).show();
            }



        }
    }


    private String callBeniDeleteCONFORM() throws IOException {

        gson = new Gson();
        String responce = "";

        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = mobileNumber;
        if(!getIntent().getExtras().get("BeneficiaryCode").toString().equals("")){
            mAllRequest.BeneficiaryCode = getIntent().getExtras().get("BeneficiaryCode").toString();
        }else{
            mAllRequest.BeneficiaryCode = "0";
        }
        mAllRequest.otp = etOTPField.getText().toString().trim();

        Log.e("BeniList", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(BeniDetailActivity.this);
        responce = mPostService.getJsonResponce(Keys.DELETE_BENI_CONFORM, gson.toJson(mAllRequest));

        Log.e("benilist", "responce : " + responce);

        if (responce != null) {
            mResponceBeniParser = gson.fromJson(responce, ResponceBeniAll.class);
        } else {
            responce = null;
        }
        return responce;
    }


}

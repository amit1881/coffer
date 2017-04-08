package com.asmobisoft.coffer.registration;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 2/1/16.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText etOTP;
    private LinearLayout llRoot;
    private EditText etMobile;
    private Dialog dialog;
    private TextView tvMobileNumber;

    private LinearLayout llBack;
    private ProgressDialog mProgressDialog;
    private String strMob;
    private String otpStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        getView();

        tvMobileNumber.setVisibility(View.GONE);
        etOTP.setVisibility(View.GONE);

    }
    public void getView(){

        llRoot = (LinearLayout)findViewById(R.id.loginroot);
        etOTP = (EditText)findViewById(R.id.et_otp);
        etMobile = (EditText)findViewById(R.id.et_fotgot_mobile);
        tvMobileNumber = (TextView)findViewById(R.id.tv_mobile_number);

        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.ll_back).setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity1
        moveTaskToBack(true);
        //overridePendingTransition(R.anim.slidout,R.anim.slidin);
    }


    public boolean validate() {
        boolean valid = true;

        if(etMobile.getText().toString().length() ==0){
            Toast.makeText(getApplicationContext(),"Please Enter Mobile number !",Toast.LENGTH_LONG).show();
            return false;
        }
        if(etMobile.getText().toString().trim().length() < 10 || etMobile.getText().toString().trim().length() > 15 ){
            Toast.makeText(getApplicationContext(),"Please Enter Mobile number between 10 to 14 characters !",Toast.LENGTH_LONG).show();
            return false;

        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:

                if (validate()) {
                    strMob = etMobile.getText().toString().trim();
                    if (Utility.isOnline(ForgotPasswordActivity.this)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        OTPAsync mOTPAsync = new OTPAsync(strMob);
                        mOTPAsync.execute();
                    } else {
                        Utility.InternetSetting(ForgotPasswordActivity.this);
                    }
                }


                break;

            case R.id.ll_back:

                Intent i = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                //overridePendingTransition(R.anim.slidout,R.anim.slidin);
                break;

        }
    }
    private class OTPAsync extends AsyncTask<String, String, String> {

        EditText etOTPField;
        private String strMobi;

        public OTPAsync(String strMobi) {

            this.strMobi = strMobi;
//            this.password=password;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(ForgotPasswordActivity.this);
//
            String responce = "";

            String url = Keys.OTP_URL + "&mobile=" + strMob;
            Log.e("Signup", "URL : " + url);

            responce = mNetClientGet.getDataClientData(url);

            Log.e("Signup", "responce : " + responce);
            return responce;


        }

        protected void onPostExecute(String result) {
            if(mProgressDialog !=null){
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        Log.e("Signup", "Hit OTP : " + jsonObj.getString("rtnMSG"));

                        if (jsonObj.getString("rtnMSG").equals("OTP Send successfully")) {
                            Log.e("Signup", "Hit OTP : " + jsonObj.getString("otp"));
                            otpStr = jsonObj.getString("otp").trim();
                            tvMobileNumber.setVisibility(View.VISIBLE);
                            etOTP.setVisibility(View.VISIBLE);
                            tvMobileNumber.setText("You have receive an OTP on your "+strMobi+" Mobile Number please enter bellow");

                            if(etOTP.getText().toString().trim().length() == 0){
                                Toast.makeText(ForgotPasswordActivity.this,"Please Enter OTP !",Toast.LENGTH_LONG).show();
                            }else if(etOTP.getText().toString().trim().equals(otpStr)){

                                //open a Dialog
                                DialogShow();

                            }else{
                                Toast.makeText(ForgotPasswordActivity.this,"Please Enter a valid OTP !",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Unable to Send OTP !", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    private class ChangepassWord extends AsyncTask<String, String, String> {

        EditText etOTPField;
        private String strPass;

        public ChangepassWord(String strPass) {
            this.strPass = strPass;
          //  this.password=password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(ForgotPasswordActivity.this);
//
            String responce = "";
            //http://webservice.cofferwallet.com/cofferdb.php?task=forgetpassword&mobile=7827505727654321
            String url = Keys.FORGOT_PASS_URL + "&mobile=" + strMob +"&password="+strPass;
            Log.e("Signup", "URL : " + url);

            responce = mNetClientGet.getDataClientData(url);

            Log.e("Signup", "responce : " + responce);
            return responce;


        }

        protected void onPostExecute(String result) {
            if(mProgressDialog !=null){
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        Log.e("Signup", "Hit OTP : " + jsonObj.getString("rtnMSG"));

                        if (jsonObj.getString("rtnMSG").equals("Password Reset successfully")) {
                           // Log.e("Signup", "Password Status : " + );
                            Toast.makeText(ForgotPasswordActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    private void DialogShow(){

        final EditText etNewPass;

        final Dialog dialog1 = new Dialog(ForgotPasswordActivity.this);
        Typeface externalFont = Typeface.createFromAsset(ForgotPasswordActivity.this.getAssets(), "fonts/Frank.ttf");
        // String mess = SignupActivity.this.getResources().getString(R.string.internet_connection_dialog);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.alert_dialog_new_pass);
        dialog1.setCancelable(true);
        TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
        text1.setText("Please Enter New Password !");
        text1.setTypeface(externalFont, Typeface.BOLD);

        etNewPass = (EditText) dialog1.findViewById(R.id.et_new_pass);
//                    text.setText("");
        // text.setTypeface(externalFont);

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
                if(Utility.isOnline(ForgotPasswordActivity.this)){
                    String strNewPass = etNewPass.getText().toString();
                    new ChangepassWord(strNewPass).execute();
                }else{
                    Utility.InternetSetting(ForgotPasswordActivity.this);
                }

            }
        });
        dialog1.show();
    }



}

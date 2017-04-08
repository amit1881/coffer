package com.asmobisoft.coffer.registration;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
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
public class SignupActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";

    private EditText etFirstName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etLastName;
    private EditText etMobile;
    private TextView tvLink;
    private LinearLayout llRegister;
    private TextView tvHelp;
    private Dialog dialog;
    private ProgressDialog pDialog;
    private String strFirstName;
    private String strLastName;
    private String strEmail;
    private String strMob;
    private String strPassword;
    private String otpStr = "";
    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getView();
    }

    public void getView() {
        //Input Unit
        etEmail = (EditText) findViewById(R.id.et_email);
        etFirstName = (EditText) findViewById(R.id.et_fullName);
        etPassword = (EditText) findViewById(R.id.et_password);
        etLastName = (EditText) findViewById(R.id.et_lastname);
        etMobile = (EditText) findViewById(R.id.et_mobile);

        //Event Click Unit
        llRegister = (LinearLayout) findViewById(R.id.ll_signup);
        tvLink = (TextView) findViewById(R.id.tv_login_here);
        tvHelp = (TextView) findViewById(R.id.tv_help);


        llRegister.setOnClickListener(this);
        tvHelp.setOnClickListener(this);
        tvLink.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity1
        moveTaskToBack(true);
        //overridePendingTransition(R.anim.slidout,R.anim.slidin);

    }

    public boolean validate() {

        if (etFirstName.getText().toString().length() == 0) {
            etFirstName.setError("Please enter First name !");
            return false;
        }
        if (etLastName.getText().toString().length() == 0) {
            etLastName.setError("Please enter Last name !");
            return false;
        }
        if (etEmail.getText().toString().length() == 0) {
            etEmail.setError("Please enter Email !");
            return false;

        }
        if (etPassword.getText().toString().length() == 0) {
            etPassword.setError("Please enter Password !");
            return false;
        }


        if (etMobile.getText().toString().length() == 0 && etMobile.getText().toString().length() < 10) {
            etMobile.setError("Please enter Mobile Number of minimum 10 !");
            return false;
        }


        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_signup:

                strFirstName = etFirstName.getText().toString().trim();
                strLastName = etLastName.getText().toString().trim();

                strEmail = etEmail.getText().toString().trim();
                strMob = etMobile.getText().toString().trim();
                strPassword = etPassword.getText().toString().trim();
                if (validate()) {
                    if (Utility.isOnline(SignupActivity.this)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        OTPAsync mOTPAsync = new OTPAsync(strMob);
                        mOTPAsync.execute();
                    } else {
                        Utility.InternetSetting(SignupActivity.this);
                    }
                }


                break;
            case R.id.tv_help:
                WebView wvEula;

                // Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/frabk.ttf");
                dialog = new Dialog(SignupActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.help);
                dialog.setCancelable(true);

                //Load eula url from text
                wvEula = (WebView) dialog.findViewById(R.id.wv_eula_text);
                wvEula.loadUrl("file:///android_asset/help.html");
                /*wvEula.setBackgroundResource(R.drawable.back_gradient);
                wvEula.setBackgroundColor(Color.TRANSPARENT);*/
                dialog.show();


                break;
            case R.id.tv_login_here:

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.slidout,R.anim.slidin);

                break;
        }
    }

    private class SignupAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(SignupActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(SignupActivity.this);
//          String responceotp= "";
            String responce = "";
            String url = Keys.SIGNUP_URL + "&email=" + strEmail + "&firstname="
                    + strFirstName + "&lastname=" + strLastName + "&mobile=" + strMob + "&password=" + strPassword +"&device_id="+Utility.getPrefsData(SignupActivity.this,"FCMtoken","");
            Log.e("Signup", "URL : " + url);
            responce = mNetClientGet.getDataClientData(url);

            Log.e("Signup", "responce : " + responce);
            return responce;
        }

        protected void onPostExecute(String result) {
            if(mProgressDialog !=null){
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce : " + result);
            if (result != null) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        Log.e("Signup", "Hit Responce : " + jsonObj.getString("rtnMSG"));
                        if (jsonObj.getString("rtnMSG").equals("Registration successfully")) {

                            Log.e("SignupActivity","mobile number"+etMobile.getText().toString().trim());
                          //  Utility.setPrefsData(SignupActivity.this,"mobile",etMobile.getText().toString().trim());
                            etFirstName.setText("");
                            etLastName.setText("");
                            etEmail.setText("");
                            etMobile.setText("");
                            etPassword.setText("");
                            Toast.makeText(SignupActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();

                            Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            Toast.makeText(SignupActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }
            }
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

            mProgressDialog = new ProgressDialog(SignupActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(SignupActivity.this);
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
                        } else {
                            Toast.makeText(SignupActivity.this, "Unable to Send OTP !", Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final Dialog dialog1 = new Dialog(SignupActivity.this);
                    Typeface externalFont = Typeface.createFromAsset(SignupActivity.this.getAssets(), "fonts/Frank.ttf");
                    // String mess = SignupActivity.this.getResources().getString(R.string.internet_connection_dialog);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.alert_dialog_otp);
                    dialog1.setCancelable(true);
                    TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
                    text1.setText("OOPS !");
                    text1.setTypeface(externalFont, Typeface.BOLD);
                    etOTPField = (EditText) dialog1.findViewById(R.id.et_otp);
//                    text.setText("");
                    // text.setTypeface(externalFont);

                    Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);
                    Button buttonOk = (Button) dialog1.findViewById(R.id.buttonOk);


                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            etFirstName.setText("");
                            etLastName.setText("");
                            etEmail.setText("");
                            etMobile.setText("");
                            etPassword.setText("");
                            etFirstName.requestFocus();
                            dialog1.dismiss();

                        }
                    });
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e("SignupActivity", "OTP :" + otpStr);
                            if (Utility.isOnline(SignupActivity.this)) {
                                if (etOTPField.getText().toString().trim().length() != 0) {
                                    if (etOTPField.getText().toString().trim().equals(otpStr)) {
                                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                        SignupAsync mSignupAsync = new SignupAsync();
                                        mSignupAsync.execute();
                                        dialog1.dismiss();
                                    } else {
                                        Toast.makeText(SignupActivity.this, "OTP Not Matched !", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(SignupActivity.this, "Please Enter OTP !", Toast.LENGTH_LONG).show();
                                }

                            }  else {
                                Utility.InternetSetting(SignupActivity.this);
                            }


//                            dialog1.dismiss();
                        }
                    });
                    dialog1.show();

                } else {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
        }
    }


}

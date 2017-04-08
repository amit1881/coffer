package com.asmobisoft.coffer.registration;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.ChatRoomActivity;
import com.asmobisoft.coffer.chatroomfragment.ChatRoomFragment;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 2/1/16.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText etEmail;
    private LinearLayout llRoot;
    private EditText etPassword;
    private Dialog dialog;
    private ProgressDialog pDialog;
    private String strEmail;
    private String strPassword;
    private EditText etMobile;
    private String strMobile;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getView();

    }

    public void getView() {

        llRoot = (LinearLayout) findViewById(R.id.loginroot);
        // etEmail = (EditText)findViewById(R.id.et_email);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        etPassword = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.ll_login).setOnClickListener(this);
        findViewById(R.id.forgot_pass).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);
        findViewById(R.id.help).setOnClickListener(this);

        Log.e("FCM token=",""+Utility.getPrefsData(this,"FCMtoken","0"));
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity1
        moveTaskToBack(true);
        //overridePendingTransition(R.anim.slidout,R.anim.slidin);
    }


    public boolean validate() {
        boolean valid = true;

        // strEmail = etEmail.getText().toString();
        strPassword = etPassword.getText().toString();
        strMobile = etMobile.getText().toString();
//        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        Log.e("", "strMobile" + strMobile.length());

        if (etMobile.getText().toString().trim().length() == 0) {
            Toast.makeText(LoginActivity.this, "Please Enter Mobile Number !", Toast.LENGTH_SHORT).show();
        }

        if (etMobile.getText().toString().length() < 10 || etMobile.getText().toString().length() > 15) {
            etMobile.setError("Mobile Number should be between 10 and 14 alphanumeric characters");
            valid = false;
        } else {
            etMobile.setError(null);
        }

        if (strPassword.isEmpty() || strPassword.length() < 4 || strPassword.length() > 10) {
            etPassword.setError("Password should be between 4 to 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:


                Intent mIntent1 = new Intent(LoginActivity.this, SignupActivity.class);
                mIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //  mIntent.putExtra(Keys.PUTKEY_EMAIL, "No Email");
                startActivity(mIntent1);
                finish();

                /*if(etEmail.getText().toString().trim().length() == 0){

                    Intent mIntent = new Intent(LoginActivity.this,SignupActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.putExtra(Keys.PUTKEY_EMAIL, "No Email");
                    startActivity(mIntent);
                    finish();
                    //overridePendingTransition(R.anim.slidin,R.anim.slidout);

                }else{

                    Intent mIntent = new Intent(LoginActivity.this,SignupActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mIntent.putExtra(Keys.PUTKEY_EMAIL, etEmail.getText().toString().trim());
                    startActivity(mIntent);
                    finish();
                    //overridePendingTransition(R.anim.slidin,R.anim.slidout);
                }
*/
                break;
            case R.id.ll_login:

                if (validate()) {

                    if (Utility.isOnline(LoginActivity.this)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        new LoginAsync().execute();
                    } else {
                        Utility.InternetSetting(LoginActivity.this);
                    }

                }

                break;

            case R.id.help:

                WebView wvEula;
                // Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/frabk.ttf");
                dialog = new Dialog(LoginActivity.this);
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
            case R.id.forgot_pass:

                Intent mIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                //overridePendingTransition(R.anim.slidin,R.anim.slidout);

                break;
        }
    }


    private class LoginAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(LoginActivity.this);
//
            String responce = "";
            String url = Keys.LOGIN_URL + "&mobile=" + strMobile + "&password=" + strPassword;
            Log.e("Login", "URL : " + url);
            responce = mNetClientGet.getDataClientData(url);
            Log.e("Login", "responce : " + responce);
            return responce;

        }

        EditText etOTPField;
        private String fName, lName, mEmail, url, user_id;
        private String address, mobile;

        protected void onPostExecute(String result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        Log.e("Signup", "Hit OTP : " + jsonObj.getString("rtnMSG"));

                        if (jsonObj.getString("rtnMSG").equals("login successfully")) {

                            if (jsonObj.getString("state") != null) {
                                JSONArray state = jsonObj.getJSONArray("state");

                                for (int i = 0; i < state.length(); i++) {
                                    JSONObject c = state.getJSONObject(i);
                                    Log.e("", "Login Activity : FName " + c.getString("first_name") + "\n" + c.getString("last_name") + "\n"
                                            + c.getString("email") + "\n" + c.getString("mobile") + "\n" + c.getString("id"));
                                    user_id = c.getString("id");
                                    fName = c.getString("first_name");
                                    lName = c.getString("last_name");
                                    mEmail = c.getString("email");
                                    address = c.getString("address");
                                    mobile = c.getString("mobile");
                                    url = c.getString("profile_image");
                                }
                            }

                            Log.e("SignupActivity", "mobile number" + etMobile.getText().toString().trim());
                            Utility.setPrefsData(LoginActivity.this, "mobile", etMobile.getText().toString().trim());
                            Utility.setPrefsData(LoginActivity.this, "password", etPassword.getText().toString().trim());
                            Intent intent = new Intent(getApplicationContext(), ChatRoomActivity .class);
                            Utility.setPrefsData(LoginActivity.this, "full_name", fName + " " + lName);
                            Utility.setPrefsData(LoginActivity.this, "mEmail", mEmail);
                            Utility.setPrefsData(LoginActivity.this, "user_id",user_id);
                            Utility.setPrefsData(LoginActivity.this, "url", url);
                            Utility.setPrefsData(LoginActivity.this, "address", address.replaceAll("%20", " "));
                            Utility.setPrefsData(LoginActivity.this, "mobile", mobile);
                            intent.putExtra("screenValue", "login");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this,
                                    jsonObj.getString("rtnMSG"),
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.internet_connection_dialog),
                            Toast.LENGTH_LONG).show();
                }


            }


        }
    }

}

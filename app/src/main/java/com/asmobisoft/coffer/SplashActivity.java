package com.asmobisoft.coffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.activity.ChatRoomActivity;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.database.SqliteDb;
import com.asmobisoft.coffer.model.ProvidersData;
import com.asmobisoft.coffer.registration.LoginActivity;
import com.asmobisoft.coffer.webservices.NetClientGet;
import com.crittercism.app.Crittercism;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 2/1/16.
 */

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SLEEP_TIME = 3;//Splash timing in sec
    private IntentLauncher launcher;
    private ProgressBar progressbar;
    private TextView tvProgressText;
    private SqliteDb mSqliteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        Crittercism.initialize(getApplicationContext(), "46236cfdcc2643ac923d3919de9a7f3400555300");

        mSqliteDb = new SqliteDb(SplashActivity.this);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        tvProgressText = (TextView)findViewById(R.id.tv_progress);

        mSqliteDb.deletData();

        if(Utility.isOnline(SplashActivity.this)){
            new ProviderAsync().execute();
        }else{
            Utility.InternetSetting(SplashActivity.this);
           //this.finish();
        }

        launcher = new IntentLauncher();
        launcher.start();

    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity1
        moveTaskToBack(true);
       // overridePendingTransition(R.anim.slidout,R.anim.slidin);
    }
    private ProgressDialog mProgressDialog;

    private class ProviderAsync extends AsyncTask<String, String, String> {

        private String strValue;

        public ProviderAsync() {
//            this.strValue = strValue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressbar.setVisibility(View.VISIBLE);
            tvProgressText.setVisibility(View.VISIBLE);
          /*  mProgressDialog = new ProgressDialog(SplashActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();*/

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(SplashActivity.this);
            String responce = "";

            String url = Keys.ALL_PROVIDERS + Keys.API_KEY;
            Log.e("Login", "URL : " + url);

            responce = mNetClientGet.getDataClientData(url);
            Log.e("Login", "responce : " + responce);

            return responce;

        }

        protected void onPostExecute(String result) {

            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {

                    String data = "";
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);

                        //Get the instance of JSONArray that contains JSONObjects
                        JSONArray jsonArray = jsonRootObject.optJSONArray("providers");

                        // mProvidersData.setProvider_name("SelectProvider");
                        //Iterate the jsonArray and print the info of JSONObjects
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ProvidersData mProvidersData = new ProvidersData();

                            if (!jsonObject.optString("service").toString().equals("")) {

                                if (!jsonObject.optString("provider_id").toString().equals("")) {
                                    mProvidersData.setProvider_id(Integer.parseInt(jsonObject.optString("provider_id").toString()));
                                } else {
                                    mProvidersData.setProvider_id(0);
                                }

                                if (!jsonObject.optString("provider_name").toString().equals("")) {
                                    mProvidersData.setProvider_name(jsonObject.optString("provider_name").toString());
                                } else {
                                    mProvidersData.setProvider_name("");
                                }

                                if (!jsonObject.optString("provider_code").toString().equals("")) {
                                    mProvidersData.setProvider_code(jsonObject.optString("provider_code").toString());
                                } else {
                                    mProvidersData.setProvider_code("");
                                }

                                if (!jsonObject.optString("status").toString().equals("")) {
                                    mProvidersData.setStatus(jsonObject.optString("status").toString());
                                } else {
                                    mProvidersData.setStatus("");
                                }

                                if (!jsonObject.optString("service").toString().equals("")) {
                                    mProvidersData.setService(jsonObject.optString("service").toString());
                                } else {
                                    mProvidersData.setService("");
                                }

                                if (!jsonObject.optString("provider_image").toString().equals("")) {
                                    mProvidersData.setProvider_image(jsonObject.optString("provider_image").toString());
                                } else {
                                    mProvidersData.setProvider_image("");
                                }
                                // providerList.add(mProvidersData);
                                mSqliteDb.addProvider(mProvidersData);

                            } else {
                                // mProvidersData.setService("");
                            }

                            progressbar.setVisibility(View.INVISIBLE);
                            tvProgressText.setVisibility(View.INVISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                Toast.makeText(SplashActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
            }
        }
    }


    private class IntentLauncher extends Thread {

        public void run() {
            try {
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                // TODO: handle exception
            }

            String strmobile = Utility.getPrefsData(SplashActivity.this,"mobile","");
            Log.e("Splash Activity","strmobile vlaue : "+strmobile);
            if(strmobile.length() > 0){
                Intent i = new Intent(SplashActivity.this, ChatRoomActivity.class);
                i.putExtra("screenValue","splash");
                startActivity(i);
                // close this activity
                finish();
            }else{
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }

            //Animator Part going here
           // overridePendingTransition(R.anim.slidin,R.anim.slidout);
        }
    }

}

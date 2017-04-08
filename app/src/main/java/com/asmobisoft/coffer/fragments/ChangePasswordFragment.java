package com.asmobisoft.coffer.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordFragment extends Fragment {


    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog mProgressDialog;
    private String strNewPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    private EditText etNewPass;
    private EditText etRetype;
    private Button btnChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);

        getid(v);

        return v;
    }

    private void getid(View v) {


        etNewPass = (EditText) v.findViewById(R.id.et_new);
        etRetype = (EditText) v.findViewById(R.id.et_retype);
        btnChangePassword = (Button) v.findViewById(R.id.btn_update);

        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isvalidate()) {

                    if (Utility.isOnline(getActivity())) {
                        strNewPassword = etNewPass.getText().toString().trim();
                        ChangepassWord mOTPAsync = new ChangepassWord(strNewPassword);
                        mOTPAsync.execute();
                    } else {
                        Utility.InternetSetting(getActivity());
                    }

                }
            }


        });
    }

    private boolean isvalidate() {

        if (etNewPass.getText().toString().trim().length() == 0) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please enter new password !", Snackbar.LENGTH_LONG);

            snackbar.show();
            //Toast.makeText(getActivity(), "Please enter new password !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etNewPass.getText().toString().trim().length() <= 5) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please enter new password of min length 6 !", Snackbar.LENGTH_LONG);

            snackbar.show();

            // Toast.makeText(getActivity(), "Please enter new password of min length 6 !", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (etRetype.getText().toString().trim().length() == 0) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please enter retype password !", Snackbar.LENGTH_LONG);

            snackbar.show();

            //  Toast.makeText(getActivity(), "Please enter retype password !", Toast.LENGTH_SHORT).show();
            return false;
        }
      /*  if(etNewPass.getText().toString().trim().length() <=5 ){

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Please enter retype of min length 6 !", Snackbar.LENGTH_LONG);

            snackbar.show();

            //Toast.makeText(getActivity(), "Please enter retype of min length 6 !", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        if (!etNewPass.getText().toString().trim().equals(etRetype.getText().toString().trim())) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "New password and retype password should be the same !", Snackbar.LENGTH_LONG);

            snackbar.show();
            //Toast.makeText(getActivity(), "New password and retype password should be the same !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private class ChangepassWord extends AsyncTask<String, String, String> {

        EditText etOTPField;
        private String strNewPassword;

        public ChangepassWord(String strNewPassword) {
            this.strNewPassword = strNewPassword;
            //  this.password=password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(getActivity());
//
            String responce = "";
            //http://webservice.cofferwallet.com/cofferdb.php?task=forgetpassword&mobile=7827505727654321
            String strMob = Utility.getPrefsData(getActivity(), "mobile", "no");
            if (!strMob.equals("no")) {
                String url = Keys.FORGOT_PASS_URL + "&mobile=" + strMob + "&password=" + strNewPassword;
                Log.e("Signup", "URL : " + url);

                responce = mNetClientGet.getDataClientData(url);
                Log.e("Signup", "responce : " + responce);
            } else {
                responce = "";
            }

            return responce;


        }

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

                        if (jsonObj.getString("rtnMSG").equals("Password Reset successfully")) {
                            // Log.e("Signup", "Password Status : " + );

                            Toast.makeText(getActivity(), jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                            etNewPass.setText("");
                            etRetype.setText("");

                        } else {
                            Toast.makeText(getActivity(), jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}

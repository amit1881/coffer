package com.asmobisoft.coffer.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.CustomAdapter;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.database.SqliteDb;
import com.asmobisoft.coffer.model.ProvidersData;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class MobileFragment extends Fragment implements View.OnClickListener {


    private LinearLayout llTab1View;
    private Spinner spnrMobile;

    private Button btnRechargeNow;
    private String TAG = "MobileFragment";
    private EditText et_mobile_number, et_amount;

    // TODO - insert your API KEY obtained from pay2all.com here
    private final static String API_KEY = "PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";

    private CustomAdapter mSpinnerAdapeter;
    ArrayList<ProvidersData> providerList = new ArrayList<ProvidersData>();
    private String amount;
    private String mobileNumber;
    private int strProvider;
    private LinearLayout ll_pre, ll_pos;
    private ImageView ivPre, icPost;
    private SqliteDb mSqliteDb;


    public MobileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mobile_recharge, container, false);
        // Utility.hideSoftKeyboard(getActivity());
        mSqliteDb = new SqliteDb(getActivity());

        llTab1View = (LinearLayout) v.findViewById(R.id.ll_tab1_view);
        spnrMobile = (Spinner) v.findViewById(R.id.spnr_oprator);
        //radioMobileGroup = (RadioGroup) v.findViewById(R.id.radio_mobile);
        btnRechargeNow = (Button) v.findViewById(R.id.btn_recharge_now);
        et_mobile_number = (EditText) v.findViewById(R.id.et_mobile_number);
        et_amount = (EditText) v.findViewById(R.id.et_amount);
        ll_pre = (LinearLayout) v.findViewById(R.id.ll_pre);
        ll_pos = (LinearLayout) v.findViewById(R.id.ll_post);
        ivPre = (ImageView) v.findViewById(R.id.iv_prepaid);
        icPost = (ImageView) v.findViewById(R.id.iv_postpaid);

        ivPre.setImageResource(R.mipmap.click);
        icPost.setImageResource(R.mipmap.un_click);

        SetDataOnSpinner("pre");


        ll_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPre.setImageResource(R.mipmap.click);
                icPost.setImageResource(R.mipmap.un_click);

                SetDataOnSpinner("pre");

            }
        });

        ll_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPre.setImageResource(R.mipmap.un_click);
                icPost.setImageResource(R.mipmap.click);

                SetDataOnSpinner("post");


            }
        });

        btnRechargeNow.setOnClickListener(this);
        //Toast.makeText(getActivity(), "radioMobileButton"+radioMobileButton.getText().toString(), Toast.LENGTH_SHORT).show();

        // Listener called when spinner item selected
        spnrMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                try {
                    // Get selected row data to show on screen
                    //   String name = ((TextView) v.findViewById(R.id.tv_spinner_text)).getText().toString();
                    strProvider = providerList.get(position).getProvider_id();
                    //   String CompanyUrl = ((TextView) v.findViewById(R.id.sub)).getText().toString();
                    //  TextView output = null;
                    //   String OutputMsg = "Selected Company : \n\n" + name;
                    Log.e("MobileScreen", "Provider " + strProvider);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // output.setText(OutputMsg);

                //  Toast.makeText(getActivity(),OutputMsg, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_recharge_now:

                if (isValidate()) {
                    if (Utility.isOnline(getActivity())) {
                        amount = et_amount.getText().toString().trim();
                        mobileNumber = et_mobile_number.getText().toString().trim();
                        Log.e("strProvider", "strProvider : " + strProvider);
                        RechargeAsync mRechargeAsync = new RechargeAsync();
                        mRechargeAsync.execute();
                    } else {
                        Utility.InternetSetting(getActivity());
                    }
                }

                break;
        }


    }

    private ProgressDialog mProgressDialog;

    private class RechargeAsync extends AsyncTask<String, String, String> {

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
            String responce = "";
            String clientID = Utility.getPrefsData(getActivity(), "mobile", "");
            if (clientID.equals("")) {
                String countid = String.valueOf(UUID.randomUUID());
                String url = Keys.MOBILE_RECHARGE + API_KEY + "&number=" + mobileNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + countid;
                Log.e("Login", "URL : " + url);

                responce = mNetClientGet.getDataClientData(url);
                Log.e("Login", "responce : " + responce);
            } else {
                String url = Keys.MOBILE_RECHARGE + API_KEY + "&number=" + mobileNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + clientID;
                Log.e("Login", "URL : " + url);

                responce = mNetClientGet.getDataClientData(url);
                Log.e("Login", "responce : " + responce);
            }

            return responce;

        }

        Button btnCancel;

        protected void onPostExecute(String result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {

                    String data = "";
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);
                        if (jsonRootObject.getString("status") != null) {
                            Toast.makeText(getActivity(), jsonRootObject.getString("status"), Toast.LENGTH_LONG).show();
                            if (jsonRootObject.getString("status").equals("success")) {

                                final Dialog dialog1 = new Dialog(getActivity());

                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog1.setContentView(R.layout.custom_dialog_with_onebutton);
                                dialog1.setCancelable(true);

                                TextView text1 = (TextView) dialog1.findViewById(R.id.tv_tittle1);
                                text1.setText(Keys.CONG);
                                // text1.setTypeface(externalFont, Typeface.BOLD);
                                text1.setTextSize(18);

                                TextView text = (TextView) dialog1.findViewById(R.id.tv_subtittle);
                                text.setText(Keys.SUCCESS_MESSGAE);
                                text.setTextSize(14);
                                // text.setTypeface(externalFont);

                                btnCancel = (Button) dialog1.findViewById(R.id.btn_ok);

                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            dialog1.dismiss();

                                            Intent i = new Intent(getActivity(), MainActivity1.class);
                                            i.putExtra("screenValue", "recharge");
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                        }

                                    }
                                });

                                dialog1.show();

                            } else if (jsonRootObject.getString("status").equals("Pending")) {
                                Utility.showAlertwithOneButton(getActivity(), Keys.OOPS, Keys.PENDING_MESSGAE);
                            } else if (jsonRootObject.getString("status").equals("Initiated")) {
                                Utility.showAlertwithOneButton(getActivity(), Keys.OOPS, Keys.INITIATED_MESSGAE);
                            } else {
                                Utility.showAlertwithOneButton(getActivity(), Keys.OHOO, Keys.FAILED_MESSGAE);
                            }

                        } else {
                            Toast.makeText(getActivity(), "Something went Wrong ,Please try again later !", Toast.LENGTH_SHORT).show();
                        }
                        et_mobile_number.setText("");
                        et_amount.setText("");
                        SetDataOnSpinner("pre");
                        ivPre.setImageResource(R.mipmap.click);
                        icPost.setImageResource(R.mipmap.un_click);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isValidate() {

        if (et_mobile_number.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter mobile number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_mobile_number.getText().toString().trim().length() < 10 || et_mobile_number.getText().toString().trim().length() > 15) {
            Toast.makeText(getActivity(), "Please enter valid mobile number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (strProvider == 100000) {
            Toast.makeText(getActivity(), "Please select a provider !", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (et_amount.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter amount!", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private void SetDataOnSpinner(String service) {

        if (service.equals("pre")) {
            providerList = mSqliteDb.getALLProvider(Keys.MOBILE_SERVICE);
            Log.e("MobileFragment", "Pree Size :" + providerList.size());
            Log.e("MobileFragment", "Pree Size :" + providerList.get(0).getProvider_name());

            mSpinnerAdapeter = new CustomAdapter(getActivity(), R.layout.spinner_row, providerList);
            spnrMobile.setAdapter(mSpinnerAdapeter);
        } else if (service.equals("post")) {
            providerList = mSqliteDb.getALLProvider(Keys.POSTPAID);

            mSpinnerAdapeter = new CustomAdapter(getActivity(), R.layout.spinner_row, providerList);
            spnrMobile.setAdapter(mSpinnerAdapeter);
        }
    }
}

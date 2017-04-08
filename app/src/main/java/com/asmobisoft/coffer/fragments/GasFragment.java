package com.asmobisoft.coffer.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by didi1 on 31-03-2017.
 */

public class GasFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Spinner spnrMobile;
    private CustomAdapter mSpinnerAdapeter;
    private String gasNumber;
    private String amount;
    private Button btnCancel;

    public GasFragment() {
        // Required empty public constructor
    }

    ArrayList<ProvidersData> providerList = new ArrayList<ProvidersData>();
    private int strProvider;
    private EditText et_electricity_number, et_amount_electricity;
    private Button btn_recharge_now;
    private SqliteDb mSqliteDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_electric_bill, container, false);
        // Inflate the layout for this fragment
        mSqliteDb = new SqliteDb(getActivity());
        spnrMobile = (Spinner) v.findViewById(R.id.spnr_oprator);
        et_electricity_number = (EditText) v.findViewById(R.id.et_electricity_number);
        et_amount_electricity = (EditText) v.findViewById(R.id.et_amount_electricity);
        btn_recharge_now = (Button) v.findViewById(R.id.btn_recharge_now);

        btn_recharge_now.setOnClickListener(this);

       et_electricity_number.setVisibility(View.GONE);
       et_amount_electricity.setVisibility(View.GONE);
        et_electricity_number.setHint("GAS Bill Number");
        //btn_recharge_now.setVisibility(View.GONE);
        SetDataOnSpinner();


        // Listener called when spinner item selected
        spnrMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                // Get selected row data to show on screen
                //  String name = ((TextView) v.findViewById(R.id.tv_spinner_text)).getText().toString();
                strProvider = providerList.get(position).getProvider_id();
                if (strProvider != 100000) {

                    et_electricity_number.setVisibility(View.VISIBLE);
                    et_amount_electricity.setVisibility(View.VISIBLE);

                } else {
                    et_electricity_number.setVisibility(View.GONE);
                    et_amount_electricity.setVisibility(View.GONE);

                }

                Log.e("MobileScreen", "Provider " + strProvider);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return v;
    }


    private ProgressDialog mProgressDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge_now:

                if (isValidate()) {
                    if (Utility.isOnline(getActivity())) {

                        gasNumber = et_electricity_number.getText().toString();
                        amount = et_amount_electricity.getText().toString();

                        new RechargeAsync().execute();

                    } else {
                        Utility.InternetSetting(getActivity());
                    }

                }

                break;
        }


    }


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
                String url = Keys.GAS_BILL + Keys.API_KEY + "&number=" + gasNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + countid + "&cnumber=" + clientID;

                Log.e("Login", "URL : " + url);
                responce = mNetClientGet.getDataClientData(url);
                Log.e("Login", "responce : " + responce);

            } else {

                String countid = String.valueOf(UUID.randomUUID());
                clientID = "9899077656";
                String url = Keys.ELECTRICITY_BILL + Keys.API_KEY + "&number=" + gasNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + countid + "&cnumber=" + clientID;
                Log.e("Login", "URL : " + url);
                responce = mNetClientGet.getDataClientData(url);
                Log.e("Login", "responce : " + responce);

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
                    // Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
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
                        et_amount_electricity.setText("");
                        et_electricity_number.setText("");

                        SetDataOnSpinner();

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

        if (spnrMobile.getSelectedItem().equals("Please select provider")) {
            Toast.makeText(getActivity(), "Please select a provider !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_electricity_number.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter Gas number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (et_amount_electricity.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter amount!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void SetDataOnSpinner() {

        providerList = mSqliteDb.getALLProvider(Keys.GAS);
        mSpinnerAdapeter = new CustomAdapter(getActivity(), R.layout.spinner_list, providerList);
        spnrMobile.setAdapter(mSpinnerAdapeter);
    }


}

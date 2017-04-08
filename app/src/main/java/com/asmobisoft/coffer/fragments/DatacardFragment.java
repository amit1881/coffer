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

public class DatacardFragment extends Fragment {


    private LinearLayout llTab2;
    private View vData;
    private LinearLayout llTab2View;
    private Spinner spnrData;
    private EditText etDataCardNumber;
    private EditText etAmountData;

    private SqliteDb mSqliteDb;
    ArrayList<ProvidersData> providerList = new ArrayList<ProvidersData>();
    private String amount;
    private String datacardNumber;
    private Button btn_submit;
    private Button btnCancel;

    public DatacardFragment() {
        // Required empty public constructor
    }

    public int strProvider;
    private CustomAdapter mSpinnerAdapeter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datacard_recharge, container, false);

        mSqliteDb = new SqliteDb(getActivity());
        etDataCardNumber = (EditText) v.findViewById(R.id.et_data_card_number);
        etAmountData = (EditText) v.findViewById(R.id.et_amount_data);
        llTab2View = (LinearLayout) v.findViewById(R.id.ll_tab2_view);
        spnrData = (Spinner) v.findViewById(R.id.spnr_oprator_data_card);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);

        SetDataOnSpinner();
        // Listener called when spinner item selected
        spnrData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                // Get selected row data to show on screen
                //  String name = ((TextView) v.findViewById(R.id.tv_spinner_text)).getText().toString();
                strProvider = providerList.get(position).getProvider_id();
                //   String CompanyUrl = ((TextView) v.findViewById(R.id.sub)).getText().toString();
                //  TextView output = null;
                // String OutputMsg = "Selected Company : \n\n" + name;
                Log.e("MobileScreen", "Provider " + strProvider);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idvalidate()) {
                    if (Utility.isOnline(getActivity())) {
                        datacardNumber = etDataCardNumber.getText().toString().trim();
                        amount = etAmountData.getText().toString().trim();
                        Log.e("strProvider", "strProvider : " + strProvider);
                        DataCardAsync mRechargeAsync = new DataCardAsync();
                        mRechargeAsync.execute();
                    } else {
                        Utility.InternetSetting(getActivity());
                    }
                }

            }
        });

        return v;
    }


    private ProgressDialog mProgressDialog;

    private boolean idvalidate() {

        if (etDataCardNumber.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Please enter DATACARD number !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (strProvider == 100000) {
            Toast.makeText(getActivity(), "Please select a provider !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etAmountData.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Please enter Amount !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private class DataCardAsync extends AsyncTask<String, String, String> {

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
                //https://www.pay2all.in/web-api/paynow?api_token=api_token&number=customer_number&provider_id
                // =prvoider_id&amount=amount&client_id=clientuniqueid&cnumber=cnumber

                String url = Keys.MOBILE_RECHARGE + Keys.API_KEY + "&number=" + datacardNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + countid + "&cnumber=" + clientID;
                Log.e("Login", "URL : " + url);
                responce = mNetClientGet.getDataClientData(url);
                Log.e("Login", "responce : " + responce);

            } else {
                String url = Keys.MOBILE_RECHARGE + Keys.API_KEY + "&number=" + datacardNumber + "&provider_id=" + strProvider + "&amount="
                        + amount + "&client_id=" + clientID + "&cnumber=" + clientID;
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
                    //  Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
                    String data = "";
                    try {
                        JSONObject jsonRootObject = new JSONObject(result);
                        //    Toast.makeText(getActivity(), jsonRootObject.getString("message"), Toast.LENGTH_LONG).show();
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

                        etDataCardNumber.setText("");
                        etAmountData.setText("");

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


    private void SetDataOnSpinner() {

        providerList = mSqliteDb.getALLProvider(Keys.DATACARD);
        mSpinnerAdapeter = new CustomAdapter(getActivity(), R.layout.spinner_row, providerList);
        spnrData.setAdapter(mSpinnerAdapeter);
    }

}

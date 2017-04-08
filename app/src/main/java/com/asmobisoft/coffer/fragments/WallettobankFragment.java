package com.asmobisoft.coffer.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.BeneficiaryListActivity;
import com.asmobisoft.coffer.adapter.CardViewDataAdapter;
import com.asmobisoft.coffer.adapter.CustmSpinnerAdapter;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.Beneficierymodel;
import com.asmobisoft.coffer.model.BeniListForSpinner;
import com.asmobisoft.coffer.requestparser.RequestAll;
import com.asmobisoft.coffer.responceparser.ResponceBeniAll;
import com.asmobisoft.coffer.responceparser.ResponceTransection;
import com.asmobisoft.coffer.webservices.CallPostService;
import com.asmobisoft.coffer.webservices.NetClientGet;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WallettobankFragment extends Fragment {

    private View v;
    //private Button btnBenifeatryList;
    private ProgressDialog mProgressDialog;
    private EditText tv_account_number,bank_name,etAmount,et_ifsc,et_amount;
    private Button btnTransfer;
    private ResponceBeniAll mResponceBeniParser;
    private BeniListForSpinner mBeniListForSpinner;
    private Gson gson;
    private LinearLayout ll_progress;
    private  String balance;
    private ResponceTransection mResponceTransection;

    public WallettobankFragment(String balance) {
        // Required empty public constructor
        this.balance = balance;
    }
    private TextView tv_total_balence;
    private CustmSpinnerAdapter custmSpinnerAdapter;
    private Spinner spnrChanel;
    private Spinner spnrBeni;
    private String strBaniCode;
    private String strBaniName;
    private String strBaniAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_wallet_to_bank, container, false);

        ll_progress= (LinearLayout)v.findViewById(R.id.ll_progress_transfer);
        bank_name = (EditText)v.findViewById(R.id.name_of_bank);
        tv_account_number = (EditText)v.findViewById(R.id.tv_account_number);
        et_ifsc = (EditText) v.findViewById(R.id.et_ifsc);
        et_amount = (EditText) v.findViewById(R.id.et_amount);
        tv_total_balence = (TextView) v.findViewById(R.id.tv_total_balence);
        tv_total_balence.setText("\u20B9 "+balance);
        btnTransfer= (Button)v.findViewById(R.id.btn_transfer);

     /*   btnBenifeatryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), BeneficiaryListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });*/

        if (Utility.isOnline(getActivity())) {
          //  BalanceAsync mRechargeAsync = new BalanceAsync();
         //   mRechargeAsync.execute();

        } else {
            Utility.InternetSetting(getActivity());
        }

/*//        spnrBeni.setVisibility(View.GONE);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select A Chanel");
        categories.add("IMPS");
        categories.add("NEFT");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spnrChanel.setAdapter(dataAdapter);

        // Listener called when spinner item selected
        spnrBeni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                // Get selected row data to show on screen
                String Company    = ((TextView) v.findViewById(R.id.tv_spinner)).getText().toString();
              //  String CompanyUrl = ((TextView) v.findViewById(R.id.sub)).getText().toString();

                String OutputMsg = "Selected Company : \n"+mSpinnerLsit.get(position).getBeniName()+"\n"
                        +mSpinnerLsit.get(position).getBenicode();
                strBaniCode =mSpinnerLsit.get(position).getBenicode();
                strBaniName =mSpinnerLsit.get(position).getBeniName();
                strBaniAccount =mSpinnerLsit.get(position).getBeniAccount();

                //output.setText(OutputMsg);
                Log.e("ActivityWtoB","Spinner Value"+strBaniCode+"\n"+strBaniName+"\n"+strBaniAccount);
                //Toast.makeText(getActivity(),OutputMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/

        btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidated()){
                    TransferAsync mTransferAsync = new TransferAsync();
                    mTransferAsync.execute();
                }

            }
        });


        return v;
    }

    private boolean isValidated() {
        
        if(spnrChanel.getSelectedItem().toString().equals("Select A Chanel")){
            Toast.makeText(getActivity(), "Please Select a Chanel", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(spnrBeni.getSelectedItem().toString().equals("Select Beneficiary")){
            Toast.makeText(getActivity(), "Please Select a Chanel", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etAmount.getText().toString().length() == 0){
            Toast.makeText(getActivity(), "Please enter amount !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private class BalanceAsync extends AsyncTask<String, String, String> {

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

            String url = Keys.GET_BALENCE;
            Log.e("Login", "URL : " + url);
            responce = mNetClientGet.getDataClientData(url);

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
                        if (jsonRootObject.getString("balance") != null) {
                            //  Toast.makeText(getActivity(), jsonRootObject.getString("balance"), Toast.LENGTH_LONG).show();

                            tv_total_balence.setTextSize(25);
                            tv_total_balence.setText("\u20B9 "+jsonRootObject.getString("balance"));


                        } else {
                            Toast.makeText(getActivity(), "Something went Wrong ,Please try again later !", Toast.LENGTH_SHORT).show();
                        }

                        if (Utility.isOnline(getActivity())) {
                            ll_progress.setVisibility(View.VISIBLE);

                            BenificiaryAsync mBenificiaryAsync = new BenificiaryAsync();
                            mBenificiaryAsync.execute();


                        } else {
                            Utility.InternetSetting(getActivity());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
            }
        }
    }
    private ArrayList<Beneficierymodel> mBeniList;
    private ArrayList<BeniListForSpinner> mSpinnerLsit = new ArrayList<BeniListForSpinner>();

    private class BenificiaryAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  ll_progress.setVisibility(View.VISIBLE);*/
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
            ll_progress.setVisibility(View.GONE);
            mBeniList = new ArrayList<Beneficierymodel>();
            spnrBeni.setVisibility(View.VISIBLE);

            Beneficierymodel mBeneficiery;
            BeniListForSpinner mBeniListForSpinner;
            mBeniListForSpinner = new BeniListForSpinner();
            mBeniListForSpinner.setBeniName("Select Beneficiary");
            mBeniListForSpinner.setBeniId("12121");
            mBeniListForSpinner.setBenicode("0");
            mBeniListForSpinner.setBeniBank("Demo");
            mBeniListForSpinner.setBeniAccount("4155487412454");
            mSpinnerLsit.add(mBeniListForSpinner);

            if (mResponceBeniParser != null) {
                Log.e("parser Size", "Size : " + mResponceBeniParser.status);
                if (mResponceBeniParser.status.equals("0")) {


                    for (int i = 0; i < mResponceBeniParser.data.size(); i++) {
                        //Log.e("History Section Service","Oprators : "+mResponceBeniParser.data.get(i).getProvider());
                        mBeneficiery = new Beneficierymodel();
                        mBeniListForSpinner = new BeniListForSpinner();

                        if (mResponceBeniParser.data.get(i).account != null) {
                            mBeneficiery.setAccount(mResponceBeniParser.data.get(i).account);
                        } else {
                            mBeneficiery.setAccount("");
                        }
                        if (mResponceBeniParser.data.get(i).BankName != null) {
                            mBeneficiery.setBankName(mResponceBeniParser.data.get(i).BankName);
                        } else {
                            mBeneficiery.setBankName("");
                        }
                        if (mResponceBeniParser.data.get(i).BeneficiaryCode != null) {
                            mBeneficiery.setBeneficiaryCode(mResponceBeniParser.data.get(i).BeneficiaryCode);
                        } else {
                            mBeneficiery.setBeneficiaryCode("");
                        }
                        if (mResponceBeniParser.data.get(i).ifsc != null) {
                            mBeneficiery.setIfsc(mResponceBeniParser.data.get(i).ifsc);
                        } else {
                            mBeneficiery.setIfsc("");
                        }

                        if (mResponceBeniParser.data.get(i).OrgAckNo != null) {
                            mBeneficiery.setOrgAckNo(mResponceBeniParser.data.get(i).OrgAckNo);
                        } else {
                            mBeneficiery.setOrgAckNo("");
                        }

                        if (mResponceBeniParser.data.get(i).recipient_id != null) {
                            mBeneficiery.setRecipient_id(mResponceBeniParser.data.get(i).recipient_id);
                        } else {
                            mBeneficiery.setRecipient_id("");
                        }

                        if (mResponceBeniParser.data.get(i).recipient_name != null) {
                            mBeneficiery.setRecipient_name(mResponceBeniParser.data.get(i).recipient_name);
                        } else {
                            mBeneficiery.setRecipient_name("");
                        }

                        if (mResponceBeniParser.data.get(i).OrgTransRefNum != null) {
                            mBeneficiery.setOrgTransRefNum(mResponceBeniParser.data.get(i).OrgTransRefNum);
                        } else {
                            mBeneficiery.setOrgTransRefNum("");
                        }
                        if (mResponceBeniParser.data.get(i).status != null) {
                            mBeneficiery.setStatus(mResponceBeniParser.data.get(i).status);

                            if (mResponceBeniParser.data.get(i).status.equals("ACTIVE")) {
                                //mBeneficiery.setStatus(mResponceBeniParser.data.get(i).status);
                                mBeniListForSpinner.setBeniName(mResponceBeniParser.data.get(i).recipient_name);
                                mBeniListForSpinner.setBeniId(mResponceBeniParser.data.get(i).recipient_id);
                                mBeniListForSpinner.setBenicode(mResponceBeniParser.data.get(i).BeneficiaryCode);
                                mBeniListForSpinner.setBeniBank(mResponceBeniParser.data.get(i).BankName);
                                mBeniListForSpinner.setBeniAccount(mResponceBeniParser.data.get(i).account);
                                mSpinnerLsit.add(mBeniListForSpinner);

                            } else {
                                mBeneficiery.setStatus("");
                            }

                        } else {
                            mBeneficiery.setStatus("");
                        }

                        mBeniList.add(mBeneficiery);
                    }



                    // Create custom adapter object ( see below CustomAdapter.java )
                    custmSpinnerAdapter = new CustmSpinnerAdapter(getActivity(), mSpinnerLsit);
                    // Set adapter to spinner
                    spnrBeni.setAdapter(custmSpinnerAdapter);

                    // Listener called when spinner item selected

                } else {
                    Toast.makeText(getActivity(), "No Beneficiary in your list !", Toast.LENGTH_SHORT).show();
                    // Create custom adapter object ( see below CustomAdapter.java )
                    custmSpinnerAdapter = new CustmSpinnerAdapter(getActivity(), mSpinnerLsit);
                    // Set adapter to spinner
                    spnrBeni.setAdapter(custmSpinnerAdapter);

                }
            } else {
                Toast.makeText(getActivity(), "No Beneficiary in your list !", Toast.LENGTH_SHORT).show();

                // Create custom adapter object ( see below CustomAdapter.java )
                custmSpinnerAdapter = new CustmSpinnerAdapter(getActivity(), mSpinnerLsit);
                // Set adapter to spinner
                spnrBeni.setAdapter(custmSpinnerAdapter);

            }


        }
    }

  //  CardViewDataAdapter mAdapter;

    private String callBeniList() throws IOException {

        gson = new Gson();
        String responce = "";

        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = Utility.getPrefsData(getActivity(),"mobile","0");

        Log.e("BeniList", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(getActivity());
        responce = mPostService.getJsonResponce(Keys.GET_Beneficiary_DETAIL, gson.toJson(mAllRequest));

        Log.e("benilist", "responce : " + responce);

        if (responce != null) {
            mResponceBeniParser = gson.fromJson(responce, ResponceBeniAll.class);
        } else {
            responce = null;
        }
        return responce;
    }


    //Call Service of transfer

    private class TransferAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                callTransferService();
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

            if(mResponceTransection!=null){

                if(!mResponceTransection.message.equals("Insufficient Fund, Refill your wallet")){
                    Toast.makeText(getActivity(), mResponceTransection.message, Toast.LENGTH_SHORT).show();
                    Utility.showAlertwithTwoTitle(getActivity(),"WOW !",mResponceTransection.message);
                    etAmount.setText("");
                }else{
                    Toast.makeText(getActivity(), mResponceTransection.message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //  CardViewDataAdapter mAdapter;
    private String callTransferService() throws IOException {

        gson = new Gson();
        String responce = "";

        /*api_token,mobile_number,BeneficiaryCode,bank_account,amount,channel,client_id*/

        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = Utility.getPrefsData(getActivity(),"mobile","0");
        mAllRequest.BeneficiaryCode = strBaniCode;
        mAllRequest.bank_account = strBaniAccount;
        mAllRequest.amount = etAmount.getText().toString();

        if(spnrChanel.getSelectedItem().toString().equals("IMPS")){
            mAllRequest.channel = "2";
        }else{
            mAllRequest.channel = "1";
        }
        mAllRequest.client_id = Utility.getPrefsData(getActivity(),"mobile","0");

        Log.e("BeniList", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(getActivity());
        responce = mPostService.getJsonResponce(Keys.TRANSECTION, gson.toJson(mAllRequest));

        Log.e("benilist", "responce : " + responce);

        if (responce != null) {
            mResponceTransection = gson.fromJson(responce, ResponceTransection.class);
        } else {
            responce = null;
        }
        return responce;
    }


}

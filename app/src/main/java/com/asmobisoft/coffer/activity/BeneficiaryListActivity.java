package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.CardViewDataAdapter;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.RecyclerItemClickListener;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.Beneficierymodel;
import com.asmobisoft.coffer.requestparser.RequestAll;
import com.asmobisoft.coffer.responceparser.ResponceBeniAll;
import com.asmobisoft.coffer.webservices.CallPostService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class BeneficiaryListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvBeneficiary;
    private CardViewDataAdapter mCardViewDataAdapter;
    private LinearLayout llViewVisibile;
    private LinearLayout llProgress;
    //private ListView listHistory;
    private LinearLayout llNetworkConnextion;
    private Gson gson;
    private Beneficierymodel mBeneficierymodel;
    private ResponceBeniAll mResponceBeniParser;
    private ImageView ivNetwork;
    private String mobileNumber;
    private Button btnAddBeni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Beneficiary List");

        btnAddBeni = (Button) findViewById(R.id.btn_add_beni);


        btnAddBeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(BeneficiaryListActivity.this, AddBeniActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        mobileNumber = Utility.getPrefsData(BeneficiaryListActivity.this, "mobile", "");
        Log.e("BeniActivity", "mobileNumber : " + mobileNumber);
        getViewID();

        if (Utility.isOnline(BeneficiaryListActivity.this)) {
            llNetworkConnextion.setVisibility(View.GONE);
            llViewVisibile.setVisibility(View.GONE);
            rvBeneficiary.setVisibility(View.GONE);

            new BenificiaryAsync().execute();


        } else {
            llNetworkConnextion.setVisibility(View.VISIBLE);
            llViewVisibile.setVisibility(View.GONE);
            rvBeneficiary.setVisibility(View.GONE);
            llProgress.setVisibility(View.GONE);
        }


        rvBeneficiary.addOnItemTouchListener(
                new RecyclerItemClickListener(BeneficiaryListActivity.this, rvBeneficiary ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Intent i = new Intent(BeneficiaryListActivity.this,BeniDetailActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        if(mResponceBeniParser!=null){
                            i.putExtra("recipient_id",mResponceBeniParser.data.get(position).recipient_id);
                            i.putExtra("status",mResponceBeniParser.data.get(position).status);
                            i.putExtra("recipient_name",mResponceBeniParser.data.get(position).recipient_name);
                            i.putExtra("BankName",mResponceBeniParser.data.get(position).BankName);
                            i.putExtra("BeneficiaryCode",mResponceBeniParser.data.get(position).BeneficiaryCode);
                            i.putExtra("account",mResponceBeniParser.data.get(position).account);
                            i.putExtra("ifsc",mResponceBeniParser.data.get(position).ifsc);
                        }else{
                            i.putExtra("recipient_id","");
                            i.putExtra("status","");
                            i.putExtra("recipient_name","");
                            i.putExtra("BankName","");
                            i.putExtra("BeneficiaryCode","");
                            i.putExtra("account","");
                            i.putExtra("ifsc","");
                        }

                        startActivity(i);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


    }

    private void getViewID() {

        llViewVisibile = (LinearLayout) findViewById(R.id.ll_view_visibile);
        //   rvBeneficiary = (ListView) findViewById(R.id.recycler_view);
        llNetworkConnextion = (LinearLayout) findViewById(R.id.ll_network);
        llProgress = (LinearLayout) findViewById(R.id.ll_progress);
        ivNetwork = (ImageView) findViewById(R.id.iv_network);
        rvBeneficiary = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BeneficiaryListActivity.this, MoneyTransferActivity.class);
        intent.putExtra("screenValue", "novalue");
        startActivity(intent);
        finish();
    }

    private ArrayList<Beneficierymodel> mBeniList;

    private class BenificiaryAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llProgress.setVisibility(View.VISIBLE);
            llNetworkConnextion.setVisibility(View.GONE);
            llViewVisibile.setVisibility(View.GONE);
            rvBeneficiary.setVisibility(View.GONE);

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

            llProgress.setVisibility(View.GONE);
            mBeniList = new ArrayList<Beneficierymodel>();
            Beneficierymodel mBeneficiery;

            if (mResponceBeniParser != null) {
                Log.e("parser Size", "Size : " + mResponceBeniParser.status);
                if (mResponceBeniParser.status.equals("0")) {
                    for (int i = 0; i < mResponceBeniParser.data.size(); i++) {
                        //Log.e("History Section Service","Oprators : "+mResponceBeniParser.data.get(i).getProvider());
                        mBeneficiery = new Beneficierymodel();

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
                        } else {
                            mBeneficiery.setStatus("");
                        }

                        mBeniList.add(mBeneficiery);
                    }

                    llProgress.setVisibility(View.GONE);
                    llNetworkConnextion.setVisibility(View.GONE);
                    llViewVisibile.setVisibility(View.GONE);
                    rvBeneficiary.setVisibility(View.VISIBLE);

                    mAdapter = new CardViewDataAdapter(BeneficiaryListActivity.this, mBeniList);
                    // set the adapter object to the Recyclerview
                    rvBeneficiary.setHasFixedSize(true);
                    rvBeneficiary.setAdapter(mAdapter);
                    rvBeneficiary.setLayoutManager(new LinearLayoutManager(BeneficiaryListActivity.this));
                    //rvBeneficiary.setAdapter(mAdapter);


                } else {
                    llViewVisibile.setVisibility(View.VISIBLE);
                    llProgress.setVisibility(View.GONE);
                    llNetworkConnextion.setVisibility(View.GONE);
                    rvBeneficiary.setVisibility(View.GONE);
                }
            } else {
                llNetworkConnextion.setVisibility(View.VISIBLE);
                llViewVisibile.setVisibility(View.GONE);
                llProgress.setVisibility(View.GONE);
                rvBeneficiary.setVisibility(View.GONE);
                new BenificiaryAsync().execute();

            }


        }
    }

    CardViewDataAdapter mAdapter;

    private String callBeniList() throws IOException {

        gson = new Gson();
        String responce = "";

        RequestAll mAllRequest = new RequestAll();
        mAllRequest.api_token = Keys.API_KEY;
        mAllRequest.mobile_number = mobileNumber;

        Log.e("BeniList", "Request : " + gson.toJson(mAllRequest));

        CallPostService mPostService = new CallPostService(BeneficiaryListActivity.this);
        responce = mPostService.getJsonResponce(Keys.GET_Beneficiary_DETAIL, gson.toJson(mAllRequest));

        Log.e("benilist", "responce : " + responce);

        if (responce != null) {
            mResponceBeniParser = gson.fromJson(responce, ResponceBeniAll.class);
        } else {
            responce = null;
        }
        return responce;
    }


}

package com.asmobisoft.coffer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.WalletTransfer_History;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.responceparser.HistroryParser;
import com.asmobisoft.coffer.responceparser.Monet_Wallet_History;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gauarv on 24-03-2017.
 */

public class MoneyTransactionHistory extends Fragment {

    private LinearLayout llViewVisibile;
    private LinearLayout llProgress;
    private ListView listHistory;
    private WalletTransfer_History mAdapter;
    private LinearLayout llNetworkConnextion;
    private Gson gson;
    private HistroryParser mHistroryParser;
    private ImageView ivNetwork;
    RequestQueue MyRequestQueue;
    private ArrayList<Monet_Wallet_History> mReportList;

    public MoneyTransactionHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        MyRequestQueue = Volley.newRequestQueue(getActivity());
        getViewID(v);

        if (Utility.isOnline(getActivity())) {

            llNetworkConnextion.setVisibility(View.GONE);
            llViewVisibile.setVisibility(View.GONE);
            listHistory.setVisibility(View.GONE);
          //  new HistroyDataAsync().execute();
            getPostService();

        } else {
            llNetworkConnextion.setVisibility(View.VISIBLE);
            llViewVisibile.setVisibility(View.GONE);
            listHistory.setVisibility(View.GONE);
            llProgress.setVisibility(View.GONE);
        }


        return v;
    }

    private void getViewID(View v) {

        llViewVisibile = (LinearLayout) v.findViewById(R.id.ll_view_visibile);
        listHistory = (ListView) v.findViewById(R.id.recycler_view);
        llNetworkConnextion = (LinearLayout) v.findViewById(R.id.ll_network);
        llProgress = (LinearLayout) v.findViewById(R.id.ll_progress);
        ivNetwork = (ImageView) v.findViewById(R.id.iv_network);
        TextView transfer_type =  (TextView)v.findViewById(R.id.opertaor);
        transfer_type.setText("Transfer Type");
        TextView ref_id =  (TextView)v.findViewById(R.id.number);
        ref_id.setText("Ref-id");
        TextView amount =  (TextView)v.findViewById(R.id.amount);
        amount.setText("Description");
        TextView status =  (TextView)v.findViewById(R.id.status);
        status.setText("Date");
    }


   /* private class HistroyDataAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llProgress.setVisibility(View.VISIBLE);
            llNetworkConnextion.setVisibility(View.GONE);
            llViewVisibile.setVisibility(View.GONE);
            listHistory.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... params) {

         //   CallServiceHistory();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            llProgress.setVisibility(View.GONE);
            mReportList = new ArrayList<Monet_Wallet_History>();
            Reports mReport;

            if (mHistroryParser != null) {
                Log.e("parser Size", "Size : " + mHistroryParser.getReports().size());
                if (mHistroryParser.getReports().size() != 0) {

                    for (int i = 0; i < mHistroryParser.getReports().size(); i++) {
                        //Log.e("History Section Service","Oprators : "+mHistroryParser.getReports().get(i).getProvider());
                        mReport = new Reports();
                        if (mHistroryParser.getReports().get(i).getId() != null) {
                            mReport.setId(mHistroryParser.getReports().get(i).getId());
                        } else {
                            mReport.setId("");
                        }

                        if (mHistroryParser.getReports().get(i).getProvider() != null) {
                            mReport.setProvider(mHistroryParser.getReports().get(i).getProvider());
                        } else {
                            mReport.setProvider("");
                        }

                        if (mHistroryParser.getReports().get(i).getNumber() != null) {
                            mReport.setNumber(mHistroryParser.getReports().get(i).getNumber());
                        } else {
                            mReport.setNumber("");
                        }

                        if (mHistroryParser.getReports().get(i).getAmount() != null) {
                            mReport.setAmount(mHistroryParser.getReports().get(i).getAmount());
                        } else {
                            mReport.setAmount("");
                        }

                        if (mHistroryParser.getReports().get(i).getStatus() != null) {
                            mReport.setStatus(mHistroryParser.getReports().get(i).getStatus());
                        } else {
                            mReport.setStatus("");
                        }
                        if (mHistroryParser.getReports().get(i).getTotal_balance() != null) {
                            mReport.setTotal_balance(mHistroryParser.getReports().get(i).getTotal_balance());
                        } else {
                            mReport.setTotal_balance("");
                        }
                        if (mHistroryParser.getReports().get(i).getProfit() != null) {
                            mReport.setProfit(mHistroryParser.getReports().get(i).getProfit());
                        } else {
                            mReport.setProfit("");
                        }
                        mReportList.add(mReport);
                    }

                    llProgress.setVisibility(View.GONE);
                    llNetworkConnextion.setVisibility(View.GONE);
                    llViewVisibile.setVisibility(View.GONE);
                    listHistory.setVisibility(View.VISIBLE);

                    mAdapter = new HistoryAdapter(getActivity(),mReportList);
                    // set the adapter object to the Recyclerview
                    listHistory.setAdapter(mAdapter);

                } else {
                    llViewVisibile.setVisibility(View.VISIBLE);
                    llProgress.setVisibility(View.GONE);
                    llNetworkConnextion.setVisibility(View.GONE);
                    listHistory.setVisibility(View.GONE);

                }

            } else {
                llNetworkConnextion.setVisibility(View.VISIBLE);
                llViewVisibile.setVisibility(View.GONE);
                llProgress.setVisibility(View.GONE);
                listHistory.setVisibility(View.GONE);
                new HistroyDataAsync().execute();

            }

        }
    }*/

    public void getJsonResponse(String response) {

        llProgress.setVisibility(View.GONE);
        mReportList = new ArrayList<Monet_Wallet_History>();
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);
                if (jsonObj.getString("rtnMSG").equalsIgnoreCase("Get Money Transfer History successfully")) {
                    if (jsonObj.getString("state") != null) {
                        JSONArray state = jsonObj.getJSONArray("state");
                        for (int i = 0; i < state.length(); i++) {

                            JSONObject jsonObject = state.getJSONObject(i);
                            Monet_Wallet_History monet_wallet_history = new Monet_Wallet_History();
                            monet_wallet_history.setTransaction_type(jsonObject.getString("transaction_type"));
                            monet_wallet_history.setPayment_id(jsonObject.getString("payment_id"));
                            monet_wallet_history.setDescription(jsonObject.getString("description"));
                            monet_wallet_history.setCreated_date(jsonObject.getString("created_date"));
                            mReportList.add(monet_wallet_history);
                        }
                        llProgress.setVisibility(View.GONE);
                        llNetworkConnextion.setVisibility(View.GONE);
                        llViewVisibile.setVisibility(View.GONE);
                        listHistory.setVisibility(View.VISIBLE);
                        mAdapter = new WalletTransfer_History(getActivity(), mReportList);
                        // set the adapter object to the Recyclerview
                        listHistory.setAdapter(mAdapter);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




 /*   private String CallServiceHistory() {

        gson = new Gson();

        NetClientGet mNetClientGet = new NetClientGet(getActivity());
        String responce = "";

        String url = Keys.GET_LAST_50_TRANSACTION;
        Log.e("Login", "URL : " + url);

        responce = mNetClientGet.getDataClientData(url);
        Log.e("Login", "responce : " + responce);

        if (responce != null) {

            mHistroryParser = gson.fromJson(responce, HistroryParser.class);

        } else {
            responce = null;
        }
        return responce;
    }*/


    public void getPostService() {

        String url = Keys.WALLET_TRANSFER_HISTORY;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.e("REsponse=", response);
                try {
                    if (response != null) {
                        getJsonResponse(response);
                    } else {
                        response = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("Error =", "" + error);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", Utility.getPrefsData(getActivity(), "user_id", null)); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }



}


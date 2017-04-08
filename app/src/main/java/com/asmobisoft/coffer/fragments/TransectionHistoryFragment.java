package com.asmobisoft.coffer.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.HistoryAdapter;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.responceparser.HistroryParser;
import com.asmobisoft.coffer.responceparser.Reports;
import com.asmobisoft.coffer.webservices.NetClientGet;
import com.google.gson.Gson;

import java.util.ArrayList;

public class TransectionHistoryFragment extends Fragment {

    private LinearLayout llViewVisibile;
    private LinearLayout llProgress;
    private ListView listHistory;
    private HistoryAdapter mAdapter;
    private LinearLayout llNetworkConnextion;
    private Gson gson;
    private HistroryParser mHistroryParser;
    private ImageView ivNetwork;

    public TransectionHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        getViewID(v);

        if (Utility.isOnline(getActivity())) {
            llNetworkConnextion.setVisibility(View.GONE);
            llViewVisibile.setVisibility(View.GONE);
            listHistory.setVisibility(View.GONE);

            new HistroyDataAsync().execute();


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


    }

    private ArrayList<Reports> mReportList;

    private class HistroyDataAsync extends AsyncTask<String, String, String> {

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

            CallServiceHistory();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            llProgress.setVisibility(View.GONE);
            mReportList = new ArrayList<Reports>();
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
    }

    private String CallServiceHistory() {

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
    }


}

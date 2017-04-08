package com.asmobisoft.coffer.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WalletBalenceFragment extends Fragment {

    RequestQueue MyRequestQueue;
    private ProgressDialog mProgressDialog;

    public WalletBalenceFragment() {
        // Required empty public constructor
    }

    private TextView tv_total_balence;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wallet_balence, container, false);
        tv_total_balence = (TextView) v.findViewById(R.id.tv_total_balence);
        MyRequestQueue = Volley.newRequestQueue(getActivity());
        if (Utility.isOnline(getActivity())) {
            getPostService();
        } else {
            Utility.InternetSetting(getActivity());
        }

        return v;

    }

    public void getPostService(){
        String url = Keys.GET_BALENCE;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.e("REsponse=",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    tv_total_balence.setText("\u20B9 "+jsonObject.getString("wallet_balance"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.e("Error =", ""+error);
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", Utility.getPrefsData(getActivity(),"user_id",null)); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

/*    private class BalanceAsync extends AsyncTask<String, String, String> {

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


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
            }
        }
    }*/


}

package com.asmobisoft.coffer.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.AddBeniActivity;
import com.asmobisoft.coffer.activity.MoneyTransferActivity;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.CallPostService;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WallettoWalletFragment extends Fragment implements View.OnClickListener {

    private TextView tvTotalBalence;
    private TextView etMobileNo;
    private TextView etPasswrrdCoffer;
    private TextView btnSendNow;
    private EditText etAmount;
    private TextView tv_total_balence;
    private ProgressDialog mProgressDialog;
    String balance;
    RequestQueue MyRequestQueue;

    public WallettoWalletFragment(String balance) {
        // Required empty public constructor
        this.balance = balance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wallet_to_wallet, container, false);

        // Inflate the layout for this fragment
        tvTotalBalence = (TextView) v.findViewById(R.id.tv_total_balence);
        etMobileNo = (TextView) v.findViewById(R.id.et_mobile_no);
        etAmount = (EditText) v.findViewById(R.id.et_enter_amount);
        etPasswrrdCoffer = (TextView) v.findViewById(R.id.et_password_coffer);
        btnSendNow = (Button) v.findViewById(R.id.btn_send_now);
        tvTotalBalence.setText("\u20B9 "+balance);
        btnSendNow.setOnClickListener(this);

        MyRequestQueue = Volley.newRequestQueue(getActivity());

        if (Utility.isOnline(getActivity())) {
           // BalanceAsync mRechargeAsync = new BalanceAsync();
          //  mRechargeAsync.execute();
        } else {
            Utility.InternetSetting(getActivity());
        }

        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_now:

               if (isValidate()) {
                       Log.e("pwd=",""+Utility.getPrefsData(getActivity(),"password","null"));
                       getPostService();
                }
                break;
        }
    }

    private boolean isValidate() {

        if (etMobileNo.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Please enter mobile number !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etMobileNo.getText().toString().trim().length() < 10 || etMobileNo.getText().toString().trim().length() > 10) {
            Toast.makeText(getActivity(), "Please enter a valid mobile number !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etAmount.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Please enter amount !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPasswrrdCoffer.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Please enter Password !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!etPasswrrdCoffer.getText().toString().trim().equals(Utility.getPrefsData(getActivity(),"password",null))) {
            Toast.makeText(getActivity(), "Please enter correct password !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Integer.parseInt(etAmount.getText().toString().trim()) > Integer.parseInt(balance)){
            Toast.makeText(getActivity(), "You have not sufficent amount in your wallet !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void getPostService() {

        String url = Keys.WALLET_TO_WALLET_TRANSFER;
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.e("REsponse=", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("rtnCode").equals("1")){
                        alertDialog(2,jsonObject.getString("rtnMSG"));
                    }else {
                        alertDialog(1,"Dear " + Utility.getPrefsData(getActivity(), "full_name", "Account Name") + " \n" +
                                "Rs. " + etAmount.getText().toString().trim() + " is debited from your account and credited to " + etMobileNo.getText().toString().trim() + ".\n" +
                                "Thanks,\n" +
                                "Coffer Wallet Team");
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
                MyData.put("amount", etAmount.getText().toString().trim());
                MyData.put("mobile", etMobileNo.getText().toString().trim());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    //status 1 or 2 check for wallet transfer successfully status =1 and if fail then status = 2;
    public void alertDialog(final int status , String msg) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Coffer Wallet Transfer");
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.empty);
        alertDialog.setCancelable(false);
        // Setting OK Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(status==1) {
                    Intent intent = new Intent(getActivity(), MainActivity1.class);
                    intent.putExtra("screenValue", "wallet_money_transfer");
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    dialog.dismiss();
                }
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}

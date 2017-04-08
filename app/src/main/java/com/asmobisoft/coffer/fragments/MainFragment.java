package com.asmobisoft.coffer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.ChatRoomActivity;
import com.asmobisoft.coffer.activity.MobileRechargeActivity;
import com.asmobisoft.coffer.activity.MoneyTransferActivity;
import com.asmobisoft.coffer.activity.SendingReceiveOption;

public class MainFragment extends Fragment implements View.OnClickListener{


    public MainFragment() {
        // Required empty public constructor
    }

    private LinearLayout llRecharge;
    private LinearLayout llFund;
    private LinearLayout llChat;
    private LinearLayout llFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        getID(v);

        return v;
    }

    private void getID(View v) {


        llRecharge = (LinearLayout)v.findViewById(R.id.ll_recharge);
        llFund = (LinearLayout)v.findViewById(R.id.ll_fund);
        llChat = (LinearLayout)v.findViewById(R.id.ll_chat);
        llFile = (LinearLayout)v.findViewById(R.id.ll_file);

        llRecharge.setOnClickListener(this);
        llFund.setOnClickListener(this);
        llChat.setOnClickListener(this);
        llFile.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_recharge:

                Intent rechage = new Intent(getActivity(), MobileRechargeActivity.class);
                rechage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(rechage);

                break;
            case R.id.ll_fund:

                Intent fund = new Intent(getActivity(), MoneyTransferActivity.class);
                fund.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(fund);


                break;
            case R.id.ll_chat:
                Intent chatRoom = new Intent(getActivity(), ChatRoomActivity.class);
                chatRoom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(chatRoom);

              //  Toast.makeText(getActivity(), "Work in progress!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.ll_file:

                Intent file = new Intent(getActivity(), SendingReceiveOption.class);
//                file.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(file);

                //Toast.makeText(getActivity(), "Work in progress!", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}

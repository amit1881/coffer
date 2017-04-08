package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.responceparser.Monet_Wallet_History;

import java.util.ArrayList;

/**
 * Created by Gauarv on 24-03-2017.
 */

public class WalletTransfer_History extends BaseAdapter {
    private final Context context;
    private ArrayList<Monet_Wallet_History> mData;
    // private final Edit edit;
    private static String today;

    public WalletTransfer_History(Context context, ArrayList<Monet_Wallet_History> mData) {
        this.context = context;
        this.mData = mData;

    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HistoryAdapter.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_history, parent, false);

            holder = new HistoryAdapter.ViewHolder();
            //Log.e("NewHolder", "position = " + position);

            holder.tvOprator = (TextView) convertView.findViewById(R.id.tv_opt_number);
            holder.tvMobileNumber = (TextView) convertView.findViewById(R.id.tv_mobile_no);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.llroot = (LinearLayout) convertView.findViewById(R.id.ll_root);

            convertView.setTag(holder);
        } else {
            holder = (HistoryAdapter.ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            holder.llroot.setBackgroundResource(R.color.even);
        } else if (EvenOdd(position).equals("Even")) {
            holder.llroot.setBackgroundResource(R.color.even);
        } else {
            holder.llroot.setBackgroundResource(R.color.white);
        }

        if(mData.get(position).getTransaction_type().equals("1")){
            holder.tvOprator.setText("Wallet to Wallet");
        }else{
            holder.tvOprator.setText("Wallet to Bank");
        }
        holder.tvMobileNumber.setText(mData.get(position).getPayment_id());
        holder.tvAmount.setText(mData.get(position).getDescription());
        holder.tvStatus.setText(mData.get(position).getCreated_date());

        return convertView;
    }

    static class ViewHolder {
        TextView tvAmount;
        TextView tvOprator;
        TextView tvMobileNumber;
        TextView tvStatus;
        LinearLayout llroot;
    }

    private String EvenOdd(int pos) {
        String mNumberIS;

        if (pos % 2 == 0) {
            mNumberIS = "Even";
        } else {
            mNumberIS = "Odd";
        }

        return mNumberIS;
    }
}



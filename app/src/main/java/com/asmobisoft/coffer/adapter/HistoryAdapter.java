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
import com.asmobisoft.coffer.responceparser.Reports;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Reports> itemsArrayList;
    private ArrayList<Monet_Wallet_History> mData;
   // private final Edit edit;
   private static String today;

    public HistoryAdapter(Context context, ArrayList<Reports> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;

    }

   /* public HistoryAdapter(Context context, ArrayList<Monet_Wallet_History> itemsArrayList) {
        this.context = context;
        this.mData = mData;

    }*/

    @Override
    public int getCount() {
        return itemsArrayList.size();
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
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row_history, parent, false);

            holder = new ViewHolder();
            //Log.e("NewHolder", "position = " + position);

            holder.tvOprator = (TextView)convertView.findViewById(R.id.tv_opt_number);
            holder.tvMobileNumber = (TextView)convertView.findViewById(R.id.tv_mobile_no);
            holder.tvStatus = (TextView)convertView.findViewById(R.id.tv_status);
            holder.tvAmount = (TextView)convertView.findViewById(R.id.tv_amount);
            holder.llroot = (LinearLayout)convertView.findViewById(R.id.ll_root);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if( position == 0 ){
            holder.llroot.setBackgroundResource(R.color.even);
        }else if(EvenOdd(position).equals("Even")){
            holder.llroot.setBackgroundResource(R.color.even);
        }else{
            holder.llroot.setBackgroundResource(R.color.white);
        }

        holder.tvOprator.setText(itemsArrayList.get(position).getProvider());
        holder.tvMobileNumber.setText(itemsArrayList.get(position).getNumber());
        holder.tvAmount.setText(itemsArrayList.get(position).getAmount()+"\u20B9");
        holder.tvStatus.setText(itemsArrayList.get(position).getStatus());

        return convertView;
    }

    static class ViewHolder {
        TextView tvAmount;
        TextView tvOprator;
        TextView tvMobileNumber;
        TextView tvStatus;
        LinearLayout llroot;
    }

    private String EvenOdd(int pos){
        String mNumberIS;

        if(pos % 2 == 0){
           mNumberIS= "Even";}
        else{
            mNumberIS= "Odd";}

        return mNumberIS;
    }


}
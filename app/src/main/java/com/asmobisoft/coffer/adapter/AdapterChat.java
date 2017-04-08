package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.ChatRoom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterChat extends ArrayAdapter<ChatRoom> {
    private final Context context;
    private final ArrayList<ChatRoom> itemsArrayList;
   // private final Edit edit;
   private static String today;

    public AdapterChat(Context context, ArrayList<ChatRoom> itemsArrayList) {
        super(context, R.layout.list_chat_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;

        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_chat_row, parent, false);
            holder = new ViewHolder();
            //Log.e("NewHolder", "position = " + position);

            holder.tvSenderMessage = (TextView)convertView.findViewById(R.id.tv_message_sender);
            holder.tvReciverMessage = (TextView)convertView.findViewById(R.id.tv_message_reciver);
            holder.tvTimerSender = (TextView)convertView.findViewById(R.id.tv_timer_sender);
            holder.tvTimerReciver = (TextView)convertView.findViewById(R.id.tv_timmer_reciver);
            holder.llLeft = (LinearLayout)convertView.findViewById(R.id.ll_left);
            holder.llRight = (LinearLayout)convertView.findViewById(R.id.ll_right);

            convertView.setTag(holder);

        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }
       /* holder.tvOptNumber.setText(itemsArrayList.get(position).getName());
        holder.tvMobileNumber.setText(itemsArrayList.get(position).getLastMessage());
        //holder.tvAmount.setText(itemsArrayList.get(position).getUnreadCount());
        holder.tvAmount.setText("2");
        //holder.tvStatus.setText(getTimeStamp(itemsArrayList.get(position).getTimestamp()));
        holder.tvStatus.setText("12:20AM");*/

        return convertView;
    }

    static class ViewHolder {
        TextView tvSenderMessage;
        LinearLayout llLeft;
        LinearLayout  llRight;
        TextView tvReciverMessage;
        TextView tvTimerSender;
        TextView tvTimerReciver;
    }


    public static String getTimeStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = "";

        today = today.length() < 2 ? "0" + today : today;

        try {
            Date date = format.parse(dateStr);
            SimpleDateFormat todayFormat = new SimpleDateFormat("dd");
            String dateToday = todayFormat.format(date);
            format = dateToday.equals(today) ? new SimpleDateFormat("hh:mm a") : new SimpleDateFormat("dd LLL, hh:mm a");
            String date1 = format.format(date);
            timestamp = date1.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }
}
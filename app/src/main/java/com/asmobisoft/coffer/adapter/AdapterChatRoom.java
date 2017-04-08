package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.ChatRoom;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdapterChatRoom extends ArrayAdapter<ChatRoom> {
    private final Context context;
    private final ArrayList<ChatRoom> itemsArrayList;
    public ArrayList<ChatRoom> list;
   // private final Edit edit;
   private static String today;

    public AdapterChatRoom(Context context, ArrayList<ChatRoom> itemsArrayList) {
        super(context, R.layout.chat_rooms_list_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.list = new ArrayList<ChatRoom>(); // for search button
        this.list.addAll(itemsArrayList); // for search button
        Calendar calendar = Calendar.getInstance();
        today = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_rooms_list_row, parent, false);
            holder = new ViewHolder();
            //Log.e("NewHolder", "position = " + position);\

            holder.ProfileimageView = (ImageView) convertView.findViewById(R.id.profile_image);
            holder.tvName = (TextView)convertView.findViewById(R.id.name);
            holder.tvMessage = (TextView)convertView.findViewById(R.id.message);
            holder.tvTime = (TextView)convertView.findViewById(R.id.timestamp);
            holder.tvCount = (TextView)convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(itemsArrayList.get(position).getName());
        holder.tvMessage.setText("Available");
       // holder.tvMessage.setText(itemsArrayList.get(position).getLastMessage());
        //holder.tvAmount.setText(itemsArrayList.get(position).getUnreadCount());
        holder.tvCount.setText("2");
        //holder.tvStatus.setText(getTimeStamp(itemsArrayList.get(position).getTimestamp()));
        holder.tvTime.setText("12:20AM");

        try {
            Picasso.with(context)
                    .load(itemsArrayList.get(position).getProfile_image())
                    .error(R.mipmap.no_image)
                    .into(holder.ProfileimageView);
        }catch (Exception e){
            holder.ProfileimageView.setImageResource(R.mipmap.no_image);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvCount;
        TextView tvName;
        TextView tvMessage;
        TextView tvTime;
        ImageView ProfileimageView;

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

    public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
        itemsArrayList.clear();
        if (charText.length()==0)
        {
            itemsArrayList.addAll(list);
        }
        else {
            for (ChatRoom wp : list) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    itemsArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
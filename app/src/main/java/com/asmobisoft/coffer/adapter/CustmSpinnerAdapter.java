package com.asmobisoft.coffer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.Beneficierymodel;
import com.asmobisoft.coffer.model.BeniListForSpinner;
import com.asmobisoft.coffer.pojo.ImageItem;

import java.util.ArrayList;

/**
 * Created by root on 5/23/16.
 */
public class CustmSpinnerAdapter extends BaseAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<BeniListForSpinner> data ;

    public CustmSpinnerAdapter(Context context, ArrayList<BeniListForSpinner> data) {
        //super(context, data);
//        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_list, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.tv_spinner);
        //    holder.image = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

      //  ImageItem item = (ImageItem) data.get(position);
        holder.imageTitle.setText(data.get(position).getBeniName());

       // holder.image.setImageBitmap(item.getImage());

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
       // ImageView image;
    }
}
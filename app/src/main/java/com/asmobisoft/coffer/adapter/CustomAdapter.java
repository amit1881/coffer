package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.ProvidersData;

import java.util.ArrayList;

/*****
 * Adapter class extends with ArrayAdapter
 ******/
public class CustomAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList providerList;
    //public Resources res;
    ProvidersData providerValues = null;
    LayoutInflater inflater;

    /*************
     * CustomAdapter Constructor
     *****************/
    public CustomAdapter(Context mContext,
                         int textViewResourceId,
                         ArrayList providerList) {
        super(mContext, textViewResourceId, providerList);

        /********** Take passed values **********/
        mContext = mContext;
        this.providerList = providerList;
        // res      = resLocal;

        /***********  Layout inflater to call external xml layout () **********************/
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called providerList.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        /***** Get each Model object from Arraylist ********/
        providerValues = null;
        providerValues = (ProvidersData) providerList.get(position);

        TextView label = (TextView) row.findViewById(R.id.tv_spinner_text);

        // Set values for spinner each row
        label.setText(providerValues.getProvider_name());

        return row;
    }
}
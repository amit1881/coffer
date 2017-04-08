package com.asmobisoft.coffer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmobisoft.coffer.R;

/**
 * Created by Abhishek on 03-Apr-17.
 */

public class ReceiveProgressAdapter extends RecyclerView.Adapter<ReceiveProgressAdapter.ReceiveProgressAdapterHolder>{

    public ReceiveProgressAdapter() {
    }

    @Override
    public ReceiveProgressAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sending_progress_row,parent,false);
        return new ReceiveProgressAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiveProgressAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ReceiveProgressAdapterHolder extends RecyclerView.ViewHolder {
        public ReceiveProgressAdapterHolder(View itemView) {
            super(itemView);
        }
    }
}

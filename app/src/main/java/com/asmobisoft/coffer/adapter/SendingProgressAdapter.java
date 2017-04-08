package com.asmobisoft.coffer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asmobisoft.coffer.R;

/**
 * Created by Abhishek on 03-Apr-17.
 */

public class SendingProgressAdapter extends RecyclerView.Adapter<SendingProgressAdapter.SendingProgressAdapterHolder> {

    public SendingProgressAdapter() {
    }

    @Override
    public SendingProgressAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sending_progress_row,parent,false);
        return new SendingProgressAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(SendingProgressAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class SendingProgressAdapterHolder extends RecyclerView.ViewHolder{
        public SendingProgressAdapterHolder(View itemView) {
            super(itemView);
        }
    }
}

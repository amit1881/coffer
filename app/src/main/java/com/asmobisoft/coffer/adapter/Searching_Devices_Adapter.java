package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.SendingProgress;

/**
 * Created by Abhishek on 03-Apr-17.
 */

public class Searching_Devices_Adapter extends RecyclerView.Adapter<Searching_Devices_Adapter.Searching_Devices_Adapter_Holder> {

    private Context context;
    public Searching_Devices_Adapter(Context context)
    {
        this.context = context;
    }

    @Override
    public Searching_Devices_Adapter_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_devices_row, parent, false);
        return new Searching_Devices_Adapter_Holder(view);
    }

    @Override
    public void onBindViewHolder(final Searching_Devices_Adapter_Holder holder, final int position)
    {
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context , SendingProgress.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class Searching_Devices_Adapter_Holder extends RecyclerView.ViewHolder
    {
        private RelativeLayout mRelativeLayout;

        public Searching_Devices_Adapter_Holder(View view)
        {
            super(view);
           mRelativeLayout = (RelativeLayout)view.findViewById(R.id.rl_selected_device);
        }
    }
}

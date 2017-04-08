package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.Beneficierymodel;

import java.util.List;

public class CardViewDataAdapter extends
		RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

	private List<Beneficierymodel> stList;
	private Context mCtx;

	public CardViewDataAdapter(Context mCtx, List<Beneficierymodel> students) {
		this.stList = students;
		this.mCtx = mCtx;

	}

	// Create new views
	@Override
	public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		// create a new view
		View itemLayoutView = LayoutInflater.from(mCtx).inflate(
				R.layout.card_view, null);

		// create ViewHolder

		ViewHolder viewHolder = new ViewHolder(itemLayoutView);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {

		final int pos = position;

		viewHolder.tvName.setText(stList.get(position).getRecipient_name());
		viewHolder.tvAccountnumber.setText(stList.get(position).getAccount());
		viewHolder.tvStatus.setText(stList.get(position).getStatus());

	}

	// Return the size arraylist
	@Override
	public int getItemCount() {
		return stList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView tvName;
		public TextView tvAccountnumber;
		public TextView tvStatus;

		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);

			tvName = (TextView) itemLayoutView.findViewById(R.id.tv_name);
			tvAccountnumber = (TextView) itemLayoutView.findViewById(R.id.tv_account_number);
			tvStatus= (TextView) itemLayoutView.findViewById(R.id.tv_status);

		}

	}

	// method to access in activity after updating selection
	public List<Beneficierymodel> getStudentist() {
		return stList;
	}

}

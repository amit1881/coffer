package com.asmobisoft.coffer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.Searching_Devices_Adapter;

/**
 * Created by Abhishek on 03-Apr-17.
 */

public class Searching_Devices extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvtiitle;
    private RecyclerView mRecyclerView;
    private Searching_Devices_Adapter mSearching_devices_adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_devices);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvtiitle = (TextView)findViewById(R.id.tv_tittle);
        tvtiitle.setText("Searching Devices");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        getId();
    }

    private void getId() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_searching_devices);
        mSearching_devices_adapter = new Searching_Devices_Adapter(this);
        mRecyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mSearching_devices_adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), FileShareOption.class);
        intent.putExtra("screenValue","novalue");
        startActivity(intent);
        finish();
    }
}

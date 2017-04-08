package com.asmobisoft.coffer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.SendingProgressAdapter;

/**
 * Created by Abhishek on 03-Apr-17.
 */

public class SendingProgress extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvtitle;
    private RecyclerView mRecyclerView;
    private SendingProgressAdapter mSendingProgressAdapter;
    private RecyclerView.LayoutManager manager;
    private ProgressBar mProgressDialog;
    private Handler mHandler = new Handler();
    private int mProgressStatus = 0;
    private TextView text , deviceName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_progress);
        
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvtitle = (TextView)findViewById(R.id.tv_tittle);
        tvtitle.setText("Sending File Progress");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        
        getId();
        
    }

    private void getId() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_file_progress);
        mSendingProgressAdapter = new SendingProgressAdapter();
        mRecyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mSendingProgressAdapter);

        mProgressDialog = (ProgressBar)findViewById(R.id.sending_progressbar);
        text = (TextView)findViewById(R.id.progressbar_text);
        deviceName = (TextView)findViewById(R.id.tv_device_name_connected);

        progressBar();
    }

    private void progressBar()
    {
        new Thread(new Runnable() {
            public void run() {
                final int max = 100;
                while (mProgressStatus < max) {
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgressDialog.setProgress(mProgressStatus);
                            text.setText(""+mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Searching_Devices.class);
        intent.putExtra("screenValue","novalue");
        startActivity(intent);
        super.onBackPressed();
    }
}

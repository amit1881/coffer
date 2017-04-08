package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.ReceiveProgressAdapter;

/**
 * Created by Abhishek on 01-Apr-17.
 */

public class ReceiveProgress extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvtiitle;
    private RecyclerView mRecyclerView;
    private ReceiveProgressAdapter mReceiveProgressAdapter;
    private RecyclerView.LayoutManager manager;
    private ProgressBar mProgressBar;
    private Handler handler = new Handler();
    private int mProgressStatus = 0;
    private TextView text , deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_progress);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvtiitle= (TextView)toolbar.findViewById(R.id.tv_tittle);
        tvtiitle.setText("Receive File Progress");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        getid();

    }

    private void getid() {
        mRecyclerView =(RecyclerView)findViewById(R.id.rv_file_progress);
        mReceiveProgressAdapter = new ReceiveProgressAdapter();
        mRecyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mReceiveProgressAdapter);
        mProgressBar = (ProgressBar)findViewById(R.id.sending_progressbar);
        text = (TextView)findViewById(R.id.progressbar_text);
        deviceName = (TextView)findViewById(R.id.tv_device_name_connected);

        progressBar();
    }

    private void progressBar()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int max = 100;
                while (mProgressStatus < max)
                {
                    mProgressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                            text.setText(""+mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }}).start();
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
        Intent intent = new Intent(getApplicationContext(), SendingReceiveOption.class);
        intent.putExtra("screenValue","novalue");
        startActivity(intent);
        finish();
    }
}

package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;

/**
 * Created by Abhishek on 01-Apr-17.
 */

public class SendingReceiveOption extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView tvtiitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sending_receive_option);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvtiitle= (TextView)toolbar.findViewById(R.id.tv_tittle);
        tvtiitle.setText("File Share");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");


        getid();


    }

    private void getid() {
        findViewById(R.id.btn_send_file).setOnClickListener(this);
        findViewById(R.id.btn_receive_file).setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.btn_send_file:
                Intent send = new Intent(SendingReceiveOption.this,FileShareOption.class);
                startActivity(send);
                break;

            case R.id.btn_receive_file:
                Intent receive = new Intent(SendingReceiveOption.this, ReceiveProgress.class);
                startActivity(receive);
                break;
        }
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
        Intent intent = new Intent(getApplicationContext(), MainActivity1.class);
        intent.putExtra("screenValue","novalue");
        startActivity(intent);
        finish();
    }

}

package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;

/**
 * Created by root on 5/23/16.
 */
public class RechargeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRechargeNow;

    // TODO - insert your API KEY obtained from pay2all.com here
    private final static String API_KEY = "PPqNCFK6DCncHEvLzza4qBmQLS8IbNT60kl0loMVfp6x5h6cXRi3HztFt4Z2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mobile_recharge);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.recharge));

        getID();
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
    private void getID() {
        btnRechargeNow = (Button) findViewById(R.id.btn_recharge_now);
        btnRechargeNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recharge_now:

                break;
        }


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

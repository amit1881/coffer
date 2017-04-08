package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;

public class WalletActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_wallet_balence);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

      //  String title = getIntent().getExtras().getString(Keys.RECHARGE_CONSTANT);
        actionBar.setTitle("Wallet Balance");

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Recharge Section");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);*/
       // String title = getIntent().getExtras().getString(Keys.RECHARGE_CONSTANT);


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
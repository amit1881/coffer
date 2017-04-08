package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.asmobisoft.coffer.R;

/**
 * Created by Abhishek on 31-Mar-17.
 */

public class Send_Activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView sendview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        getView();

    }

    private void getView() {
        findViewById(R.id.send_img).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.send_img:
                Intent intent = new Intent(Send_Activity.this, FileSharingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

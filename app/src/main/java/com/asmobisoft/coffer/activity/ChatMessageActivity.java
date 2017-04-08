package com.asmobisoft.coffer.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.adapter.AdapterChat;
import com.asmobisoft.coffer.model.ChatRoom;
import com.asmobisoft.coffer.model.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/17/16.
 */

public class ChatMessageActivity extends AppCompatActivity {

    private ListView lvMessage;
    private EditText etSend;
    private Button btnSend;
    private Toolbar toolbar;
    private ArrayList<ChatRoom> mChatList;
    AdapterChat mAdapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getIntent().getExtras().getString("name"));

        getid();

        btnSend.setEnabled(false);
        btnSend.setTextColor(getResources().getColor(R.color.gray));

        etSend.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    btnSend.setEnabled(true);
                    btnSend.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    btnSend.setEnabled(false);
                    btnSend.setTextColor(getResources().getColor(R.color.gray));
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ChatMessageActivity.this, "Clicked", Toast.LENGTH_SHORT).show();


            }

        });

        mChatList = new ArrayList<ChatRoom>();

        ChatRoom chatRoom;
        for (int i = 0; i < 5; i++) {
            chatRoom = new ChatRoom();
            chatRoom.setName("rohit is a goood boy");
            chatRoom.setLastMessage("sjdjksdjk hsgdjksdjk sdhsjkh");
            mChatList.add(chatRoom);
        }

        AdapterChat mAdapterChat = new AdapterChat(ChatMessageActivity.this, mChatList);
        lvMessage.setAdapter(mAdapterChat);
        scrollMyListViewToBottom();
    }

    private void scrollMyListViewToBottom() {
        lvMessage.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lvMessage.setSelection(lvMessage.getAdapter().getCount() - 1);
            }
        });
    }

    private boolean isvalidate() {
        
        /*if(etSend.getText().toString().trim().length() == 0){

            btnSend.clicka
            Toast.makeText(ChatMessageActivity.this, "", Toast.LENGTH_SHORT).show();
            return false;
            
        }
        */

        return true;
    }

    private void getid() {

        lvMessage = (ListView) findViewById(R.id.lv_message);
        btnSend = (Button) findViewById(R.id.btn_send);
        etSend = (EditText) findViewById(R.id.et_message);

    }

    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i = 1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.setName("sggds");
            ci.getSurname();
            ci.setEmail("yo@gmailtest.com");
/*
            ci.email = ContactInfo.EMAIL_PREFIX + i + "@test.com";
            ci.surname = ContactInfo.SURNAME_PREFIX + i;
*/
            result.add(ci);

        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chatmessagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_app_bar_search:
                Toast.makeText(this, "Search ", Toast.LENGTH_SHORT)
                        .show();
                break;

            case R.id.action_view_contact:
                Toast.makeText(this, "View Contact", Toast.LENGTH_SHORT)
                        .show();
                break;

            case R.id.action_gallery_item:
                Toast.makeText(this, "Gallery Item", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
        intent.putExtra("screenValue", "novalue");
        startActivity(intent);
        finish();
    }
}

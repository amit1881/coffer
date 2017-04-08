package com.asmobisoft.coffer.chatroomfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.activity.ChatMessageActivity;
import com.asmobisoft.coffer.adapter.AdapterChatRoom;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.model.ChatRoom;
import com.asmobisoft.coffer.webservices.NetClientGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {


    public GroupFragment() {
        // Required empty public constructor
    }
    private RecyclerView recyclerView;
    private AdapterChatRoom mAdapter;
    private ArrayList<ChatRoom> chatRoomArrayList;
    private EditText inputMessage;
    private Button btnSend;

    private ListView lvChatRoom;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_group, container, false);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        lvChatRoom = (ListView) view.findViewById(R.id.lv_chatRoom);

        if (Utility.isOnline(getActivity())){
            GroupFragment.ChatroomAsync ChatroomAsync = new ChatroomAsync();
            ChatroomAsync.execute();
        } else {
            Utility.InternetSetting(getActivity());
        }

        lvChatRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),ChatMessageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("name",chatRoomArrayList.get(position).getName());
                startActivity(i);
            }
        });
        return view;
    }
    private class ChatroomAsync extends AsyncTask<String, String, String> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(getActivity());

            String responce = "";

            String url = Keys.USER_LIST_CHAT;
            Log.e("Signup", "URL : " + url);

            responce = mNetClientGet.getDataClientData(url);
            Log.e("Signup", "responce : " + responce);

            return responce;
        }

        protected void onPostExecute(String result) {
            if(mProgressDialog !=null){
                mProgressDialog.dismiss();
            }
            Log.e("Signup", "responce otp : " + result);
            if (result != null) {
                if (!result.equals("")) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        Log.e("Signup", "Hit OTP : " + jsonObj.getString("rtnMSG"));

                        if (jsonObj.getString("rtnMSG").equals("Get Suggested Friends successfully")) {

                            if(jsonObj.getString("state")!=null) {
                                JSONArray state = jsonObj.getJSONArray("state");
                                chatRoomArrayList = new ArrayList<ChatRoom>();
                                ChatRoom chatRoom;
                                for (int i = 0; i < state.length(); i++) {
                                    chatRoom = new ChatRoom();
                                    JSONObject c = state.getJSONObject(i);
                                    Log.e("", "Login Activity : FName " + c.getString("first_name")
                                            + "\n" + c.getString("last_name") + "\n"
                                    );

                                    if(c.getString("first_name") !=null || c.getString("last_name") !=null){
                                        chatRoom.setName(c.getString("first_name")+" "+c.getString("last_name"));
                                    }else{
                                        chatRoom.setName(" ");
                                    }
                                    if (c.getString("profile_image") != null && !c.getString("profile_image").equals("")) {
                                        chatRoom.setProfile_image(c.getString("profile_image"));
                                    } else {
                                        chatRoom.setProfile_image(" ");
                                    }
                                    chatRoomArrayList.add(chatRoom);
                                }
                            }
                            mAdapter = new AdapterChatRoom(getActivity(), chatRoomArrayList);
                            lvChatRoom.setAdapter(mAdapter);

                        } else {
                            Toast.makeText(getActivity(), jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}

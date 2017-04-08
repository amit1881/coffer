package com.asmobisoft.coffer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.asmobisoft.coffer.chatroomfragment.ChatRoomFragment;
import com.asmobisoft.coffer.chatroomfragment.ContactFragment;
import com.asmobisoft.coffer.chatroomfragment.GroupFragment;
import com.asmobisoft.coffer.chatroomfragment.StatusFragment;
import com.asmobisoft.coffer.model.ChatRoom;

import java.util.Locale;

/**
 * Created by Abhishek on 04-Apr-17.
 */

public class ChatRoomPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ChatRoomPagerAdapter(FragmentManager fm, int NumOfTabs)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
                return chatRoomFragment;

            case 1:
            GroupFragment groupFragment = new GroupFragment();
            return groupFragment;

            case 2:
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;

    }




}

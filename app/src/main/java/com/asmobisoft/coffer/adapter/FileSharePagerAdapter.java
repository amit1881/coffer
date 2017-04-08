package com.asmobisoft.coffer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.asmobisoft.coffer.share_fragment.AppsFragment;
import com.asmobisoft.coffer.share_fragment.File_fragment;
import com.asmobisoft.coffer.share_fragment.MusicFragment;
import com.asmobisoft.coffer.share_fragment.Photo_Fragment;
import com.asmobisoft.coffer.share_fragment.Video_Fragment;

/**
 * Created by Abhishek on 01-Apr-17.
 */

public class FileSharePagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public FileSharePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                File_fragment file_fragment = new File_fragment();
                return file_fragment;
            case 1:
                Video_Fragment videoFragment = new Video_Fragment();
                return videoFragment;
            case 2:
                AppsFragment appsFragment = new AppsFragment();
                return appsFragment;
            case 3:
                Photo_Fragment photoFragment = new Photo_Fragment();
                return photoFragment;
            case 4:
                MusicFragment musicFragment = new MusicFragment();
                return musicFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

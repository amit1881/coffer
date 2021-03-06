package com.asmobisoft.coffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.database.SqliteDb;
import com.asmobisoft.coffer.fragments.DatacardFragment;
import com.asmobisoft.coffer.fragments.DtheFragment;
import com.asmobisoft.coffer.fragments.ElectricityFragment;
import com.asmobisoft.coffer.fragments.GasFragment;
import com.asmobisoft.coffer.fragments.MobileFragment;

import java.util.ArrayList;
import java.util.List;

public class MobileRechargeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*SqliteDb mSqliteDb = new SqliteDb(MobileRechargeActivity.this);
        mSqliteDb.deletData();*/

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab;
        tab = tabLayout.getTabAt(0);
        tab.select();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MobileFragment(), getResources().getString(R.string.tb1));
        adapter.addFragment(new DatacardFragment(), getResources().getString(R.string.tb2));
        adapter.addFragment(new DtheFragment(), getResources().getString(R.string.tb3));
        adapter.addFragment(new ElectricityFragment(), getResources().getString(R.string.tb4));
        adapter.addFragment(new GasFragment(), getResources().getString(R.string.tb5));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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

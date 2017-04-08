package com.asmobisoft.coffer;

import android.*;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.asmobisoft.coffer.activity.MoneyTransferActivity;
import com.asmobisoft.coffer.activity.ProfileActivity;
import com.asmobisoft.coffer.adapter.DrawerMenuItem;
import com.asmobisoft.coffer.adapter.DrawerMenuItemAdapter;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.fragments.AboutFragment;
import com.asmobisoft.coffer.fragments.BonusPointFragment;
import com.asmobisoft.coffer.fragments.ChangePasswordFragment;
import com.asmobisoft.coffer.fragments.FAQFragment;
import com.asmobisoft.coffer.fragments.FeedbackFragment;
import com.asmobisoft.coffer.fragments.MainFragment;
import com.asmobisoft.coffer.fragments.MoneyTransactionHistory;
import com.asmobisoft.coffer.fragments.TermsandConditionFragment;
import com.asmobisoft.coffer.fragments.TransectionHistoryFragment;
import com.asmobisoft.coffer.fragments.WalletBalenceFragment;
import com.asmobisoft.coffer.registration.LoginActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mLvDrawerMenu;
    private DrawerMenuItemAdapter mDrawerMenuAdapter;
    private ActionBar actionBar;
    TextView tvName;
    TextView tvNotifiacton;
    ImageView imageView,iv_background;
    private static final int REQUEST_PERMISSIONS = 100;

    private String fName, lName, mEmail, url;
    private String address, mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ContextCompat.checkSelfPermission(MainActivity1.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(MainActivity1.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity1.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity1.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");

        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLvDrawerMenu = (ListView) findViewById(R.id.lv_drawer_menu);

        List<DrawerMenuItem> menuItems = generateDrawerMenuItems();
        mDrawerMenuAdapter = new DrawerMenuItemAdapter(getApplicationContext(), menuItems);
        mLvDrawerMenu.setAdapter(mDrawerMenuAdapter);
        //Adding header of list view
        View header = getLayoutInflater().inflate(R.layout.nav_header, null);

        tvName = (TextView) header.findViewById(R.id.profile_name);
        tvNotifiacton = (TextView) header.findViewById(R.id.profile_notification);
        iv_background = (ImageView) header.findViewById(R.id.iv_background);
        imageView = (ImageView) header.findViewById(R.id.iv_profile_pic);
        mLvDrawerMenu.addHeaderView(header);
        mLvDrawerMenu.setOnItemClickListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {

            if(getIntent().getExtras().getString("screenValue").equals("recharge")){
                setFragment(9, TransectionHistoryFragment.class);
                mToolbar.setTitle("Transaction History");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            }else if(getIntent().getExtras().getString("screenValue").equals("login")){
                setFragment(1, MainFragment.class);
                mToolbar.setTitle("Coffer Dashboard");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            }else if(getIntent().getExtras().getString("screenValue").equals("wallet_money_transfer")){
                setFragment(10, MoneyTransactionHistory.class);
                mToolbar.setTitle("Money Transaction History");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            }else {
                setFragment(1, MainFragment.class);
                mToolbar.setTitle("Coffer Dashboard");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            }

        }

        // Log.e("","value prefrenc "+Utility.getPrefsData(MainActivity1.this,"data",""));


        fName = Utility.getPrefsData(MainActivity1.this, "full_name", "");
        mEmail = Utility.getPrefsData(MainActivity1.this, "mEmail", "");
        url = Utility.getPrefsData(MainActivity1.this, "url", "");
        address = Utility.getPrefsData(MainActivity1.this, "address", "");
        mobile = Utility.getPrefsData(MainActivity1.this, "mobile", "");

        tvName.setText(fName);
        tvNotifiacton.setText(mEmail);
        Picasso.with(MainActivity1.this).load(url).resize(40,40).centerCrop().into(imageView);

        Picasso.with(MainActivity1.this).load(R.drawable.bg5).resize(40,40).centerCrop().into(iv_background);

        try {
            if (!url.contains(null)) {
                Picasso.with(MainActivity1.this)
                        .load(url)
                        .placeholder(R.mipmap.user)
                        .error(R.mipmap.user)
                        .into(imageView);
            }
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                Intent i = new Intent(MainActivity1.this, ProfileActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("full_name", fName);
                mBundle.putString("mEmail", mEmail);
                mBundle.putString("url", url);
                mBundle.putString("address", address);
                mBundle.putString("mobile", mobile);
                i.putExtras(mBundle);
                startActivity(i);
                finish();
                break;
            case 1:
                setFragment(1, MainFragment.class);
                mToolbar.setTitle("Coffer Dashboard");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                setFragment(2, BonusPointFragment.class);
                mToolbar.setTitle("Bonus Points");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 3:
                setFragment(3, WalletBalenceFragment.class);
                mToolbar.setTitle("Wallet Balance");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 4:
                setFragment(4, ChangePasswordFragment.class);
                mToolbar.setTitle("Change Password");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 5:
                Intent i1 = new Intent(MainActivity1.this, MoneyTransferActivity.class);
                startActivity(i1);
                break;
            case 6:
                setFragment(6, TermsandConditionFragment.class);
                mToolbar.setTitle("Terms and Conditions");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 7:
                setFragment(7, AboutFragment.class);
                mToolbar.setTitle("About US");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 8:
                setFragment(8, FAQFragment.class);
                mToolbar.setTitle("FAQ");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;
            case 9:
                setFragment(9, TransectionHistoryFragment.class);
                mToolbar.setTitle("Transaction History");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;

            case 10:
                setFragment(10, MoneyTransactionHistory.class);
                mToolbar.setTitle("Money Transaction History");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
                break;

            case 11:
                //REfer to a Friend
                String shareBody = "Here is the share content body";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Refer Using !"));

                break;
            case 12:
                //feedback
                setFragment(9, FeedbackFragment.class);
                mToolbar.setTitle("Feedback");
                mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

                break;
            case 13:
                //Logout Functionality

                Typeface externalFont = Typeface.createFromAsset(
                        MainActivity1.this.getAssets(), "fonts/Frank.ttf");
                final Dialog dialog1 = new Dialog(MainActivity1.this);

                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.dialog);
                dialog1.setCancelable(true);

                TextView text1 = (TextView) dialog1.findViewById(R.id.tv_tittle1);
                text1.setText("Coffer IMPORTANT !");
                text1.setTypeface(externalFont, Typeface.BOLD);
                text1.setTextSize(18);

                TextView text = (TextView) dialog1.findViewById(R.id.tv_subtittle);
                text.setText("Are you really want to logout the Coffer App!");
                text.setTextSize(14);
                text.setTypeface(externalFont);

                Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);
                Button btnOK = (Button) dialog1.findViewById(R.id.btn_ok);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dialog1.dismiss();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                });

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Utility.setPrefsData(MainActivity1.this, "mobile", "");
                            Intent i = new Intent(MainActivity1.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                            dialog1.dismiss();
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                    }
                });

                dialog1.show();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mLvDrawerMenu)) {
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setFragment(int position, Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment, fragmentClass.getSimpleName());
            fragmentTransaction.commit();

            mLvDrawerMenu.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mLvDrawerMenu);
            mLvDrawerMenu.invalidateViews();
        } catch (Exception ex) {
            Log.e("setFragment", ex.getMessage());
        }
    }

    private List<DrawerMenuItem> generateDrawerMenuItems() {
        String[] itemsText = getResources().getStringArray(R.array.nav_drawer_items);
        //  TypedArray itemsIcon = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        Log.e("MainAc", "itemsText.length " + itemsText.length);
        List<DrawerMenuItem> result = new ArrayList<DrawerMenuItem>();
        for (int i = 0; i < itemsText.length; i++) {
            DrawerMenuItem item = new DrawerMenuItem();
            item.setText(itemsText[i]);
            //   item.setIcon(itemsIcon.getResourceId(i, -1));
            result.add(item);
        }
        return result;
    }

}

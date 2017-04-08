package com.asmobisoft.coffer.commonmethod;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asmobisoft.coffer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 2/1/16.
 */
public class Utility {

    public static void CallSnack(String MessageS, LinearLayout coordinatorLayout) {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, MessageS, Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                     /*   Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "View is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();*/
            }
        });

        snackbar.show();

    }

    public static void CallCustomSnackbar(LinearLayout coordinatorLayout) {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static List<String> EmailFinder(Context ctx) {

        AccountManager am = AccountManager.get(ctx);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        Account[] accounts = am.getAccounts();
        ArrayList<String> account_array = new ArrayList<String>();
        ArrayList<String> account_name = new ArrayList<String>();


        for (Account ac : accounts) {
            String acname = ac.name;
            String actype = ac.type;
            // Take your time to look at all available accounts

            if (acname.contains("@gmail.com")) {
                account_array.add(acname);
                account_name.add(acname);
            }

        }
        return account_array;
    }

    public static boolean isOnline(Context ctx) {

        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void ExitAppDialog(final Activity activity)
    {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
        alertbox.setTitle("Warning !");
        alertbox.setMessage("Would you like to exit application ?");
        alertbox.setPositiveButton("Yes", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        activity.finish();
                    }
                });
        alertbox.setNegativeButton("No", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertbox.show();
    }


    public static void InternetSetting(final Context ctx){

        final Dialog dialog1 = new Dialog(ctx);
        Typeface externalFont = Typeface.createFromAsset(ctx.getAssets(), "fonts/Frank.ttf");

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.alert_dialog_internet);
        dialog1.setCancelable(true);

        TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
        text1.setTypeface(externalFont, Typeface.BOLD);

      /*  TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
        text1.setText("Please Enter OTP !");
        text1.setTypeface(externalFont, Typeface.BOLD);*/


        Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);
        Button buttonOk = (Button) dialog1.findViewById(R.id.btn_setting);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ctx.startActivity(new Intent(Settings.ACTION_SETTINGS));

            }
        });
        dialog1.show();

    }

    /**
     * Display Dialog
     **//*
    public static void showDialog(final Context context, String message) {
        Dialog dialog = new Dialog(context,R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notify_received_activity);
        // set the custom dialog components - text and button
        TextView text = (TextView) dialog.findViewById(R.id.txtmsg);
        text.setText(message);
        Button dialogButton = (Button) dialog.findViewById(R.id.btncancel);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

*/

    public static void showAlertwithTwoTitle(Context ctx,String title,String subtitle){


        Typeface externalFont = Typeface.createFromAsset(
                ctx.getAssets(), "fonts/frabk.ttf");
        final Dialog dialog1 = new Dialog(ctx);

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog);
        dialog1.setCancelable(true);

        TextView text1 = (TextView) dialog1.findViewById(R.id.tv_tittle1);
        text1.setText(title);
        text1.setTypeface(externalFont, Typeface.BOLD);
        text1.setTextSize(18);

        TextView text = (TextView) dialog1.findViewById(R.id.tv_subtittle);
        text.setText(subtitle);
        text.setTextSize(14);
        text.setTypeface(externalFont);

        Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);

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

        dialog1.show();

    }

    public static void showAlertwithOneButton(Context ctx,String title,String subtitle){


   /*     Typeface externalFont = Typeface.createFromAsset(
                ctx.getAssets(), "fonts/frabk.ttf");*/
        final Dialog dialog1 = new Dialog(ctx);

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.custom_dialog_with_onebutton);
        dialog1.setCancelable(true);

        TextView text1 = (TextView) dialog1.findViewById(R.id.tv_tittle1);
        text1.setText(title);
       // text1.setTypeface(externalFont, Typeface.BOLD);
        text1.setTextSize(18);

        TextView text = (TextView) dialog1.findViewById(R.id.tv_subtittle);
        text.setText(subtitle);
        text.setTextSize(14);
        ///text.setTypeface(externalFont);

        Button btnCancel = (Button) dialog1.findViewById(R.id.btn_ok);

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

        dialog1.show();

    }

    private static SharedPreferences.Editor editor;

    public static String getDeviceIMEI(Context mcontext) {

        String android_id = Settings.Secure.getString(mcontext.getContentResolver(), Settings.Secure.ANDROID_ID);

        return android_id;
    }

    public static void setPrefsData(Context context, String prefsKey,
                                    String prefValue) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(prefsKey, prefValue);
        editor.commit();
    }
    public static String getPrefsData(Context context, String prefsKey,
                                      String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String prefsValue = sharedPreferences.getString(prefsKey, defaultValue);
        return prefsValue;
    }


    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}

package com.asmobisoft.coffer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.commonmethod.Keys;
import com.asmobisoft.coffer.commonmethod.Utility;
import com.asmobisoft.coffer.webservices.NetClientGet;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivEdit;
    private TextView etName;
    private TextView etJob;
    private TextView etAddress;
    private TextView etMobileNumber;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    private String userChoosenTask;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap thumbnail=null;
    private ImageView ivImage;
    File destination;
    Uri fileUri;
    boolean isImageFromcamera =false;
    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    private ImageView upload_image;
    private ImageView profile_pic;

    private Toolbar toolbar;
    private TextView tvtitle;
    private TextView tvFullName, tvEmail, tvAddress, tvMobileno;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Todo toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvtitle= (TextView)toolbar.findViewById(R.id.tv_tittle);
        tvtitle.setText("Profile");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        //finish

   /*     i.putExtra("full_name",fName +" "+lName);
        i.putExtra("mEmail",mEmail);
        i.putExtra("url",url);
        i.putExtra("address",address);
        i.putExtra("mobile",mobile);*/
        getID();

    }
    Bundle extras;
    private void getID() {
     /*    etName = (TextView)findViewById(R.id.et_fullname);
        etJob = (TextView)findViewById(R.id.et_designation);
        etAddress = (TextView)findViewById(R.id.tv_address_profile);
        etMobileNumber = (TextView)findViewById(R.id.et_mobile_profile);
        profile_pic = (ImageView)findViewById(R.id.profile_pic);

        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        ivEdit.setOnClickListener(this);
*/
        extras = getIntent().getExtras();

        tvFullName = (TextView)findViewById(R.id.tv_full_name);
        tvEmail = (TextView)findViewById(R.id.tv_email_address);
        tvAddress = (TextView)findViewById(R.id.tv_address);
        tvMobileno = (TextView)findViewById(R.id.tv_mobile_number);
        findViewById(R.id.upload_image).setOnClickListener(this);
        ivImage = (ImageView) findViewById(R.id.header_imageview);
        Picasso.with(ProfileActivity.this).load(extras.getString("url")).resize(40,40).centerCrop().into(ivImage);

        tvFullName.setText(extras.getString("full_name"));
        tvEmail.setText(extras.getString("mEmail"));
        if (extras.getString("address").equals("null"))
        {
            tvAddress.setText("No Address Found");
        }else {
        tvAddress.setText(extras.getString("address"));}
        tvMobileno.setText("+91-"+ extras.getString("mobile"));

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    //For Imagge select from Camera
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResults(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                fileUri = data.getData();
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                thumbnail = getScaledBitmap(getPath(fileUri));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 10, stream);
                Log.e("image file name=",getPath(fileUri));
                isImageFromcamera=false;
                ivImage.setImageBitmap(thumbnail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void onCaptureImageResults(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        fileUri = data.getData();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isImageFromcamera=true;
        Log.e("Fle name=",destination.getAbsolutePath());
        ivImage.setImageBitmap(thumbnail);
    }


    public String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap getScaledBitmap(String file) {
        final int IMAGE_MAX_SIZE = 256;
        Bitmap bitmap = null;
        try {
            //bitmap.recycle();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(file, options);
            Log.i("DATA", "Bitmap " + bitmap);
            options.inSampleSize = calculateOptionSampleSize(options.outWidth, options.outHeight, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(file, options);
            Log.i("DATA", "Bitmap " + bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static int calculateOptionSampleSize(int outWidth, int outHeight, int disW, int disH) {
        Log.i("DATA", "[outWidth,outHeight][" + outWidth + "," + outHeight + "]");

        int widthRatio = (int) Math.ceil(outWidth / (float) disW);
        int heightRatio = (int) Math.ceil(outHeight / (float) disH);

        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio)
                return widthRatio;
            else
                return heightRatio;
        }
        return 1;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_image:
                selectImage();
                // showDialogCustom(ProfileActivity.this);
                break;
        }
    }
    EditText etFname,etLname,etAddressEdit;
    private String strFname,strLname,strAddress,fname,lname;
    Dialog dialog1;

    private void showDialogCustom(Context ctx) {

        dialog1 = new Dialog(ctx,android.R.style.Theme_Black_NoTitleBar);
        Typeface externalFont = Typeface.createFromAsset(ctx.getAssets(), "fonts/Frank.ttf");

        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.alert_dialog_edit_profile);
        dialog1.setCancelable(true);

        TextView text1 = (TextView) dialog1.findViewById(R.id.Tv);
        text1.setTypeface(externalFont, Typeface.BOLD);

        etFname = (EditText) dialog1.findViewById(R.id.et_first_name);
        etLname = (EditText) dialog1.findViewById(R.id.et_last_name);
        etAddressEdit = (EditText) dialog1.findViewById(R.id.et_address_edit);
        upload_image = (ImageView)dialog1.findViewById(R.id.upload_image);

        String[] arrayOfString = extras.getString("full_name").split("\\s+");
        fname = arrayOfString[0];
        lname = arrayOfString[1];

        etFname.setText(fname);
        etLname.setText(lname);
        etAddressEdit.setText(extras.getString("address"));

        // Button btnCancel = (Button) dialog1.findViewById(R.id.btn_cancel);
        Button buttonOk = (Button) dialog1.findViewById(R.id.btn_setting);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }*/
                //      ctx.startActivity(new Intent(Settings.ACTION_SETTINGS));

            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isOnline(ProfileActivity.this)) {
                    strFname = etFname.getText().toString().trim();
                    strLname = etLname.getText().toString().trim();
                    strAddress = etAddressEdit.getText().toString().trim();
                    UpdateProfile UpdateProfile = new UpdateProfile(strFname,strLname,strAddress);
                    UpdateProfile.execute();
                } else {
                    Utility.InternetSetting(ProfileActivity.this);
                }
/*

                if(dialog1.isShowing()){
                    dialog1.dismiss();
                }*/
                //      ctx.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
        dialog1.show();
    }

    private class UpdateProfile extends AsyncTask<String, String, String> {
        EditText etOTPField;
        private String strFname;
        String strLname;
        String strAddress;
        private ProgressDialog mProgressDialog;

        public UpdateProfile(String strFname, String strLname, String strAddress) {
            this.strFname = strFname;
            this.strLname = strLname;
            this.strAddress= strAddress;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(ProfileActivity.this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected String doInBackground(String... urls) {
            NetClientGet mNetClientGet = new NetClientGet(ProfileActivity.this);
//
            String responce = "";
            //http://webservice.cofferwallet.com/cofferdb.php?task=forgetpassword&mobile=7827505727654321
            //firstname=Ahsan&lastname=Khan&address=First Floor ,Okhla ,New Delhi
            String strMob =  Utility.getPrefsData(ProfileActivity.this,"mobile","no");
            if(!strMob.equals("no")){
                String url = Keys.UPDATE_PROFILE + "&mobile=" + strMob +"&firstname="+strFname
                        +"&lastname="+strLname+"&address="+strAddress.replaceAll(" ","%20");
                Log.e("Signup", "URL : " + url);

                responce = mNetClientGet.getDataClientData(url);
                Log.e("Signup", "responce : " + responce);
            }else{
                responce ="";
            }

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

                        if (jsonObj.getString("rtnMSG").equals("Profile Updated successfully")) {
                            // Log.e("Signup", "Password Status : " + );
                            Toast.makeText(ProfileActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                            if(dialog1.isShowing()){
                                dialog1.dismiss();
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, jsonObj.getString("rtnMSG"), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, getResources().getString(R.string.internet_connection_dialog), Toast.LENGTH_LONG).show();
                }

            }
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

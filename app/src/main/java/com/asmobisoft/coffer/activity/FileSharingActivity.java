package com.asmobisoft.coffer.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asmobisoft.coffer.MainActivity1;
import com.asmobisoft.coffer.R;
import com.asmobisoft.coffer.model.ChatRoom;
import com.asmobisoft.coffer.model.Track_Bean;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by root on 10/17/16.
 */

public class FileSharingActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvDoc;
    private TextView tvCamera;
    private TextView tvGallery;
    private TextView tvLocation;
    private TextView tvAudio;
    private TextView tvContact;
    private Toolbar toolbar;
    private ArrayList<ChatRoom> mChatList;
    private TextView tvtiitle;


    //For Image work
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    private ProgressDialog dialog;
    Uri imageUri;
    private ImageView iv_image;

    MediaPlayer mp = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filesharing);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvtiitle= (TextView)toolbar.findViewById(R.id.tv_tittle);
        tvtiitle.setText("File Sharing");
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        getid();

    }

    private void getid() {

        tvDoc = (TextView) findViewById(R.id.tv_document);
        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvGallery = (TextView) findViewById(R.id.tv_gallery);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvAudio = (TextView) findViewById(R.id.tv_audio);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        iv_image = (ImageView)findViewById(R.id.iv_image);

        tvDoc.setOnClickListener(this);
        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvAudio.setOnClickListener(this);
        tvContact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.tv_document:
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            Intent i = Intent.createChooser(intent, "File");
            startActivityForResult(i, 3);
            break;
        case R.id.tv_camera:
            cameraIntent();
            break;
        case R.id.tv_gallery:

            galleryIntent();

            break;
        case R.id.tv_location:



            break;
        case R.id.tv_audio:

            getAllSdCardTrack_Beans(FileSharingActivity.this);
//            ArrayList<Track_Bean> mtrack = getAllSdCardTrack_Beans(FileSharingActivity.this);


            break;
        case R.id.tv_contact:

            Intent intent1 = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
            startActivityForResult(intent1, 4);

            break;

    }


    }

    private void galleryIntent() {
        try {
            Intent gintent = new Intent();
            gintent.setType("image/*");
            gintent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(gintent, "Select Picture"),
                    PICK_IMAGE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    private void cameraIntent() {
        String fileName = "new-photo-name.jpg";
        //create parameters for Intent with filename
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
        //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, PICK_Camera_IMAGE);

    }

    /**
     * SD CARD QUERIES
     */
    public ArrayList<Track_Bean> getAllSdCardTrack_Beans(Context context) {
        ArrayList<Track_Bean> mTrackList = new ArrayList<Track_Bean>();

        Cursor c = context
                .getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Audio.Media._ID,
                                MediaStore.Audio.Media.DATA,
                                MediaStore.Audio.Media.ARTIST,
                                MediaStore.Audio.Media.ALBUM,
                                MediaStore.Audio.Media.DURATION,
                                MediaStore.Audio.Media.DISPLAY_NAME,
                                MediaStore.Audio.Media.ALBUM_ID }, "1=1",
                        null, null);
        if (c.moveToFirst()) {
            do {
                Track_Bean mTcBean = new Track_Bean();

                String mTitle = c
                        .getString(c
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));

                if(mTitle.trim().endsWith(".mp3") || mTitle.trim().endsWith(".MP3")){
                    mTcBean.setmTitle(mTitle);

                    String mArtist = c
                            .getString(c
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    //mTcBean.setmArtist(mArtist);
                    Log.e("FILESHARING","mArtist : "+mArtist);

                    //Media Id
                    String mId = c.getString(c
                            .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    //mTcBean.setmId(mId);
                    Log.e("FILESHARING","mId : "+mId);

                    //ALbumName
                    String mAlbumName = c
                            .getString(c
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    //mTcBean.setmAlbumName(mAlbumName);
                    Log.e("FILESHARING","mAlbumName : "+mAlbumName);

                    String mAlbumID = c
                            .getString(c
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                   // mTcBean.setmAlbumId(mAlbumID);
                    Log.e("FILESHARING","mAlbumID : "+mAlbumID);

                    //Path
                    String mPath = c
                            .getString(c
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    //mTcBean.setmPath(mPath);
//                    System.out.println("PATH"+mPath);
                    Log.e("FILESHARING","mPath : "+mPath);
                    //Duration
                    long mDuration = c
                            .getLong(c
                                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    String mFormattedDuration = DateUtils
                            .formatElapsedTime(mDuration / 1000);
                    //mTcBean.setmDuration(mFormattedDuration);
                    Log.e("FILESHARING","mDuration : "+mDuration);

                    mTrackList.add(mTcBean);
                }else{
                    System.out.println("The Track ext is not Mp3::"+mTitle);
                }

            } while (c.moveToNext());
            if (c != null)
                c.close();
        }
        System.out.println("Size of array::"+mTrackList.size());
       // mLv_tracks.setAdapter(new Audio_Post_Adapter(Audio_Fetch.this, Track_Beans));
        return mTrackList;

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
        Intent intent = new Intent(getApplicationContext(), SendingReceiveOption.class);
        intent.putExtra("screenValue","novalue");
        startActivity(intent);
        finish();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;

            case 3:

                Uri uri = data.getData();
                String fileImagePath = getRealPathFromURI(uri);
                String type = data.getType();

                Log.d("Hello", fileImagePath + "");
                if (uri != null) {
                    String path = uri.toString();
                    if (path.toLowerCase().startsWith("file://")) {
                        // Selected file/directory path is below
                        path = (new File(URI.create(path))).getAbsolutePath();
                        Log.d("Hel", path);
                    }

                }

                break;
            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
        }



        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA );
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);

        } else
            return null;
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        iv_image.setImageBitmap(bitmap);

    }

}

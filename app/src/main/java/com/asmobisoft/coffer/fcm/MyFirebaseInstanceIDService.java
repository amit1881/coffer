package com.asmobisoft.coffer.fcm;

import android.util.Log;

import com.asmobisoft.coffer.commonmethod.Utility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Gaurav on 06-03-2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        //Getting registration token
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Utility.setPrefsData(this, "FCMtoken", refreshedToken);
            //Displaying token on logcat
            Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}

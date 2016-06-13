package com.videri.helloworldselfie;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

///MAIN PROJECT TO WORK ON 6/12/16 by Ayal Fieldust

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    TakePictureFragment F1;
    ShowPictureFragment F2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        F1 = new TakePictureFragment();
        F2 = new ShowPictureFragment();

        fragmentTransaction.replace(R.id.content_layout, F1);
        fragmentTransaction.commit();

    }


    public void changeFragment(int i) {
        fragmentTransaction = fragmentManager.beginTransaction();

        if (i == 1) {
            fragmentTransaction.replace(
                    R.id.content_layout, F1);
        } else {
            fragmentTransaction.replace(
                    R.id.content_layout, F2);
        }
        fragmentTransaction.commit();

    }


    public void respond(String data) {
        Log.v(TAG, "Calling respond: " + data);
               F2.changeImagePath(data);
    }

}
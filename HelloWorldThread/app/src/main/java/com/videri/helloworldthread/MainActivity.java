/**
 * MainActivity.java
 * Videri Thread Application Sample
 * 1.0.0
 *
 * Copyright 2016 Videri Inc.
 *
 * Unless required by applicable law or agreed to in writing by both parties,
 * this sample software is distributed on an "AS IS" AND "AS AVAILABLE" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package com.videri.helloworldthread;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


/**
 * This is the main activity for the application.
 * It switches content when it receives message from VLE.
 */
public class MainActivity extends Activity {

    /**
     * Key for indicating log's TAG
     */
    private final String TAG = "MainActivity";

    /**
     * HelloWorldFragment instance for showing helloworld's content
     */
    private HelloWorldFragment helloWorldFragment;
    /**
     * MediaPlayerFragment instance for showing helloworld's content
     */
    private MediaPlayerFragment mediaPlayerFragment;

    /**
     *An index of the fragment
     */
    private int frameIndex = 1;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorldFragment = new HelloWorldFragment();
        mediaPlayerFragment = new MediaPlayerFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(
                R.id.content_layout,getFragment(frameIndex));
        fragmentTransaction.commit();


    }

    /**
     * Getting different fragment by index
     * @param index
     * @return
     */
    private Fragment getFragment(int index){
        if(index == 1)
            return helloWorldFragment;
        else
            return mediaPlayerFragment;
    }


    @Override
    protected void onPause() {
        super.onPause();


    }


    /**
     * switching content when it resume.
     */
    @Override
    protected void onResume() {
        super.onResume();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(
                R.id.content_layout,getFragment(frameIndex));
        fragmentTransaction.commit();
        frameIndex++;
        if(frameIndex>2)
            frameIndex = 1;
    }


}

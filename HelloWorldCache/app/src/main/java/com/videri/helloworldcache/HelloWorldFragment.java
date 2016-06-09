package com.videri.helloworldcache;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HelloWorldFragment extends Fragment {

    private final String TAG = "HelloWorldFragment";

    /**
     * Every 1000ms change update textview's data
     */
    private final int TEXT_UPDATE_DELAY = 1000;
    /**
     * A TextView shows helloWorld text.
     */
    private TextView helloWorldText;

    private Handler mHandler;
    /**
     * An index displays between 0 and 99
     */
    private int currentIndex = 0;
    public HelloWorldFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hello_world_fragment, container, false);
        //init textview
        helloWorldText = (TextView)view.findViewById(R.id.helloword_text);
        //init handler
        mHandler = new Handler();

        return view;
    }

    /**
     * A thread update the helloWorldText every second
     */
    private Runnable TextUpdateTask = new Runnable() {
        @Override
        public void run() {

            helloWorldText.setText("Hello World " + currentIndex);
            currentIndex++;
            if(currentIndex == 100){
                currentIndex = 0;
            }

            mHandler.postDelayed(this,TEXT_UPDATE_DELAY);
        }
    };

    /**
    * Remove thread
    */
    @Override
    public void onPause() {
        super.onPause();
        if(TextUpdateTask != null)
            mHandler.removeCallbacks(TextUpdateTask);
    }

    /**
     * Start thread
     */
    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(TextUpdateTask);
    }
}

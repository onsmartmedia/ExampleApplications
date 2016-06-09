/**
 * MainActivity.java
 * HelloWorld Application Sample
 * 1.0.0
 *
 * Copyright 2016 Videri Inc.
 *
 * Unless required by applicable law or agreed to in writing by both parties,
 * this sample software is distributed on an "AS IS" AND "AS AVAILABLE" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */



package com.videri.helloworld;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * This is the main activity for the application.
 * It displays "Hello World!" letters on the screen.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * TODO: handle all removing/pausing thread in onPause
     * Ex: timer, runnable, thread, bus
     *     if(timer != null)
     *          timer.cancel();
     *     if(runnable != null)
     *          handler.removeCallbacks(runnable);
     *     if(bus != null)
     *          bus.unregister(this);
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * TODO: handle all resuming thread in onResume()
     *     if(timer == null)
     *          timer.start();
     *     if(runnable != null)
     *          handler.post(runnable);
     *     if(bus != null)
     *          bus.register(this);
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

}

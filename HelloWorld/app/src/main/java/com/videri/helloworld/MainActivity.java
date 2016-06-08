package com.videri.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

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

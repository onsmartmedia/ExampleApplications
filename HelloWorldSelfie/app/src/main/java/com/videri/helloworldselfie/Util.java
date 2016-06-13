package com.videri.helloworldselfie;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Ayalus on 6/12/16.
 */
public class Util {

    private static final String TAG = "Util";

    public static void loadImage(Context context, ImageView imageView, Object source){
        try {
            Glide.with(context)
                    .load(source)
                    .into(imageView);
        }
        catch (Exception e){
            Log.v(TAG, "Error loadImage error: " + e.toString());
        }
    }
}

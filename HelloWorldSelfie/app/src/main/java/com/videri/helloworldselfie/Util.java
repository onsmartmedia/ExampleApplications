package com.videri.helloworldselfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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


    public static void loadImageAsBitmap(final Context context, final ImageView imageView, Object source){
        Glide
                .with(context)
                .load(source)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        imageView.setImageBitmap(flipImage(resource)); // Possibly runOnUiThread()
                    }
                });
    }


    public static Bitmap flipImage(Bitmap bmp){
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        Bitmap mirroredBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
        return mirroredBitmap;
    }
}



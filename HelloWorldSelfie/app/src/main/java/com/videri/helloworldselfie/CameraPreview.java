package com.videri.helloworldselfie;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ayalus on 6/7/16.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    //public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {
    private static boolean DEBUGGING = true;
    private SurfaceHolder mHolder;
    private Camera mCamera;

    protected Activity mActivity;
    protected Camera.Size mPreviewSize;
    protected Camera.Size mPictureSize;

    static final String vCameraPreview = "vCameraPreview";
    private int mCameraId;
    protected List<Camera.Size> mPreviewSizeList;
    protected List<Camera.Size> mPictureSizeList;

    public CameraPreview(Context context, int cameraId, Camera camera) {
        super(context);

        mActivity = (MainActivity) context; //OR TAKEPICTUREFRAGMENT.java here???
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = camera;
        mCameraId = cameraId;
        if (mCamera == null) {
            try {
                mCamera = Camera.open(cameraId);
            } catch (RuntimeException e) {
                try {
                    mCamera = Camera.open(0);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (mCamera != null) {
            Camera.Parameters cameraParams = mCamera.getParameters();
            mPreviewSizeList = cameraParams.getSupportedPreviewSizes();
            mPictureSizeList = cameraParams.getSupportedPictureSizes();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        mCamera.stopPreview();
        boolean portrait = isPortrait();
        Camera.Size previewSize = determinePreviewSize(portrait, w, h);
        Camera.Size pictureSize = determinePictureSize(previewSize);
        if (mHolder== null) {
            return;
        }
        try {
            if (mCamera != null) {
                Camera.Parameters cameraParams = mCamera.getParameters();
                //hard-coding previewSize and pictureSize because of problems with HAL
                //               cameraParams.setPreviewSize(previewSize.width, previewSize.height);
                //               cameraParams.setPictureSize(pictureSize.width, pictureSize.height);

                cameraParams.setPreviewSize(1280, 720);
                cameraParams.setPictureSize(1280, 720);
                cameraParams.setJpegQuality(100);//highest quality

                mCamera.setParameters(cameraParams);
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCamera(Camera camera) {
        if (camera == null) {
            return;
        }

        if (mHolder.getSurface() == null) {
            return;
        }

        mCamera.stopPreview();
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    /**
     * @param portrait
     * @param reqWidth must be the value of the parameter passed in surfaceChanged
     * @param reqHeight must be the value of the parameter passed in surfaceChanged
     * @return Camera.Size object that is an element of the list returned from Camera.Parameters.getSupportedPreviewSizes.
     */
    protected Camera.Size determinePreviewSize(boolean portrait, int reqWidth, int reqHeight) {
        // Meaning of width and height is switched for preview when portrait,
        // while it is the same as user's view for surface and metrics.
        // That is, width must always be larger than height for setPreviewSize.
        int reqPreviewWidth; // requested width in terms of camera hardware
        int reqPreviewHeight; // requested height in terms of camera hardware

        Camera.Size largestSupportedPreviewSize = null;
        if (portrait) {
            reqPreviewWidth = reqWidth;
            reqPreviewHeight = reqHeight;
        } else {
            reqPreviewWidth = reqHeight;
            reqPreviewHeight = reqWidth;
        }

        if (DEBUGGING) {
            Log.v(vCameraPreview, "Listing all supported preview sizes");
            for (Camera.Size size : mPreviewSizeList) {
                Log.v(vCameraPreview, "  w: " + size.width + ", h: " + size.height);
            }
            Log.v(vCameraPreview, "Listing all supported pictureCallback sizes");
            for (Camera.Size size : mPictureSizeList) {
                Log.v(vCameraPreview, "  w: " + size.width + ", h: " + size.height);
            }
        }

        // Adjust surface size with the closest aspect-ratio
        float reqRatio = ((float) reqPreviewWidth) / reqPreviewHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = 0.0f;//Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : mPreviewSizeList) {
            if(largestSupportedPreviewSize == null) {
                largestSupportedPreviewSize = size;
            }
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio == deltaRatioMin) {
                if ((retSize == null) || ((retSize.width < size.width) && (retSize.height < size.height))) {
                    retSize = size;
                }
            } else {
                if ((largestSupportedPreviewSize.width < size.width) && (largestSupportedPreviewSize.height < size.height)) {
                    largestSupportedPreviewSize = size;
                }
            }
        }
        if (largestSupportedPreviewSize != null) {
            return largestSupportedPreviewSize;
        } else {
            return retSize;
        }
    }

    protected Camera.Size determinePictureSize(Camera.Size previewSize) {
        Camera.Size retSize = null;
        for (Camera.Size size : mPictureSizeList) {
            if (size.equals(previewSize)) {
                return size;
            }
        }
        if (DEBUGGING) { Log.v(vCameraPreview, "Same pictureCallback size not found."); }

        Camera.Size largestSupportedPictureSize = null;
        // if the preview size is not supported as a pictureCallback size
        float reqRatio = ((float) previewSize.width) / previewSize.height;
        float curRatio, deltaRatio;
        float deltaRatioMin = 0.0f;//Float.MAX_VALUE;
        for (Camera.Size size : mPictureSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio == deltaRatioMin) {
                retSize = size;
            }
        }
        return retSize;
    }

    public Camera getCamera() {
        if (mCamera != null) {
            return mCamera;
        }
        return null;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        stop();
    }

    public void stop() {
        if (mCamera == null) {
            return;
        }
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public boolean isPortrait() {
        return (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
}

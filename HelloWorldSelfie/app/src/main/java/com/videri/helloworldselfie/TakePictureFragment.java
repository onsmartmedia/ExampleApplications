package com.videri.helloworldselfie;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.usb.UsbManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TakePictureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TakePictureFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private final String TAG = "Fragment One TAG-------";
    private View view;
    private ImageButton  captureBtn;
    private ImageView countDownImageView = null;
    private boolean isCountDown = false;
    private Handler mHandler;
    private int sec = 4;
    private Camera mCamera = null;
    private CameraPreview mPreview = null;
    private Camera.PictureCallback mPicture;

    public String pathOfPicture = "";
    public File pictureFile = null;
    private MediaPlayer mPlayer;
    private RelativeLayout cameraPreviewRelative;
    private boolean cameraFront = false;
    float currentVol, currentLeftVol, currentRightVol;
    float minLeftVol, minRightVol, maxLeftVol, maxRightVol;
    Toast volumeToast;
    private TextView countDownView = null;
    private TextView mCameraInfo = null;
    private static boolean mCameraInfoShowing = false;
    private static final int TAKE_PHOTO_CODE = 100;
    private static int cameraId = -1;
    BroadcastReceiver mUsbReceiver = null;
    private static boolean DEBUGGING = true;
    private boolean usbConnected = false;
    private ImageView backgroundImage = null;
    public TakePictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_take_picture, container, false);

        view = inflater.inflate(R.layout.fragment_take_picture, container, false);
        Log.v(TAG, "inflated fragment 1. now listening to button 1.");

        backgroundImage = (ImageView) view.findViewById(R.id.bg_image);
        Util.loadImage(getActivity(),backgroundImage,R.drawable.bg_selfie_1);



        countDownView = (TextView)view.findViewById(R.id.TextViewCountDown);
        //     countDownImageView = (ImageView)view.findViewById(R.id.imageViewCountDown);


        mHandler = new Handler();


        mHandler = new Handler();
        countDownView = (TextView)view.findViewById(R.id.TextViewCountDown);
        currentLeftVol = currentRightVol = 0.4f;
        currentVol = currentLeftVol;
        minLeftVol = minRightVol = 0.0f;
        maxLeftVol = maxRightVol = 1.0f;



        cameraId = findFrontFacingCamera();
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::resumeCameraActivity()..."); }
        try {
            mCamera = Camera.open(cameraId);
        } catch (RuntimeException rte) {
            Toast toast = Toast.makeText(getActivity(), "Your device does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (mCamera != null) {
            mPreview = new CameraPreview(getActivity(), cameraId, mCamera);
            if (mPreview != null) {
                mCamera = mPreview.getCamera();
                RelativeLayout surfaceViewWithTextview = (RelativeLayout) view.findViewById(R.id.preview);
                mCameraInfo = new TextView(getActivity());
                mCameraInfo.setTextColor(Color.WHITE);
                mCameraInfo.setBackgroundColor(0xFFCCCCCC);
                mCameraInfo.getBackground().setAlpha(50);//0-255
                mCameraInfo.setPadding(10, 10, 10, 10);
                mCameraInfo.setVisibility(View.GONE);
                surfaceViewWithTextview.addView(mCameraInfo);
                if (mCameraInfoShowing) {
                    showCameraInfo();
                } else {
                    hideCameraInfo();
                }
                cameraPreviewRelative = (RelativeLayout) view.findViewById(R.id.preview);
                //I took out:
                //          RelativeLayout.LayoutParams previewLayoutParamsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //          cameraPreviewRelative.setLayoutParams(previewLayoutParamsRelative);
                cameraPreviewRelative.addView(mPreview);
                initialize();
            }
        }
        //listen for new usb devices
        mUsbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if((UsbManager.ACTION_USB_DEVICE_DETACHED).equalsIgnoreCase(action)) {
                    if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::onCreate()...camera REMOVED"); }
                    Toast toast = Toast.makeText(getActivity(), "Camera removed!", Toast.LENGTH_LONG);
                    toast.show();
                    usbConnected = false;
                    releaseCamera();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        getActivity().registerReceiver(mUsbReceiver, filter);
        saveLogcatToFile(getActivity());
//added until here
        return view;
    }

    private Runnable TimerTask = new Runnable() {
        @Override
        public void run() {
            sec--;
            countDownView.setText(String.valueOf(sec));
            Log.v(TAG, (String.valueOf(sec)));
            if(sec == 0){

                ((MainActivity)getActivity()).changeFragment(2);
                isCountDown = false;
                sec = 4;
            }
            else
                mHandler.postDelayed(this,1000);
        }
    };


    View.OnClickListener buttonOneListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "button one pressed, now moving to fragment 2");
            if(!isCountDown ) {
                mHandler.post(TimerTask);
//                ((MainActivity)getActivity()).changeFragment(2);

                isCountDown = true;
            }
            ((MainActivity)getActivity()).respond("");//(pathOfPicture)

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause..................");
//        releaseCamera();
        if(TimerTask != null)
            mHandler.removeCallbacks(TimerTask);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume..................");
//        resumeCameraActivity();
        if(isCountDown == true)
            mHandler.post(TimerTask);
//        isCountDown = false;
    }

    //in case the media server died
    private void resumeCameraActivity() {
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::resumeCameraActivity()..."); }
        try {
            mCamera = Camera.open(cameraId);
        } catch (RuntimeException rte) {
            Toast toast = Toast.makeText(getActivity(), "Your device does not have a camera!", Toast.LENGTH_LONG);
            toast.show();
        }
        if (mCamera != null) {
            mPreview = new CameraPreview(getActivity(), cameraId, mCamera);
            if (mPreview != null) {
                mCamera = mPreview.getCamera();
                RelativeLayout surfaceViewWithTextview = (RelativeLayout) view.findViewById(R.id.preview);
                mCameraInfo = new TextView(getActivity());
                mCameraInfo.setTextColor(Color.WHITE);
                mCameraInfo.setBackgroundColor(0xFFCCCCCC);
                mCameraInfo.getBackground().setAlpha(50);//0-255
                mCameraInfo.setPadding(10, 10, 10, 10);
                mCameraInfo.setVisibility(View.GONE);
                surfaceViewWithTextview.addView(mCameraInfo);
                if (mCameraInfoShowing) {
                    showCameraInfo();
                } else {
                    hideCameraInfo();
                }
                cameraPreviewRelative = (RelativeLayout) view.findViewById(R.id.preview);
                //I took out:
                //          RelativeLayout.LayoutParams previewLayoutParamsRelative = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //          cameraPreviewRelative.setLayoutParams(previewLayoutParamsRelative);
                cameraPreviewRelative.addView(mPreview);
                initialize();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop..................");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy..................");

    }












    private void releaseCamera() {
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::releaseCamera().."); }
        if (mPreview != null) {
            mPreview.stop();
            mPreview.setBackgroundColor(Color.BLACK);
            mPreview = null;
        }
        // stop and release camera
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
    public int findFrontFacingCamera() {
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::findFrontFacingCamera()..."); }
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }





    public int findBackFacingCamera() {
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::findBackFacingCamera()..."); }
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {
                byte[] copy = new byte[data.length];
                System.arraycopy(data, 0, copy, 0, data.length);
                //make a new pictureCallback file
                FileOutputStream fos = null;
                Log.v(TAG, "pictureFile1: " + pictureFile);
                pictureFile = getOutputJpegFile();
//                    if (pictureFile == null) {
//                        Toast noFileToast = Toast.makeText(getActivity(),"no file saved in pictureFile", Toast.LENGTH_LONG);
//
//                    }
                Log.v(TAG, "pictureFile2: " + pictureFile);

                try {
                    //write the file
                    Log.v(TAG,"SAVING FILE NOW:");
                    fos = new FileOutputStream(pictureFile);
                    fos.write(copy);
                    fos.flush();
                    fos.close();
                    Toast toast = Toast.makeText(getActivity(), "JPG saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    toast.show();
                    pathOfPicture = pictureFile.getPath();
                    // comm.respond(pathOfPicture);
                    ((MainActivity)getActivity()).respond(pathOfPicture);
                    ((MainActivity)getActivity()).changeFragment(2);
                    Log.v(TAG, "Picture Taken. now going to preview page to show the pictureCallback.....");
                }  catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //refresh camera to continue preview
            mPreview.refreshCamera(mCamera);
        }

    };

    private static File getOutputJpegFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/com.videri.vcamera/captured", "jpg");

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }


    public void saveLogcatToFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "logcat_"+timeStamp+".txt";
        File outputFile = new File(context.getExternalCacheDir(),fileName);
        //eg. /storage/emulated/0/Android/data/com.videri.vcamera/cache/logcat_20160429_115948.txt
        try {
            Process process = Runtime.getRuntime().exec("logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void initialize() {
        if (DEBUGGING) { Log.d(TAG, "vCameraMainActivity::initialize()..."); }
//        ct.start();
        captureBtn = (ImageButton) view.findViewById(R.id.button_capture);
        Util.loadImage(getActivity(),captureBtn,R.drawable.capture);

        captureBtn.setOnClickListener(captureListener);
//        captureBtn.setEnabled(true);

    }

    View.OnClickListener captureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG,"BUTTON CLICKED........" );
            if (DEBUGGING) { Log.v(TAG, "vCameraMainActivity::captureListener.onClick().. BUTTON CLICKED........"); }
               captureBtn.setEnabled(false);

            //     mPlayer = MediaPlayer.create(getActivity(), R.raw.automatic_camera);
            //      mPlayer.start();
                  mCamera.takePicture(null, null, pictureCallback);
//            ((MainActivity)getActivity()).respond("");//(pathOfPicture)
//            ((MainActivity)getActivity()).changeFragment(2);

        }
    };

    private void showCameraInfo() {
        if (DEBUGGING) { Log.v(TAG, "pdinh...vCameraMainActivity::showCameraInfo()"); }
        if (mCamera != null) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("Focus mode: " + mCamera.getParameters().getFocusMode() + "\n");
            strBuilder.append("Focal length: " + mCamera.getParameters().getFocalLength() + "\n");
            //we only support JPEG for now
            strBuilder.append("Picture format: JPEG" + "\n");//==256
            strBuilder.append("Jpeg quality: " + mCamera.getParameters().getJpegQuality() + "(highest)\n");
            strBuilder.append("Picture size: " + mCamera.getParameters().getPictureSize().width + "x" + mCamera.getParameters().getPictureSize().height);
            mCameraInfo.setText(strBuilder);
        }

        if (mCameraInfo != null) {
            if (mCameraInfo.getVisibility() != View.VISIBLE) {
                mCameraInfo.setVisibility(View.VISIBLE);
                mCameraInfo.bringToFront();
                mCameraInfoShowing = true;
            }
        }
    }




    private void hideCameraInfo() {
        if (DEBUGGING) { Log.v(TAG, "pdinh...vCameraMainActivity::hideCameraInfo()"); }
        if (mCameraInfo != null) {
            if (mCameraInfo.getVisibility() != View.GONE) {
                mCameraInfo.setVisibility(View.GONE);
                mCameraInfoShowing = false;
            }
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

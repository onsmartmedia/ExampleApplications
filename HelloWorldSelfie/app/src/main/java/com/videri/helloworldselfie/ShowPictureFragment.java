package com.videri.helloworldselfie;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Ayalus on 6/10/16.
 */
public class ShowPictureFragment extends Fragment {


    private View view;
    private ImageButton backBtn;
    private ImageView BGImageSelfieTaken = null;
    private final String TAG = "ShowPictureFragment----";
    public String imagePath = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_picture, container, false);

        BGImageSelfieTaken = (ImageView) view.findViewById(R.id.bg_image2); //background image using glide (util class)
        Util.loadImage(getActivity(),BGImageSelfieTaken,R.drawable.bg_selfie_2);

        ImageView imageCapturd = (ImageView) view.findViewById(R.id.image_captured);
        Util.loadImage(getActivity(),imageCapturd,imagePath);

        Log.v(TAG, "inflated fragment 2. now listening to button 2.");

        backBtn = (ImageButton) view.findViewById(R.id.back_button); //back imageButton using glide (util class)
        Util.loadImage(getActivity(),backBtn,R.drawable.back_button);

        backBtn.setOnClickListener(buttonTwoListner);
        return view;
    }


    View.OnClickListener buttonTwoListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "button two pressed, now moving to fragment 1");
            ((MainActivity)getActivity()).respond("");//(pathOfPicture)
            ((MainActivity)getActivity()).changeFragment(1);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume..................");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.v(TAG, "onStop..................");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //     Log.v(TAG, "onDestroy..................");
    }




    public void changeImagePath(String data){
        Log.v(TAG, "Check image path: " + data);
        imagePath = data;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.v(TAG, "onPause..................");
//    }

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

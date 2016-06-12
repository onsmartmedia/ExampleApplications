package com.videri.helloworldselfie;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


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
    private Button buttonOne;
    private TextView countDownView = null;
    private ImageView countDownImageView = null;

    private boolean isCountDown = false;

    private Handler mHandler;

    private int sec = 4;

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
        countDownView = (TextView)view.findViewById(R.id.TextViewCountDown);
        //     countDownImageView = (ImageView)view.findViewById(R.id.imageViewCountDown);


        buttonOne = (Button) view.findViewById(R.id.button_one);
        buttonOne.setOnClickListener(buttonOneListner);
        mHandler = new Handler();

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
//            ((MainActivity)getActivity()).respond("");//(pathOfPicture)

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause..................");

        if(TimerTask != null)
            mHandler.removeCallbacks(TimerTask);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.v(TAG, "onResume..................");
        if(isCountDown == true)
            mHandler.post(TimerTask);
//        isCountDown = false;
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

package com.videri.helloworldselfie;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ayalus on 6/10/16.
 */
public class ShowPictureFragment extends Fragment {

    private final String TAG = "Fragment Two TAG--------";
    private View view;
    private Button buttonTwo;
    private TextView countDownView = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //        return super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_show_picture, container, false);
        Log.v(TAG, "inflated fragment 2. now listening to button 2.");


        buttonTwo = (Button) view.findViewById(R.id.button_two);
        buttonTwo.setOnClickListener(buttonTwoListner);


        return view;
    }


    View.OnClickListener buttonTwoListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "button two pressed, now moving to fragment 1");

//            ((MainActivity)getActivity()).respond("");//(pathOfPicture)
            ((MainActivity)getActivity()).changeFragment(1);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume..................");
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.v(TAG, "onPause..................");
//    }

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

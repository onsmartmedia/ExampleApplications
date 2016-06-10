/**
 * MediaPlayerFragment.java
 * Thread Application Sample
 * 1.0.0
 *
 * Copyright 2016 Videri Software, Inc.
 *
 * Unless required by applicable law or agreed to in writing by both parties,
 * this sample software is distributed on an "AS IS" AND "AS AVAILABLE" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.videri.helloworldthread;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.videri.helloworldthread.util.Util;

/**
 * This is the MediaPlayerFragment for the application.
 * It play onSmart videos.
 */
public class MediaPlayerFragment extends Fragment {
    private final String TAG = "MediaPlayerFragment";
    /**
     * A videoview displays video
     */
    private VideoView videoView;
    /**
     * An ImageView for displaying
     */
    private ImageView bgImage;
    /**
     * An ImageView for displaying logo
     */
    private ImageView logoImage;

    /**
     * A video file path
     */
    private String videoPath = "";
    /**
     * A videoIndex used to play different  video
     */
    private int currentVideoIndex = 0;
    /**
     * Records video stop position
     */
    private int stopPosition = 0;


    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.media_player_fragment, container, false);
        //init bgimage
        bgImage = (ImageView)view.findViewById(R.id.bg_image);

        //init logoImage
        logoImage = (ImageView)view.findViewById(R.id.logo_image);

        //get a video file path
        videoPath = "android.resource://" +
                getActivity().getPackageName() + "/" + R.raw.snapchat_bwy;
        //init videoview
        videoView = (VideoView) view.findViewById(R.id.video);
        //assign  the video file path to the videoview
        videoView.setVideoPath(getVideoPath(currentVideoIndex));
        //when video ended, play another video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentVideoIndex++;
                if(currentVideoIndex > 2)
                    currentVideoIndex = 0;
                videoView.setVideoPath(getVideoPath(currentVideoIndex));
                videoView.start();


            }
        });
        //dismiss video error dialog
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        return view;
    }

    /**
     * initialize view data
     */
    private void initViewData(){
        Util.loadImage(getActivity(),logoImage,R.drawable.logo);
        Util.loadImage(getActivity(),bgImage,R.drawable.bg);
    }

    /**
     * Return a video path by a given index
     * @param index
     *        the index determine which video to play
     * @return a file path
     *
     */
    private String getVideoPath(int index){
        if(index == 0 )
            return  "android.resource://" +
                    getActivity().getPackageName() + "/" + R.raw.snapchat_bwy;
        else
            return "android.resource://" +
                    getActivity().getPackageName() + "/" + R.raw.hudson_yards_digital_domination;
    }

    /**
     * Release video in memory
     */
    @Override
    public void onDestroy() {
        videoView.suspend();
        super.onDestroy();
    }

    /**
     * get the video stop position and pause video
     */
    @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition();
        videoView.pause();
    }

    /**
     * Resume video
     */
    @Override
    public void onResume() {
        super.onResume();
        initViewData();
        if(videoView != null) {
            videoView.seekTo(stopPosition);
            videoView.start();
        }
    }
}
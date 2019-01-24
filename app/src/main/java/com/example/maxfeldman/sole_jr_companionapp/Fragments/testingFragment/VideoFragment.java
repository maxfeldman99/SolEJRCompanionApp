package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.maxfeldman.sole_jr_companionapp.R;


public class VideoFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private VideoView videoView;
    private static int wrongTime = 0;
    private final String HAPPY = "happy";
    private final String SAD = "sad";
    private final String FINISH = " finish";
    private final String TEST = "test";
    private final String MY_URI = "android.resource://com.example.maxfeldman.sole_mobileunit/raw/";
    private final String WRONG = "wrong";
    private final String CORRECT = "correct";
    private final String WAITING = "waiting";
    private final int SECOND = 1000;
    private static int counter = 0;
    private static Uri uri = null;
    private static boolean videoLoop = true;

    private static final String ARG_TEXT = "argText";
    private String currentVideo;


    private OnFragmentInteractionListener mListener;
    //private OnDataChangedListener listener;
    final Fragment sessionFragment = new SessionFragment();
    final Fragment testFragment = new TestFragment();




    public static VideoFragment newInstance(String text)
    {
        VideoFragment fragment = new VideoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT,text);
        fragment.setArguments(args);
        return fragment;

    }



    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // to lock the landscape mode
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_fragment, container, false);
        videoView = view.findViewById(R.id.myVideoView);
        chooseEmotion(WAITING);
        if(getArguments() != null){
            currentVideo = getArguments().getString(ARG_TEXT);
            chooseEmotion(currentVideo);
        }


        // Inflate the layout for this fragment
        return view;
    }





    public void OnVideoChanged(String videoType) {
        chooseEmotion(videoType);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void executeVideo(Uri uri, final int delayTime){

        videoView.setVideoURI(uri);
        final Uri temp = uri;

//                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // this section is for looping
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        mp.setLooping(true);
//
//                    }
//                });
         videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
         {
             @Override
             public void onCompletion(MediaPlayer mediaPlayer) {
//                 counter++;
//                 while(videoLoop == true) {
//                     executeVideo(temp, 0);
//                 }

                     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentPlaceHolder, testFragment).commit();

             }
         });



            videoView.start();
            videoView.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {

                }
            }, delayTime);


        }


    public void chooseEmotion(String videoType){
        Uri uri = null;
        String pathName = null;
        switch (videoType) {

            case HAPPY:
                pathName = MY_URI + CORRECT ;
                uri = Uri.parse(pathName);
                executeVideo(uri,SECOND*3);
                break;
            case SAD:
                pathName = MY_URI + WRONG;
                uri = Uri.parse(pathName);
                executeVideo(uri,SECOND*3);
                break;
            case FINISH:
                //uri = Uri.parse(MY_URI + R.raw.youwin);
                executeVideo(uri,SECOND*3);
                break;
            case WAITING:
                pathName = MY_URI + WAITING ;
                uri = Uri.parse(pathName);
                executeVideo(uri, SECOND * 7);


                break;

        }
    }
}

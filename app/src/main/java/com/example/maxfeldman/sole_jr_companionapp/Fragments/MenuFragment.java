package com.example.maxfeldman.sole_jr_companionapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maxfeldman.sole_jr_companionapp.Controller.KotlinNetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Controller.MainController;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkController;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.DataListener;
import com.example.maxfeldman.sole_jr_companionapp.Helpers.FireBase;
import com.example.maxfeldman.sole_jr_companionapp.Lesson.LessonAdapter;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;
import com.example.maxfeldman.sole_jr_companionapp.util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */

public class MenuFragment extends Fragment implements LessonAdapter.LessonAdapterListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LessonAdapter adapter;
    private final ArrayList<Lesson> lessonList = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private SharedPreferences prefName;
    private boolean muteFlag = true;
    private MainController mainController;
    private EditText ipEditText;
    private ImageView imageVal;

    private NetworkController networkController;
    private KotlinNetworkController kotlinNetworkControllerl;
    private Utilities utilities;
    private FireBase fireBase;


    private AppCompatActivity appCompatActivity;

    public MenuFragment() {

    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        imageVal = view.findViewById(R.id.img_val);
        ipEditText = view.findViewById(R.id.ip_et);
        Button button = view.findViewById(R.id.validateButton);
        setAdapter(lessonList);
        playIntroSound();
        final ImageButton mute = view.findViewById(R.id.unmute_sound_ib);
        mute.setImageResource(R.drawable.ic_volume_off_black_24dp);

        mainController = MainController.getInstance();
        networkController = NetworkController.getInstance();
        kotlinNetworkControllerl = KotlinNetworkController.INSTANCE;
        utilities = Utilities.getInstance();

        String ip = mainController.getIp();

        if(ip != null)
        {
            ipEditText.setText(ip);
        }


        prefName = getActivity().getSharedPreferences("prefName", Context.MODE_PRIVATE);


        fireBase = FireBase.getInstance();

        fireBase.getAllLessons(new DataListener()
        {
            @Override
            public void onDataLoad(Object o)
            {
                List<Lesson> tempList = (List) o;

                for(Lesson l: tempList)
                {
                    lessonList.add(l);
                }

                adapter.notifyDataSetChanged();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = ipEditText.getText().toString();

                if(!ip.equals(mainController.ip))
                {
                    validateIP(ip);
                }

            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!muteFlag){
                        mute.setImageResource(R.drawable.ic_volume_off_black_24dp);
                        muteFlag = true;
                        muteSound();
                    }else{

                        mute.setImageResource(R.drawable.ic_unmute);
                        muteFlag = false;
                        unmuteSound();
                    }


            }
        });





        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

        if(mainController.ipValidated){
            mediaPlayer.stop();
            mainController.testLastScenario = false;
            goToLesson(lessonList.get(position));
        }else{
            Toast.makeText(getContext(), "Please Enter a Valid IP Address of Destination Unit", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getContext(), "long clicked", Toast.LENGTH_SHORT).show();
    }

    private void setAdapter(ArrayList<Lesson> adapterWithData){
        adapter = new LessonAdapter(this.getActivity(),adapterWithData);
        adapter.setMyClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void goToLesson(Lesson lesson){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        QuestionFragment questionFragment = new QuestionFragment();

        questionFragment.setActivity(appCompatActivity);


        fragmentManager.beginTransaction().replace(R.id.SplashActivity,questionFragment,"QuestionFragment").commitNow();
        questionFragment.updateData(lesson,"lesson");
        Utilities.getInstance().getServer().setListener(questionFragment);

    }

    private void validateIP(final String inputIP)
    {
          final Pattern IP_ADDRESS
                = Pattern.compile(
                "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))");
        Matcher matcher = IP_ADDRESS.matcher(inputIP);
        if (matcher.matches()) {
            kotlinNetworkControllerl.validateIp(inputIP, new updateFragment() {
                @Override
                public void updateData(Object data, String type) {
                    String result = (String) data;

                    if (result.equals("valid")) {
                        imageVal.setImageResource(R.drawable.ic_done);
                        imageVal.setVisibility(View.VISIBLE);
                        mainController.setIp(inputIP);
                        mainController.ipValidated = true;

                    } else {
                        imageVal.setImageResource(R.drawable.ic_error);
                        imageVal.setVisibility(View.VISIBLE);
                        mainController.ipValidated = false;
                    }

                }
            });


        }
    }

    private void playIntroSound(){
        mediaPlayer = MediaPlayer.create(getContext(),R.raw.intro);
        //mediaPlayer.start();

    }

    private void muteSound(){
        mediaPlayer.pause();
    }

    private void unmuteSound(){
        mediaPlayer.start();
}

    @Override
    public void onResume()
    {
        super.onResume();

        String ip = mainController.getIp();

        if(ip != null)
        {
            ipEditText.setText(ip);
        }

        if(mainController.ipValidated)
        {
            imageVal.setImageResource(R.drawable.ic_done);
            imageVal.setVisibility(View.VISIBLE);

        }
    }

}

package com.example.maxfeldman.sole_jr_companionapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxfeldman.sole_jr_companionapp.Activity.MainActivity;
import com.example.maxfeldman.sole_jr_companionapp.Controller.MainController;
import com.example.maxfeldman.sole_jr_companionapp.Controller.NetworkTest;
import com.example.maxfeldman.sole_jr_companionapp.Lesson.LessonAdapter;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.Models.updateFragment;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements LessonAdapter.LessonAdapterListener {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private LessonAdapter adapter;
    final ArrayList<Lesson> lessonList = new ArrayList<>();
    boolean isValid = false;
    SharedPreferences.Editor editor;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);

        editor = sharedPref.edit();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        TextView textView = view.findViewById(R.id.tv_item_1);
        ImageView imageView = view.findViewById(R.id.iv_item_1);
        final ImageView imageVal = view.findViewById(R.id.img_val);
        final EditText ipEditText = view.findViewById(R.id.ip_et);
        Button button = view.findViewById(R.id.validateButton);
        setAdapter(lessonList);

        sharedPref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String ipAdress = sharedPref.getString("ip", "");
        ipEditText.setText(ipAdress);

        final ArrayList<String> lessonsList = new ArrayList<>();
        lessonsList.add("https://api.myjson.com/bins/mlk5y");
        lessonsList.add("https://api.myjson.com/bins/10vsl6");
        lessonsList.add("https://api.myjson.com/bins/s0ne2");
        //lessonsList.add("https://api.myjson.com/bins/6abt6");
        lessonsList.add("https://api.myjson.com/bins/19kxqi");


        // math https://api.myjson.com/bins/10vsl6
        // vehicles https://api.myjson.com/bins/s0ne2


        NetworkTest networkTest = NetworkTest.INSTANCE;

        for (int i = 0; i<lessonsList.size() ; i++)
        {
            networkTest.getLessonFromUrl(lessonsList.get(i), new updateFragment<Object>()
            {

                @Override
                public void updateData(Object data)
                {
                    Lesson lesson = (Lesson) data;
                    lessonList.add(lesson);
                    adapter.notifyDataSetChanged();
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip = ipEditText.getText().toString();
                 isValid = validateIP(ip);
                if(isValid == true){
                    imageVal.setImageResource(R.drawable.ic_done);
                    imageVal.setVisibility(View.VISIBLE);


                    editor.putString(ip, "ip");
                    editor.apply();


                }else{
                    imageVal.setImageResource(R.drawable.ic_error);
                    imageVal.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

        if(isValid){
            goToLesson(lessonList.get(position));
        }else{
            Toast.makeText(getContext(), "Please Enter a Valid IP Address of Destination Unit", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getContext(), "long clicked", Toast.LENGTH_SHORT).show();
    }

    public void setAdapter(ArrayList<Lesson> adapterWithData){
        adapter = new LessonAdapter(this.getActivity(),adapterWithData);
        adapter.setMyClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void goToLesson(Lesson l){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        QuestionFragment questionFragment = new QuestionFragment();
        //Intent intent = new Intent(getContext(),MainActivity.class);
       // startActivity(intent);
        fragmentManager.beginTransaction().replace(R.id.SplashActivity,questionFragment,"QuestionFragment").commitNow();

        questionFragment.updateData(l);

    }

    private boolean validateIP(String inputIP){
          final Pattern IP_ADDRESS
                = Pattern.compile(
                "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                        + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                        + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                        + "|[1-9][0-9]|[0-9]))");
        Matcher matcher = IP_ADDRESS.matcher(inputIP);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}

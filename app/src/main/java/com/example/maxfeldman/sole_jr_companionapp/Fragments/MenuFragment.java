package com.example.maxfeldman.sole_jr_companionapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxfeldman.sole_jr_companionapp.Controller.MainController;
import com.example.maxfeldman.sole_jr_companionapp.Lesson.LessonAdapter;
import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements LessonAdapter.LessonAdapterListener {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private LessonAdapter adapter;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        TextView textView = view.findViewById(R.id.tv_item_1);
        ImageView imageView = view.findViewById(R.id.iv_item_1);
        ArrayList<Lesson> lessonList = new ArrayList<>();

        for (int i = 0; i <10 ; i++) {
            Lesson lesson1 = new Lesson();
            lesson1.setTitle("Lesson "+"".concat(String.valueOf(i)));
            lessonList.add(lesson1);
        }
        setAdapter(lessonList);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getContext(), "long clicked", Toast.LENGTH_SHORT).show();
    }

    public void setAdapter(ArrayList<Lesson> adapterWithData){
        adapter = new LessonAdapter(this.getActivity(),adapterWithData);
        adapter.setMyClickListener(this);
        recyclerView.setAdapter(adapter);

    }
}

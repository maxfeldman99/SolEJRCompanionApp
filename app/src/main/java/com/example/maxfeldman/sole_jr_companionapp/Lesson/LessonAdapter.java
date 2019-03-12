package com.example.maxfeldman.sole_jr_companionapp.Lesson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxfeldman.sole_jr_companionapp.Models.Lesson;
import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.ArrayList;
import java.util.Random;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {


    ArrayList<Lesson> lessonList;
    Context context;
    LessonAdapterListener lessonAdapterListener;

    public LessonAdapter(Context context, ArrayList<Lesson> list)
    {
        this.context = context;
        this.lessonList = list;
    }

    public void setMyClickListener(LessonAdapterListener listener) /// for clicklistener
    {
        this.lessonAdapterListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }


    public void onBindViewHolder(LessonAdapter.MyViewHolder holder, int position)
    {

        //holder.imageView.setImageBitmap(userList.get(position).getPhoto());
        holder.nameTv.setText(lessonList.get(position).getTitle()+"");
        holder.amountTv.setText("Number of Questions: ".concat(String.valueOf(new Random().nextInt(10))));

    }

    @Override
    public int getItemCount()
    {
        return lessonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTv;
        TextView amountTv;
        ImageView imageView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            amountTv = itemView.findViewById(R.id.tv_item_2);
            nameTv = itemView.findViewById(R.id.tv_item_1);
            imageView = itemView.findViewById(R.id.iv_item_1);

            itemView.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    lessonAdapterListener.onItemClick(v,getAdapterPosition());

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v)
                {
                    lessonAdapterListener.onItemLongClick(v,getAdapterPosition());
                    return true;
                }
            });

        }
    }

    public interface LessonAdapterListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }
}

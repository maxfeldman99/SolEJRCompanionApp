package com.example.maxfeldman.sole_jr_companionapp.Fragments.testingFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maxfeldman.sole_jr_companionapp.R;

import java.util.ArrayList;
import java.util.List;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpotClickFragment extends Fragment implements OnClickableAreaClickedListener {

    private ImageView imageClick;

    public SpotClickFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spot_click, container, false);
        imageClick = view.findViewById(R.id.image_click);

        imageClick.setImageResource(R.drawable.flamingo);

        ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(imageClick), this);
        // Initialize your clickable area list
        List<ClickableArea> clickableAreas = new ArrayList<>();

        // Define your clickable areas
        // parameter values (pixels): (x coordinate, y coordinate, width, height) and assign an object to it
        clickableAreas.add(new ClickableArea(500, 200, 125, 200,new String("ssss")));
        clickableAreas.add(new ClickableArea(400, 200, 125, 200,new String("sss")));
        clickableAreas.add(new ClickableArea(300, 200, 125, 200,new String("ss")));
        //clickableAreas.add(new ClickableArea(600, 440, 130, 160, new Character("Bart", "Simpson")));

        // Set your clickable areas to the image
        clickableAreasImage.setClickableAreas(clickableAreas);


        return view;
    }

    @Override
    public void onClickableAreaTouched(Object item) {
//        if (item instanceof Character) {
//            String text = ((Character) item).getFirstName() + " " + ((Character) item).getLastName();
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//        }
        if (item instanceof String) {
            String text = "test";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }

    }
}

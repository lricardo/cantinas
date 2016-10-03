package pt.ua.cantinas.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pt.ua.cantinas.R;
import pt.ua.cantinas.models.Menu;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Get the bundle associated to the fragment
        Bundle bundle = getArguments();

        // Get the menus
        ArrayList<Menu> menus = bundle.getParcelableArrayList("menus");

        // Inflate the views


        // Inflate the layout for this fragment
        return view;
    }

}

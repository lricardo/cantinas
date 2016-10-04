package pt.ua.cantinas.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pt.ua.cantinas.R;
import pt.ua.cantinas.adapters.ItemAdapter;
import pt.ua.cantinas.models.Item;
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

        // Inflate and give the button action
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.go_back_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        // Get the bundle associated to the fragment
        Bundle bundle = getArguments();

        // Get the menus, base on the system time
        ArrayList<Menu> menus = bundle.getParcelableArrayList("menus");
        String canteenName = bundle.getString("canteen_name");

        ArrayList<Item> menuItems = new ArrayList<>();
        Date dateNow = new Date();
        String canteenCloseTimeStr = "14:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date canteenCloseTime = null;

        try {
           canteenCloseTime = simpleDateFormat.parse(canteenCloseTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar1 = GregorianCalendar.getInstance();
        Calendar calendar2 = GregorianCalendar.getInstance();

        calendar1.setTime(dateNow);
        calendar2.setTime(canteenCloseTime);

        Menu candidate =  null;
        TextView lunchDinner = (TextView) view.findViewById(R.id.lunch_diner_designation);

        // Closed? Show only the lunch
        if (calendar1.get(Calendar.HOUR_OF_DAY) < calendar2.get(Calendar.HOUR_OF_DAY) ||
                calendar1.get(Calendar.HOUR_OF_DAY) == calendar2.get(Calendar.HOUR_OF_DAY) &&
                        calendar1.get(Calendar.MINUTE) <= calendar2.get(Calendar.MINUTE)) {

            //Set image
            lunchDinner.setText("Almoço");

            for (Menu menu: menus) {
                if (menu.getMeal().equals("Almoço")) {
                    candidate = menu;
                    break;
                }
            }
        }
        else {
            //Set image
            lunchDinner.setText("Jantar");

            for (Menu menu: menus) {
                if (menu.getMeal().equals("Jantar")) {
                    candidate = menu;
                    break;
                }
            }
        }

        menuItems.addAll(candidate.getItems());

        // Set canteen name
        TextView textView = (TextView) view.findViewById(R.id.canteen_name_text_view);
        textView.setText(canteenName);

        // Inflate the views
        ListView listView = (ListView) view.findViewById(R.id.item_list_view);
        listView.setAdapter(new ItemAdapter(getActivity(), menuItems));

        // Inflate the layout for this fragment
        return view;
    }

}

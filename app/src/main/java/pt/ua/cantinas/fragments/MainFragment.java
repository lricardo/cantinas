package pt.ua.cantinas.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import pt.ua.cantinas.R;
import pt.ua.cantinas.activities.MainActivity;
import pt.ua.cantinas.adapters.CanteensAdapter;
import pt.ua.cantinas.models.Canteen;
import pt.ua.cantinas.models.Item;
import pt.ua.cantinas.models.Menu;
import pt.ua.cantinas.tasks.FetchMenusTask;

/**
 * The main fragment with RecyclerView and Card List of Canteens.
 */
public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Menu> mMenus;
    private ArrayList<Canteen> mCanteens;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);



        mCanteens = getOpenedCanteens();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.canteen_recycler_view);

        mAdapter = new CanteensAdapter(getActivity(), mCanteens);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setClickable(true);

        // Get the refresh layout
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_main_layout);
        // Add a refresh listner
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // When user hits refresh
                if (((MainActivity) getActivity()).checkConnection()) {
                    try {
                        new FetchMenusTask().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    // Clear adapter
                    ((CanteensAdapter) mAdapter).clear();

                    // Update the adapter
                    ((CanteensAdapter) mAdapter).addAll(getOpenedCanteens());

                    swipeRefreshLayout.setRefreshing(false);


                }
                else {
                    Toast.makeText(getActivity(), "Sem conexão. Por favor, tente mais tarde.",
                            Toast.LENGTH_LONG).show();

                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public ArrayList<Canteen> getOpenedCanteens () {
        // Get system date
        Date dateNow = new Date();
        String canteenCloseDateStr = "14:30";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date canteenCloseDate = null;

        try {
           canteenCloseDate = simpleDateFormat.parse(canteenCloseDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get last menu sync date
        Menu last = Menu.last(Menu.class);
        List<Menu> lastMenus = Menu.find(Menu.class, "date = ?", last.getDate().getTime()+"");
        ArrayList<Canteen> openedCanteens = new ArrayList<>();

        if (dateNow.before(canteenCloseDate)) {
            for (Menu menu: lastMenus) {
                if (menu.getMeal().equals("Almoço") && menu.isDisabled() == false) {
                    openedCanteens.addAll(Canteen.find(Canteen.class, "name = ?", menu.getCanteen()));
                }
            }
        }
        else {
            for (Menu menu: lastMenus) {
                if (menu.getMeal().equals("Jantar") && menu.isDisabled() == false) {
                    openedCanteens.addAll(Canteen.find(Canteen.class, "name = ?", menu.getCanteen()));
                }
            }
        }

        return openedCanteens;
    }

}

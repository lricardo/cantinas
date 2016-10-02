package pt.ua.cantinas.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import pt.ua.cantinas.R;
import pt.ua.cantinas.adapters.CanteensAdapter;
import pt.ua.cantinas.models.Canteen;
import pt.ua.cantinas.models.Menu;
import pt.ua.cantinas.tasks.FetchMenusTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Map<String, ArrayList<Menu>> mMenus;
    private Set<String> mCanteens;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Fetch meal
        try {
            mMenus = new FetchMenusTask().execute().get();

            if (mMenus == null) {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            else {
                mCanteens = mMenus.keySet();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.canteen_recycler_view);

        // Filter canteens, since some are not open
        ArrayList<String> canteens = new ArrayList<String>();
        for (String canteen: mCanteens) {
            if (mMenus.get(canteen).size() > 0) {
                canteens.add(canteen);
            }
        }

        mAdapter = new CanteensAdapter(getActivity(), canteens);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setClickable(true);

        // Inflate the layout for this fragment
        return view;
    }

}

package pt.ua.cantinas.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import pt.ua.cantinas.R;
import pt.ua.cantinas.fragments.MainFragment;
import pt.ua.cantinas.models.Canteen;
import pt.ua.cantinas.models.Item;
import pt.ua.cantinas.models.Menu;
import pt.ua.cantinas.tasks.FetchMenusTask;

public class MainActivity extends AppCompatActivity {

    private Map<Menu, ArrayList<Item>> mMenus;
    private Set<String> mCanteens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Execute async task
            new FetchMenusTask().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Bundle for sending the menus to the fragment
        Bundle bundle = new Bundle();
        bundle.putSerializable("menus_dictionary", (Serializable) mMenus);

        // We can't send an intent to a fragment, but the fragment has a way for that
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, mainFragment);
        fragmentTransaction.commit();
    }
}

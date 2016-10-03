package pt.ua.cantinas.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkConnection()) {
        try {
            // Execute async task
            new FetchMenusTask().execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        }
        else {
            Toast.makeText(
                    this,
                    "Smartphone sem conexão. A App utilizará os últimos dados guardados em memória.",
                    Toast.LENGTH_LONG).show();
        }

        // We can't send an intent to a fragment, but the fragment has a way for that
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, new MainFragment());
        fragmentTransaction.commit();
    }

    /**
     * Verify if there is an internet connection.
     * @return true if there is connection, false otherwise
     */
    public boolean checkConnection () {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;

    }
}

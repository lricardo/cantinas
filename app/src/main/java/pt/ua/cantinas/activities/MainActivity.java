package pt.ua.cantinas.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pt.ua.cantinas.R;
import pt.ua.cantinas.fragments.MainFragment;
import pt.ua.cantinas.models.Menu;
import pt.ua.cantinas.tasks.FetchMenusTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, new MainFragment());
        fragmentTransaction.commit();
    }
}

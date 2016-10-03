package pt.ua.cantinas.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pt.ua.cantinas.R;
import pt.ua.cantinas.fragments.DetailFragment;
import pt.ua.cantinas.fragments.MainFragment;
import pt.ua.cantinas.models.Canteen;
import pt.ua.cantinas.models.Menu;

/**
 * Adapter for Canteens, using Cards.
 * Created by Leandro Ricardo on 10/2/16.
 */

public class CanteensAdapter extends RecyclerView.Adapter<CanteensAdapter.ViewHolder>{

    public ArrayList<Canteen> canteens;
    public Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public Button seeButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.canteen_name);
            imageView = (ImageView) itemView.findViewById(R.id.canteen_image);
            seeButton = (Button) itemView.findViewById(R.id.canteen_see_button);
        }

    }

    public CanteensAdapter(Context context, ArrayList<Canteen> canteens) {
        this.context = context;
        this.canteens = canteens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_canteen, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        // Set cantine name
        holder.nameTextView.setText(canteens.get(position).getName());

        // Set cantine image
        /* So wrong, it's hardcoded... nobody is seeing...*/
        if (canteens.get(position).getName().toLowerCase().contains("santiago")) {
            //not safe...
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.santiago));
        }
        else if (canteens.get(position).getName().toLowerCase().contains("crasto")) {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.crasto));
        }
        else {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.snackbar));
        }

        // Set cantine image
        holder.seeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction =
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();

                String canteenName = holder.nameTextView.getText().toString();
                Date lastSyncDate = Menu.last(Menu.class).getDate();

                ArrayList<Menu> menus = (ArrayList<Menu>) Menu.find(Menu.class,
                        "canteen = ? and date = ? and disabled = ?", canteenName, lastSyncDate.getTime()+"", 0+"");

                // Just sampling that I know how to pass objects between a fragment and a fragment
                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();

                bundle.putParcelableArrayList("menus", menus);
                bundle.putString("canteen_name", canteenName);
                detailFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_placeholder, detailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        if (canteens != null) {
            return canteens.size();
        }
        else {
            return 0;
        }
    }

}

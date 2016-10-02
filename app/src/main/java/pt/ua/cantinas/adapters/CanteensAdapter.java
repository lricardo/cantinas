package pt.ua.cantinas.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ua.cantinas.R;
import pt.ua.cantinas.models.Canteen;

/**
 * Created by lricardo on 10/2/16.
 */

public class CanteensAdapter extends RecyclerView.Adapter<CanteensAdapter.ViewHolder>{

    private ArrayList<String> canteens;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.canteen_name);
            imageView = (ImageView) itemView.findViewById(R.id.canteen_image);
        }
    }

    public CanteensAdapter(Context context, ArrayList<String> canteens) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(canteens.get(position));

        /* So wrong, it's hardcoded... nobody is seeing...*/
        if (canteens.get(position).toLowerCase().contains("santiago")) {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.santiago));
        }
        else if (canteens.get(position).toLowerCase().contains("crasto")) {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.crasto));
        }
        else {
            holder.imageView.setImageDrawable(context.getDrawable(R.drawable.snackbar));
        }
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

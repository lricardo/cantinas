package pt.ua.cantinas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ua.cantinas.R;
import pt.ua.cantinas.models.Item;

/**
 * Created by lricardo on 10/3/16.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the view is being reused. If not, inflate it
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_item, parent, false);
        }

        // Get data for this position
        Item item = getItem(position);

        // Lookup for the view and inflate the view with data
        TextView nameTextView = (TextView) convertView.findViewById(R.id.item_name);
        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.item_description);

        nameTextView.setText(item.getName());
        descriptionTextView.setText(item.getText());

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}

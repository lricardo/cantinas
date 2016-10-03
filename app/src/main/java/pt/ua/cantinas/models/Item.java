package pt.ua.cantinas.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by lricardo on 10/1/16.
 */

public class Item  extends SugarRecord implements Parcelable {
    private String name;
    private String text;
    private Menu menu;

    private Item() {

    }

    public Item(String name, String text, Menu menu) {
        this.name = name;
        this.text = text;
        this.menu = menu;
    }


    protected Item(Parcel in) {
        name = in.readString();
        text = in.readString();
        menu = in.readParcelable(Menu.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(text);
        dest.writeParcelable(menu, 2);
    }
}

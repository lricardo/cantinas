package pt.ua.cantinas.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by lricardo on 10/1/16.
 */

public class Canteen extends SugarRecord implements Parcelable {
    private String name;

    public Canteen() {

    }

    public Canteen(String name) {
        this.name = name;
    }

    protected Canteen(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Canteen> CREATOR = new Creator<Canteen>() {
        @Override
        public Canteen createFromParcel(Parcel in) {
            return new Canteen(in);
        }

        @Override
        public Canteen[] newArray(int size) {
            return new Canteen[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}

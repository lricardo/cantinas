package pt.ua.cantinas.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lricardo on 10/1/16.
 */

public class Menu extends SugarRecord implements Parcelable {
    private String canteen;
    private String meal;
    private Date date;
    private String weekday;
    private String weekdayNr;
    private boolean disabled;

    /**
     * Empty constructor for SugarORM
     */
    public Menu() {
    }

    /**
     *
     * @param canteen
     * @param date
     * @param disabled
     * @param meal
     * @param weekday
     * @param weekdayNr
     */
    public Menu(String canteen, Date date, boolean disabled, String meal, String weekday, String weekdayNr) {
        this.canteen = canteen;
        this.date = date;
        this.disabled = disabled;
        this.meal = meal;
        this.weekday = weekday;
        this.weekdayNr = weekdayNr;
    }

    protected Menu(Parcel in) {
        canteen = in.readString();
        meal = in.readString();
        weekday = in.readString();
        weekdayNr = in.readString();
        disabled = in.readByte() != 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getCanteen() {
        return canteen;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getMeal() {
        return meal;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getWeekdayNr() {
        return weekdayNr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(canteen);
        dest.writeString(meal);
        dest.writeString(weekday);
        dest.writeString(weekdayNr);
        dest.writeByte((byte) (disabled ? 0 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (disabled != menu.disabled) return false;
        if (canteen != null ? !canteen.equals(menu.canteen) : menu.canteen != null) return false;
        if (meal != null ? !meal.equals(menu.meal) : menu.meal != null) return false;
        if (date != null ? !date.equals(menu.date) : menu.date != null) return false;
        if (weekday != null ? !weekday.equals(menu.weekday) : menu.weekday != null) return false;
        return weekdayNr != null ? weekdayNr.equals(menu.weekdayNr) : menu.weekdayNr == null;

    }

    @Override
    public int hashCode() {
        int result = canteen != null ? canteen.hashCode() : 0;
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (weekday != null ? weekday.hashCode() : 0);
        result = 31 * result + (weekdayNr != null ? weekdayNr.hashCode() : 0);
        result = 31 * result + (disabled ? 1 : 0);
        return result;
    }
}

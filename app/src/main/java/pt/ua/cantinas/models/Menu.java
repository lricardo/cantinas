package pt.ua.cantinas.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lricardo on 10/1/16.
 */

public class Menu {
    private String canteen;
    private String meal;
    private Date date;
    private String weekday;
    private String weekdayNr;
    private boolean disabled;
    private ArrayList<Item> items;

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
        this.items  = new ArrayList<>();
    }

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

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
}

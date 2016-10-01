package pt.ua.cantinas.models;

/**
 * Created by lricardo on 10/1/16.
 */

public class Item {
    private String name;
    private String text;


    public Item(String name, String text) {
        this.name = name;
        this.text = text;
    }

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
}

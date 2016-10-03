package pt.ua.cantinas.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pt.ua.cantinas.models.Canteen;
import pt.ua.cantinas.models.Item;
import pt.ua.cantinas.models.Menu;

/**
 * AsyncTask which gathers information from a XML webservice.
 * Created by Leandro Ricardo on 10/1/16.
 */

public class FetchMenusTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {


        Map<Menu, ArrayList<Item>> menuMap = new HashMap<>();

        try {
            URL url = new URL("http://services.web.ua.pt/sas/ementas");
            URLConnection conn = url.openConnection();

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = documentBuilder.parse(conn.getInputStream());

            // Get menu items
            NodeList menus = doc.getElementsByTagName("menu");

            for (int i = 0; i < menus.getLength(); i++) {
                // Get attributes as a map
                NamedNodeMap attributes = menus.item(i).getAttributes();

                // If the number of attributes is zero, pass
                if (attributes.getLength() == 0) {
                    continue;
                }

                // Else, create a menu and fill it with items

                /* Filter and treat String fields */
                SimpleDateFormat parser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");

                Date date = parser.parse(attributes.getNamedItem("date").getNodeValue());
                boolean disabled = attributes.getNamedItem("disabled").getNodeValue().length() > 1;


                Menu menu = new Menu(
                        attributes.getNamedItem("canteen").getNodeValue(),
                        date,
                        disabled,
                        attributes.getNamedItem("meal").getNodeValue(),
                        attributes.getNamedItem("weekday").getNodeValue(),
                        attributes.getNamedItem("weekdayNr").getNodeValue()
                );

                // Initialize map, if need
                if (menuMap.get(menu) == null) {
                    menuMap.put(menu, new ArrayList<Item>());
                }

                // Iterate the map
                if (menu.isDisabled() == false) {

                    NodeList menu_items = menus.item(i).getChildNodes();

                    for (int j = 0; j < menu_items.getLength(); j++) {

                        if (menu_items.item(j).getNodeType() == Node.ELEMENT_NODE) {

                            NodeList item_list = menu_items.item(j).getChildNodes();

                            for (int k = 0; k < item_list.getLength(); k++) {

                                if (item_list.item(k).getNodeType() == Node.ELEMENT_NODE) {

                                    Item item = new Item(
                                            item_list.item(k).getAttributes().getNamedItem("name").getTextContent(),
                                            item_list.item(k).getTextContent(),
                                            menu
                                    );

                                    menuMap.get(menu).add(item);
                                }
                            }
                        }
                        else {
                            continue;
                        }
                    }

                }



            }   // for-loop ends

            // Verify the existence of new menus
            long count = Menu.count(Menu.class);

            if (count != 0) {
                Menu lastMenu = Menu.last(Menu.class);

                for(Menu menu: menuMap.keySet()) {
                    if (menu.equals(lastMenu)) {
                        return null;
                    }
                }
            }

            // If there are not repeated entries, save them (assuming that the WS is integral)
            for(Menu menu: menuMap.keySet()) {
                for(Item item: menuMap.get(menu)) {
                    item.save();
                }
                menu.save();
            }

            // Verify the existence of new canteens
            for(Menu menu: menuMap.keySet()) {

                List<Canteen> canteens = Canteen.findWithQuery(Canteen.class, "Select * from Canteen where name = ?", menu.getCanteen());

                if (canteens.size() == 0) {
                    Canteen canteen = new Canteen(menu.getCanteen());
                    canteen.save();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // There was an error, returns null
        return null;
    }

}

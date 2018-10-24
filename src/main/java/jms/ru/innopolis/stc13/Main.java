package jms.ru.innopolis.stc13;

import com.google.gson.Gson;
import jms.ru.innopolis.stc13.request.Item;
import jms.ru.innopolis.stc13.request.ItemRequest;
import jms.ru.innopolis.stc13.request.ItemResponse;

import javax.jms.JMSException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<Integer, Item> items = new HashMap<>();

    public static void main(String[] args) {
        createList();
        boolean cycleFlag = true;
        try {
            while (cycleFlag) {
                Receiver receiver = new Receiver("itemsDao.in");
                String request = receiver.receiveMessage();
                ItemRequest itemRequest = new Gson().fromJson(request, ItemRequest.class);
                Item item = items.get(itemRequest.getItemId());
                ItemResponse response = new ItemResponse(itemRequest.getId(), item);
                receiver.close();
                System.out.println(itemRequest);
                Thread.sleep(3000);

                Sender sender = new Sender("itemsDao.out");
                sender.sendMessage(response);
                sender.close();
                Thread.sleep(3000);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createList() {
        items.put(1, new Item(1, "Nokia 3310", "mobiles", 10500.00, "description", "mobiles"));
        items.put(2, new Item(2, "Siemens c35", "mobiles", 14500.00, "description", "mobiles"));
        items.put(3, new Item(3, "Apple Iphone 1", "mobiles", 20500.00, "description", "mobiles"));
        items.put(4, new Item(4, "Samsung Galaxy", "mobiles", 25500.00, "description", "mobiles"));
    }

}

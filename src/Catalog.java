import java.util.HashMap;
import java.util.Map;

public class Catalog<T extends LibraryItem> {
    private Map<Integer, T> items;

    public Catalog() {
        items = new HashMap<>();
    }

    public void addItem(T item) {
        items.put(item.getItemID(), item);
    }

    public void removeItem(int itemID) {
        if (items.containsKey(itemID)) {
            items.remove(itemID);
        } else {
            System.out.println("Item with ID " + itemID + " does not exist.");
        }
    }

    public T getItem(int itemID) {
        return items.get(itemID);
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("Catalog is empty.");
        } else {
            for (T item : items.values()) {
                System.out.println(item);
            }
        }
    }
}

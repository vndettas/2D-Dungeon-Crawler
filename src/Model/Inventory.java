package Model;

import Items.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

    private List<Item> inventory;

    private int capacity = 5;

public boolean isFull(){
    return inventory.size() == 5;
}
    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public Item getItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            return inventory.get(index);
        }
        return null;
    }

    public void useItem(int index){
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.get(index);
            item.use();
            inventory.remove(index);
        }
    }

    public List<Item> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
    public void removeItem(Item item) {
        inventory.remove(item);
    }
}

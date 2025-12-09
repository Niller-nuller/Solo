package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {

    private final double maxPlayerCarryWeight = 50.00;
    private double currentPlayerCarryWeight = 0;

    private final List<Item> playerInventoryItems = new ArrayList<Item>();
    private final Slots inventorySlots = new Slots();

    public Inventory() {}


    public double getMaxPlayerCarryWeight() {return maxPlayerCarryWeight;}

    public double getCurrentPlayerCarryWeight() {
        return currentPlayerCarryWeight;
    }

    public List<Item> getPlayerInventoryItems() {
        return playerInventoryItems;
    }

    //Method to add an item to the player object's List
    public void addItem(Item item) {
        playerInventoryItems.add(item);
        recalculateWeightAndSlots();
    }

    public void recalculateWeightAndSlots(){
        double totalWeight = 0.00;
        int usedSlots = 0;

        Map<String, Integer> consumableCount = new HashMap<>();

        for (Item i : playerInventoryItems){
            totalWeight += i.getWeight();
            if(i instanceof Consumable c && c.isStackable()) {
                consumableCount.put(c.getName(), consumableCount.getOrDefault(c.getName(), 0) + 1);
            }
            else{
                usedSlots += 1;
            }
        }
        for(Map.Entry<String, Integer> entry : consumableCount.entrySet()){
            int count =  entry.getValue();
            usedSlots += (int) Math.ceil(count / 5.0);
        }
        currentPlayerCarryWeight = totalWeight;
        inventorySlots.setUsedSlots(usedSlots);
    }

    //Method to remove an item from the player object's list
    public void removeItem(Item item) {
        playerInventoryItems.remove(item);
        recalculateWeightAndSlots();
    }

    public Slots getInventorySlots() {
        return inventorySlots;
    }
}

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {

    //The player object's max weight. Set
    private final double maxPlayerCarryWeight = 50.00;
    private double currentPlayerCarryWeight = 0;

    private final List<Item> playerInventoryItems = new ArrayList<Item>();
    private final Slots inventorySlots = new Slots();

    //A constructor for the inventory class, it is called in player.
    public Inventory() {}

    //A getter to get the player's max carry weight attribute.
    public double getMaxPlayerCarryWeight() {return maxPlayerCarryWeight;}

    //A getter to get the player's current weight variable.
    public double getCurrentPlayerCarryWeight() {return currentPlayerCarryWeight;}

    //A getter to get the player object's inventory.
    public List<Item> getPlayerInventoryItems() {return playerInventoryItems;}

    //A getter to access the slots class.
    public Slots getInventorySlots() {return inventorySlots;}


    //Method to add an item to the player object's List
    public void addItem(Item item) {
        playerInventoryItems.add(item);
        recalculateWeightAndSlots();
    }
    //This method is used whenever the player object, adds or removes an item.
    //It calculates and sets the player objects currentWeight and usedSlots, while making it so that consumable objects.
    //Only takes up a slots for every unique or after every 5th instance of the same object.
    public void recalculateWeightAndSlots(){

        double totalWeight = 0.00;
        int usedSlots = 0;

        Map<String, Integer> consumableCount = new HashMap<>();
        for (Item i : playerInventoryItems){
            totalWeight += i.getWeight();

            if(i instanceof Consumable c && c.getIsStackable()) {
                consumableCount.put(c.getName(), consumableCount.getOrDefault(c.getName(), 0) + 1);
            }
            else{
                usedSlots += 1;
            }
        }

        for(Map.Entry<String, Integer> entry : consumableCount.entrySet()){
            int count = entry.getValue();
            usedSlots += (int) Math.ceil(count / 5.0);
        }
        currentPlayerCarryWeight = totalWeight;
        inventorySlots.setUsedSlots(usedSlots);
    }

    //Method to remove an item object from the player's inventory list.
    public void removeItem(Item item) {
        playerInventoryItems.remove(item);
        recalculateWeightAndSlots();
    }
}

package models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final double maxPlayerCarryWeight = 50.00;
    private double currentPlayerCarryWeight = 0;

    private final List<Items> playerInventoryItems = new ArrayList<Items>();
    private Slots inventorySlots = new Slots();

    public Inventory() {}


    public double getMaxPlayerCarryWeight() {
        return maxPlayerCarryWeight;
    }

    public double getCurrentPlayerCarryWeight() {
        return currentPlayerCarryWeight;
    }

    public List<Items> getPlayerInventoryItems() {
        return playerInventoryItems;
    }

    //Method to add an item to the player object's List
    public void addItem(Items item) {
        playerInventoryItems.add(item);
        currentPlayerCarryWeight += item.getWeight();
        inventorySlots.addUsedSlots(+1);
    }

    public void addConsumableItemWithSlots(Items item){
        playerInventoryItems.add(item);
        currentPlayerCarryWeight += item.getWeight();
        inventorySlots.addUsedSlots(+1);
    }
    public void addConsumableItem(Items item) {
        playerInventoryItems.add(item);
        currentPlayerCarryWeight += item.getWeight();
    }
    public void addConsumableItemStack(Items item){
        playerInventoryItems.add(item);
        currentPlayerCarryWeight += item.getWeight();
        inventorySlots.addUsedSlots(+1);
    }


    //Method to remove an item from the player object's list
    public void removeItem(Items item) {
        playerInventoryItems.remove(item);
        currentPlayerCarryWeight -= item.getWeight();
        inventorySlots.deductUsedSlots(-1);
    }

    public Slots getInventorySlots() {
        return inventorySlots;
    }
}

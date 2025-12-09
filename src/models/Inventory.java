package models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final double maxPlayerCarryWeight = 50.00;
    private double currentPlayerCarryWeight = 0;

    private final List<Item> playerInventoryItems = new ArrayList<Item>();
    private Slots inventorySlots = new Slots();

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

    public void addConsumable(Consumable item){
        for (Item i : playerInventoryItems){
            if (i instanceof Consumable c && c.getName().equalsIgnoreCase(item.getName())){
                c.addQuantity(1);
                recalculateWeightAndSlots();
                return;
            }
        }
        playerInventoryItems.add(item);
        recalculateWeightAndSlots();
    }

    public void recalculateWeightAndSlots(){
        double totalWeight = 0;
        int usedSlots = 0;

        for (Item i : playerInventoryItems){
            if(i instanceof Consumable c) {
                totalWeight += c.getWeight() * c.getQuantity();
                usedSlots += c.getRequiredSlots();
            }
            else{
                totalWeight += i.getWeight();
                usedSlots += 1;
            }
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

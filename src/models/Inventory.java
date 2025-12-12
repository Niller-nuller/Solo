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
    public void recalculateWeightAndSlots(){
        //It starts with creating to local attribute, for totalWeight and usedSlots.
        //It uses double and int variables, witch corresponds with weight and slots variables.
        double totalWeight = 0.00;
        int usedSlots = 0;
        //Here we create a HashMap, to store specifically consumables.
        Map<String, Integer> consumableCount = new HashMap<>();
        //Here we go through the player objects inventory List.
        for (Item i : playerInventoryItems){
            //We add the item objects weight from the player objects List no matter what.
            totalWeight += i.getWeight();
            //We then use polymorphism, to see if the item object, is a consumable.
            //We can do this because we abstracted Item, and extends our consumable class to it.
            if(i instanceof Consumable c && c.getIsStackable()) {
                //If our item object is from the consumable class and has the attribut "isStackable = true", we then put it into our has map.
                consumableCount.put(c.getName(), consumableCount.getOrDefault(c.getName(), 0) + 1);
            }
            else{
                //If our Item object was not a consumable, we add to our used Slot attribute.
                usedSlots += 1;
            }
        }
        //Once we are through our playerInventoryItems List, we go through our HashMap.
        for(Map.Entry<String, Integer> entry : consumableCount.entrySet()){
            //We then create a new local attribute with the variable int.
            //We count the amount of times and object with the same name appers.
            int count = entry.getValue();
            //Then we add it to our usedSlots but, we use Math.ceil to "count" and it is only everytime a certain name appers.
            //The first instance of a stackable consumable will take up a usedSlot, but the next 4 of the same consumable object.
            //Will not take up any usedSlots, but as already been added to the player objects weight.
            usedSlots += (int) Math.ceil(count / 5.0);
        }
        //We end the method, with setting our player objects currentWeight and usedSlots attributes.
        currentPlayerCarryWeight = totalWeight;
        inventorySlots.setUsedSlots(usedSlots);
    }

    //Method to remove an item object from the player's inventory list.
    public void removeItem(Item item) {
        playerInventoryItems.remove(item);
        recalculateWeightAndSlots();
    }
}

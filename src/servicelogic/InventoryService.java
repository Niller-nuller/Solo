package servicelogic;

import exceptions.*;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final List<Item> worldItems = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    public InventoryService() {defaultItems(); defaultPlayer();}
    //------------------------------------------------Player Management-----------------------------------------------------
    public Player createPlayer(String name) throws PlayerNameProblem { //To be called in the client class.
        if (getPlayerByName(name) != null) { //Calls the method getPlayerByName to either get a null or an object.
            throw new PlayerNameProblem(name + " already exist");
        } else { //Creates the new player object.
            Player p = new Player(name);
            players.add(p);
            return p;
        }
    }

    public Player loginPlayer(String name) throws PlayerNameProblem{ //This method is to be called from the client class.
        if (getPlayerByName(name) == null) {
            throw new PlayerNameProblem(name + " doesn't exist");
        }
        else{
            return getPlayerByName(name);
        }
    }

    private Player getPlayerByName(String name){
        for (Player p : players){
            if (p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }
    //This layer of code in only to used to either create or login the player.
//------------------------------------------------Item Management-------------------------------------------------------
    public void addItem(Player p, String itemName) throws ItemNotFound,MaxWeightReached,MaxSlotsReached{
        Item findItem = getWorldItemByName(itemName);
        if (findItem == null){
            throw new ItemNotFound(itemName);
        }
        checkInventory(p, findItem);

        p.getInventory().addItem(findItem);
    }

    public void removeItem(Player p, String itemName) throws ItemNotFound{ // This method is to be called from the client class.
        Item findItem = getWorldItemByName(itemName);
        if (findItem == null) {
            throw new ItemNotFound(itemName + " not found");
        }
        else{
            p.getInventory().removeItem(findItem);
        }
    }

    private Item getWorldItemByName(String name){
        for (Item i : worldItems){
            if(i.getName().equalsIgnoreCase(name)){
                return i;
            }
        }return null;
    }

    public void checkInventory(Player p, Item item) throws MaxWeightReached, MaxSlotsReached{
        int availablePlayerSlots = p.getInventory().getInventorySlots().getCurrentSlots();
        int usedPlayerSlots = p.getInventory().getInventorySlots().getUsedSlots();
        double usedPlayerWeight = p.getInventory().getCurrentPlayerCarryWeight() + item.getWeight();
        double maxPlayerWeight = p.getInventory().getMaxPlayerCarryWeight();

        if(usedPlayerSlots >= availablePlayerSlots){
            throw new MaxSlotsReached(usedPlayerSlots + " out of " + availablePlayerSlots + " slots filled");
        }
        else if(usedPlayerWeight >= maxPlayerWeight){
            throw new MaxWeightReached("Weight limit reached.");
        }
    }
    // This layer is for adding and removing items from the player. It assumes a player is logged in.
//---------------------------------------Slots Management---------------------------------------------------------------
    public void increasePlayerMaxSlots(Player p){
        p.getInventory().getInventorySlots().setCurrentMaxSlots();
    }

    //----------------------------------------------World Items Initialization----------------------------------------------
    public void defaultItems() {
        worldItems.add(new Weapon("Short Sword", 2.5, 10, MainHand));
        worldItems.add(new Weapon("Short Axe", 2.5, 7, OffHand));
        worldItems.add(new Armor("Helmet", 2, 2, Helmet));
        worldItems.add(new Armor("Chestplate", 8, 6, Chestpiece));
        worldItems.add(new Consumable("Health Potion", 0.2, true, Drinkable));
        worldItems.add(new Consumable("Arrow", 0.1, true, Ammo));
        worldItems.add(new Consumable("Rock", 0.4, true, Throwable));
        worldItems.add(new Weapon("Test Sword", 49.9999, 67, MainHand));
        worldItems.add(new Weapon("Great Sword", 5, 20, TwoHand));
    }
    public void defaultPlayer(){
        players.add(new Player("Test"));
    }
    //------------------------------------------------Displays--------------------------------------------------------------
    public void displayWorldItems(){
        System.out.println("--- Available items ---");
        for (Item i : worldItems){
            System.out.println("- " + i.getName() + " (" + i.getWeight() + " kg)");
        }
    }
    public void displayPlayers(){
        System.out.println("--- Players ---");
        for (Player p : players){
            System.out.println("- " + p.getName());
        }
    }
    public void displayPlayerInventory(Player p){
        if(p.getInventory().getPlayerInventoryItems().isEmpty()){
            System.out.println("Your inventory is empty");
        }
        else{
            System.out.println("-----------------");
            for (Item i : p.getInventory().getPlayerInventoryItems()){
                System.out.println("- " + i.getName() + " (" + i.getWeight() + " kg)");
            }
            System.out.println("---------------");
        }
    }
}


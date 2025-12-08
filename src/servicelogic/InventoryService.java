package servicelogic;

import exceptions.*;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private final List<Items> worldItems = new ArrayList<>();
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
    public void addItem(Player p, String itemName){ // This method is to be called from the client class.
        Items findItem = getWorldItemByName(itemName);
        if (findItem == null) {
            System.out.println("Item does not exist");
        } else if (checkInventory(p,findItem)){
            p.getInventory().addItem(findItem);
        }
    }

    public void removeItem(Player p, String itemName){ // This method is to be called from the client class.
        Items findItem = getWorldItemByName(itemName);
        if (findItem == null) {
            System.out.println("Item does not exist");
        }
        else{
            p.getInventory().removeItem(findItem);
            System.out.println(itemName + " has been removed from your inventory");
        }
    }
    private void addConsumableItem(Player p, String itemName){
        Items findItem = getWorldItemByName(itemName);
        if (findItem == null) {

        }

    }
    private void addMultipleConsumableItems(Player p, String itemName, int amount){
        Items findItem = getWorldItemByName(itemName);
        if(findItem == null){
        }
        else{
            for(int i = 0; i < amount; i++){
                p.getInventory().addConsumableItem(findItem);
            }
        }

    }

    private Items getWorldItemByName(String name){
        for (Items i : worldItems){
            if(i.getName().equalsIgnoreCase(name)){
                return i;
            }
        }return null;
    }
//    public boolean checkInventoryParamters(Player p){
//
//    }
//    public boolean checkWeight(Player p){
//        double playerCurrentCarryWeight = p.getInventory().getCurrentPlayerCarryWeight();
//        double maxPlayerCarryWeight = p.getInventory().getMaxPlayerCarryWeight();
//    }

    public boolean checkInventory(Player p, Items item) throws MaxWeightReached, MaxSlotsReached{
        int availableSlots = p.getInventory().getInventorySlots().getCurrentSlots();
        int usedSlots = p.getInventory().getInventorySlots().getUsedSlots();
        double currentWeight = p.getInventory().getCurrentPlayerCarryWeight() + item.getWeight();
        double maxWeight = p.getInventory().getMaxPlayerCarryWeight();

        if(usedSlots >= availableSlots){
            throw new MaxSlotsReached(usedSlots + " out of " + availableSlots + " slots filled");
        }
        else if(currentWeight >= maxWeight){
            throw new MaxWeightReached("Weight limit reached.");
        }
        else {
            return true;
        }
    }
    // This layer is for adding and removing items from the player. It assumes a player is logged in.
//---------------------------------------Slots Management---------------------------------------------------------------
    public void increasePlayerMaxSlots(Player p){
        p.getInventory().getInventorySlots().setCurrentMaxSlots();
    }

    //----------------------------------------------World Items Initialization----------------------------------------------
    public void defaultItems(){
        worldItems.add(new Weapons("Short Sword",2.5,10));
        worldItems.add(new Weapons("Short Axe",2.5,10));
        worldItems.add(new Armors( "Helmet",2,2));
        worldItems.add(new Armors( "Chestplate",8,6));
        worldItems.add(new Consumables("Health Potion",0.2));
        worldItems.add(new Weapons("Test Sword",50,10));
    }
    public void defaultPlayer(){
        players.add(new Player("Test"));
    }
    //------------------------------------------------Displays--------------------------------------------------------------
    public void displayWorldItems(){
        System.out.println("--- Available items ---");
        for (Items i : worldItems){
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
            for (Items i : p.getInventory().getPlayerInventoryItems()){
                System.out.println("- " + i.getName() + " (" + i.getWeight() + " kg)");
            }
            System.out.println("---------------");
        }
    }
}


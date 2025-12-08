package Service_Logic;

import Models.*;
import Models.Player;

import java.util.ArrayList;
import java.util.List;

public class Inventory_Service {

    private final List<Items> worldItems = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    public Inventory_Service() {defaultItems();}
//Tester
//------------------------------------------------Player Management-----------------------------------------------------
    public Player createPlayer(String name){ //To be called in the client class.
        if (getPlayerByName(name) != null) { //Calls the method getPlayerByName to either get a null or an object.
            System.out.println("Player already exists");
            System.out.println("Please enter a different name");
            return null;
        } else { //Creates the new player object.
            Player p = new Player(name);
            players.add(p);
            return p;
        }
    }

    public Player loginPlayer(String name){ //This method is to be called from the client class.
        if (getPlayerByName(name) == null) {
            System.out.println("Player by the name " + name + " does not exist");
            System.out.println("Please enter a different name");
            return null;
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

    private Items getWorldItemByName(String name){
        for (Items i : worldItems){
            if(i.getName().equalsIgnoreCase(name)){
                return i;
            }
        }return null;
    }

    public boolean checkInventory(Player p, Items item){
        int currentAvailableSlots = p.getInventory().getInventorySlots().getCurrentSlots();
        int currentFilledSlots = p.getInventory().getInventorySlots().getUsedSlots();
        double currentWeight = p.getInventory().getCurrentPlayerCarryWeight() + item.getWeight();
        double maxWeight = p.getInventory().getMaxPlayerCarryWeight();

        if(currentFilledSlots > currentAvailableSlots){
            System.out.println("You don't have enough space to do this");
            return false;
        }
        else if(currentWeight > maxWeight){
            System.out.println("You can't carry anymore, it's all to heavy");
            return false;
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
        worldItems.add(new Consumables("Health Potion",0.2,1));
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
            for (Items i : p.getInventory().getPlayerInventoryItems()){
                System.out.println("- " + i.getName() + " (" + i.getWeight() + " kg)");
            }
        }
    }
}

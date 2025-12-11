package models;

public class Player {
    private final String name;
    private final Inventory inventory;

    public Player(String name){
        this.name = name;
        this.inventory = new Inventory();
    }
    public String getName(){
        return this.name;
    }
    public Inventory getInventory(){
        return this.inventory;
    }

}

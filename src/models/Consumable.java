package models;

public class Consumable extends Item {

    private final boolean stackable;
    private int quantity;
    public Consumable(String name, double weight, boolean stackable) {
        super(name,  weight);
        this.stackable = stackable;
        this.quantity = 1;
    }
    public boolean isStackable() {
        return stackable;
    }
    public int getQuantity() {
        return quantity;
    }
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
    public int getRequiredSlots(){
        if(!stackable){
            return 1;
        }
        return (int) Math.ceil((double) quantity / 5.0);
    }
}

package models;

public class Consumables extends Items{

    private boolean stackable;

    public Consumables(String name, double weight) {
        super(name,  weight);
        this.stackable = stackable;
    }
}

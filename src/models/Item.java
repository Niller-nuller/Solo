package models;

public abstract class Item {
    private final String name;
    private final double weight;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
    public String getName(){
        return name;
    }
    public double getWeight(){
        return weight;
    }
}

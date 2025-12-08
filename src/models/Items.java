package models;

public abstract class Items {
    protected String name;
    protected double weight;

    public Items(String name, double weight) {
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

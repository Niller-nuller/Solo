package models;

public class Armors extends Items {

    private int resistance;

    public Armors(String name, double weight, int resistance){
        super(name, weight);
        this.resistance = resistance;
    }
}

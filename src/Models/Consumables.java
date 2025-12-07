package Models;

public class Consumables extends Items{

    private int stacks;

    public Consumables(String name, double weight, int stacks) {
        super(name,  weight);
        this.stacks = stacks;
    }
}

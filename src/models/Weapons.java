package models;

public class Weapons extends Items{

    private int damage;

    public Weapons(String name, double weight, int damage){
        super(name,weight);
        this.damage = damage;
    }

}

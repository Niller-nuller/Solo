package models;

import models.enums.ArmorType;

public class Armor extends Item {

    private final int resistance;
    private final ArmorType armorType;

    public Armor(String name, double weight, int resistance,ArmorType armorType){
        super(name, weight);
        this.resistance = resistance;
        this.armorType = armorType;
    }
    //--This classes, setters and getters.---------------------------------------------------------------------------
    public int getResistance() {
        return resistance;
    }
    public ArmorType getArmorType() {
        return armorType;
    }
}

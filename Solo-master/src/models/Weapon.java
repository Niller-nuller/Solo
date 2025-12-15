package models;

import models.enums.WeaponType;

public class Weapon extends Item {


    private final int damage;
    private final WeaponType weaponType;

    public Weapon(String name, double weight, int damage,WeaponType weaponType){
        super(name,weight);
        this.damage = damage;
        this.weaponType = weaponType;
    }
    //--This classes, setters and getters.---------------------------------------------------------------------------
    public int getDamage(){
        return damage;
    }
    public WeaponType getWeaponType(){
        return weaponType;
    }
}

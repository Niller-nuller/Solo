package models;

import models.enums.ConsumableType;

public class Consumable extends Item {

    private final boolean stackable;
    private final ConsumableType consumableType;

    public Consumable(String name, double weight, boolean stackable, ConsumableType consumableType) {
        super(name,  weight);
        this.stackable = stackable;
        this.consumableType = consumableType;
    }
    public boolean isStackable() {
        return stackable;
    }
    public ConsumableType getConsumableType() {
        return consumableType;
    }
}

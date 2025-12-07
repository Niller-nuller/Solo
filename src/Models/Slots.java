package Models;

public class Slots {

    private final int maxSlots = 192;
    private int currentSlots = 32;
    private int usedSlots = 0;


    public Slots(){}

    public int getCurrentSlots(){
        return this.currentSlots;
    }

    public int getUsedSlots(){
        return this.usedSlots;
    }

    public void addCurrentSlots(int currentSlots){
        this.currentSlots += currentSlots;
    }
    public void deductCurrentSlots(int currentSlots){
        this.currentSlots -= currentSlots;
    }

    public void setCurrentMaxSlots(){
        if(this.currentSlots >= maxSlots){
            System.out.println("You have unlocked the max amount of inventory space");
        }
        else{
            this.currentSlots += currentSlots;
        }
    }

}

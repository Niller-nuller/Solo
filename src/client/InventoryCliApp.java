package client;

import exceptions.ItemNotFound;
import exceptions.MaxSlotsReached;
import exceptions.MaxWeightReached;
import exceptions.PlayerNameProblem;
import models.*;
import servicelogic.InventoryService;

import java.util.Scanner;

public class InventoryCliApp {
    private final InventoryService inv = new InventoryService();
    private Player player;

    static void main(String[] args) {
        new InventoryCliApp().mainMenu();
    }


    //Main menu---------------------------------------------------------------------------------------------
    private void mainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        handlePlayerCreating(scanner);
                    }

                    case "2" -> {
                        handleLogInPlayer(scanner);
                    }

                    case "3" -> {
                        running = false;
                        System.out.println("Shutting down");
                    }

                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("---Main menu---");
        System.out.println("1. Create new player");
        System.out.println("2. Log in");
        System.out.println("3. Exit");
        System.out.println("---------------");
        System.out.print("Choice: ");
    }

    private void handlePlayerCreating(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        try {
            player = inv.createPlayer(username);
            System.out.println("Player " + username + " has been created");
            gameMenu(scanner);
        } catch (PlayerNameProblem e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLogInPlayer(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        try {
            player = inv.loginPlayer(username);
            gameMenu(scanner);
        } catch (PlayerNameProblem e) {
            System.out.println(e.getMessage());
        }
    }

    //Game Menu-----------------------------------------------------------------------------------
    public void gameMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            printGameMenu();
            String choice;
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> inventoryMenu(scanner);
                case "2" -> worldItemMenu(scanner);
                case "3" -> {
                    running = false;
                    System.out.println("Returning");
                }
            }
        }
    }

    private void printGameMenu() {
        System.out.println("---Game Menu---");
        System.out.println("1. See inventory");
        System.out.println("2. See all items");
        System.out.println("3. Back");
        System.out.println("---------------");
        System.out.print("Choice: ");
    }


    //WorldItem menu---------------------------------------------------------------
    public void worldItemMenu(Scanner scanner) {
        boolean running = true;
        String choice;
        while (running) {
            printWorldItemMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> handleDisplayWorldItems();
                case "2" -> handleFindWorldItems(scanner);
                case "3" -> {
                    running = false;
                    System.out.println("Returning");
                }
            }

        }
    }

    public void printWorldItemMenu() {
        System.out.println("---World Item Menu---");
        System.out.println("1. See all world items");
        System.out.println("2. Find items");
        System.out.println("3. Back");
        System.out.println("---------------");
        System.out.print("Choice: ");
    }

    private void handleDisplayWorldItems() {
        System.out.println("Available items-");
        for (Item i : inv.getWorldItems()) {
            if (i instanceof Weapon) {
                System.out.println("- " + i.getName() + " -  Damage: " + ((Weapon) i).getDamage() + " - " + ((Weapon) i).getWeaponType().toString() + " (" + i.getWeight() + " kg)");
            } else if (i instanceof Armor) {
                System.out.println("- " + i.getName() + " - Resistance: " + ((Armor) i).getResistance() + " - " + ((Armor) i).getArmorType().toString() + " (" + i.getWeight() + " kg)");
            } else {
                System.out.println("- " + i.getName() + " - " + ((Consumable) i).getConsumableType().toString() + " (" + i.getWeight() + " kg)");
            }
        }
    }

    private void handleFindWorldItems(Scanner scanner) {
        System.out.println("---------------");
        System.out.println("Either write the name of an item to find, or an attribute(armor, weapon, consumable) of an item");
        System.out.println("For attributes write an @ before the attribute");
        System.out.println("---------------");
        System.out.print("Choice: ");
        String choice;
        choice = scanner.nextLine();
        try {
            inv.findItems(choice);
            for (Item i : inv.getSpecificWorldItemByName()) {
                if (i instanceof Weapon) {
                    System.out.println("- " + i.getName() + " -  Damage: " + ((Weapon) i).getDamage() + " - " + ((Weapon) i).getWeaponType().toString() + " (" + i.getWeight() + " kg)");
                } else if (i instanceof Armor) {
                    System.out.println("- " + i.getName() + " - Resistance: " + ((Armor) i).getResistance() + " - " + ((Armor) i).getArmorType().toString() + " (" + i.getWeight() + " kg)");
                } else {
                    System.out.println("- " + i.getName() + " - " + ((Consumable) i).getConsumableType().toString() + " (" + i.getWeight() + " kg)");
                }
            }
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    //Inventory Menu-----------------------------------------------------------------
    public void inventoryMenu(Scanner scanner) {
        boolean running = true;
        String choice;
        while (running) {
            printInventory();
            System.out.println("Slots used: " + player.getInventory().getInventorySlots().getUsedSlots() + "/" + player.getInventory().getInventorySlots().getCurrentSlots());
            System.out.println("Weight used: " + player.getInventory().getCurrentPlayerCarryWeight() + "/" + player.getInventory().getMaxPlayerCarryWeight() + "Kg");
            printInventoryMenu();

            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleAddItem(scanner);
                case "2" -> handleRemoveItem(scanner);
                case "3" -> sortingMenu(scanner);
                case "4" -> {
                    running = false;
                    System.out.println("Returning");
                }
            }
        }
    }

    public void printInventoryMenu() {
        System.out.println("---Inventory Menu---");
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. Sort items");
        System.out.println("4. Back");
        System.out.println("-------------------");
        System.out.print("Choice: ");
    }

    public void handleAddItem(Scanner scanner) {
        System.out.print("Item to add: ");
        String choice;
        choice = scanner.nextLine();
        try {
            inv.addItem(player, choice);
            System.out.println(choice + " has been added to your inventory");
        } catch (MaxWeightReached e) {
            System.out.println(e.getMessage());
        } catch (MaxSlotsReached e) {
            System.out.println(e.getMessage());
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleRemoveItem(Scanner scanner) {
        System.out.print("Item to remove: ");
        String choice;
        choice = scanner.nextLine();
        try {
            inv.removeItem(player, choice);
            System.out.println(choice + " has been removed from your inventory");
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    public void printInventory() {
        if (player.getInventory().getPlayerInventoryItems().isEmpty()) {
            System.out.println("Your inventory is empty");
        } else {
            System.out.println("-----------------");
            for (Item i : player.getInventory().getPlayerInventoryItems()) {
                if (i instanceof Weapon) {
                    System.out.println("- " + i.getName() + " - Damage: " + ((Weapon) i).getDamage() + " - " + ((Weapon) i).getWeaponType().toString() + " (" + i.getWeight() + " kg)");
                } else if (i instanceof Armor) {
                    System.out.println("- " + i.getName() + " - Resistance: " + ((Armor) i).getResistance() + " - " + ((Armor) i).getArmorType().toString() + " (" + i.getWeight() + " kg)");
                } else {
                    System.out.println("- " + i.getName() + " - " + ((Consumable) i).getConsumableType().toString() + " (" + i.getWeight() + " kg)");
                }
            }
        }
        System.out.println("-----------------");
    }


    //Sorting menu--------------------------------
    public void printSortingMenu() {
        System.out.println("---Sorting Menu---");
        System.out.println("1. Sort by weight");
        System.out.println("2. Sort by type");
        System.out.println("3. Sort by damage");
        System.out.println("4. Sort by resistance");
        System.out.println("5. Sort by name");
        System.out.println("6. Back");
        System.out.println("-------------------");
        System.out.print("Choice: ");
    }

    public void sortingMenu(Scanner scanner) {
        boolean running = true;
        String choice;
        while (running) {
            printSortingMenu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" -> inv.sortItems(player, "weight");
                case "2" -> inv.sortItems(player, "type");
                case "3" -> inv.sortItems(player, "damage");
                case "4" -> inv.sortItems(player, "resistance");
                case "5" -> inv.sortItems(player, "name");
                case "6" -> {
                    running = false;
                    System.out.println("Returning");
                }
            }
        }
    }
}




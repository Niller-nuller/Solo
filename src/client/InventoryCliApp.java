package client;

import exceptions.ItemNotFound;
import exceptions.MaxSlotsReached;
import exceptions.MaxWeightReached;
import exceptions.PlayerNameProblem;
import models.Player;
import servicelogic.InventoryService;

import java.util.Scanner;

public class InventoryCliApp {
    private final InventoryService inv = new InventoryService();
    private Player player;

    void main(String[] args) {
        //sql stuff
        mainMenu();
    }


    //Main menu---------------------------------------------------------------------------------------------
    private void mainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                String choice;
                choice = scanner.nextLine();

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
        System.out.println("------");
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
                case "2" -> handleDisplayWorldItems();
                case "3" -> {
                    running = false;
                    System.out.println("Returning");
                }
            }
        }
    }

    private static void printGameMenu() {
        System.out.println("---Game Menu---");
        System.out.println("1. See inventory");
        System.out.println("2. See all items");
        System.out.println("3. Exit");
        System.out.println("------");
        System.out.print("Choice: ");
    }

    private void handleDisplayWorldItems() {
        inv.displayWorldItems();
    }

    //Inventory Menu-----------------------------------------------------------------
    public void inventoryMenu(Scanner scanner) {
        boolean running = true;
        String choice;
        while (running) {
            inv.displayPlayerInventory(player);
            System.out.println(player.getInventory().getInventorySlots().getUsedSlots());
            printInventoryMenu();

            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleAddItem(scanner);
                case "2" -> handleRemoveItem(scanner);
                case "3" -> {
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
        System.out.println("3. Exit");
        System.out.println("------");
        System.out.print("Choice: ");
    }
    public void handleAddItem(Scanner scanner){
        System.out.print("Item to add: ");
        String choice;
        choice = scanner.nextLine();
        try {
            inv.addItem(player, choice);
        } catch (MaxWeightReached e){
            System.out.println(e.getMessage());
        } catch (MaxSlotsReached e){
            System.out.println(e.getMessage());
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }
    }
    public void handleRemoveItem(Scanner scanner){
        System.out.print("Item to remove: ");
        String choice;
        choice = scanner.nextLine();
        try {
            inv.removeItem(player, choice);
            System.out.println(choice + " has been removed from your inventory");
        }catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }
    }
}


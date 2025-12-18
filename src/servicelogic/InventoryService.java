package servicelogic;

import exceptions.*;
import models.*;
import sqldatabase.ItemRepository;
import sqldatabase.PlayerRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryService {
    //Different lists, to keep track of objects data.
    private final List<Item> worldItems = new ArrayList<>(); //Functions as our default List of all items in the legends of code craft.
    private final List<Player> players = new ArrayList<>(); //Functions
    private final List<Item> specificWorldItems = new ArrayList<>();

    private final ItemRepository itemRepository = new ItemRepository();
    private final PlayerRepository playerRepository = new PlayerRepository();

    public InventoryService() {

    }



    public void initializeGame() throws SQLException {
            worldItems.addAll(itemRepository.initializeWorldItems());
            players.addAll(playerRepository.initializePlayers());
    }

    //------------------------------------------------Player Management-----------------------------------------------------

    //Used to create a Player object, while making sure the objects name us unique, and then adds it to the players list and then returning it.
    public Player createPlayer(String name) throws PlayerNameProblem {
        if (getPlayerByName(name) != null) {
            throw new PlayerNameProblem(name + " already exist");
        } else {
            Player p = new Player(name);
            players.add(p);
            return p;
        }
    }
    //Used to return an already existing player object.
    public Player loginPlayer(String name) throws PlayerNameProblem {
        if (getPlayerByName(name) == null) {
            throw new PlayerNameProblem(name + " doesn't exist");
        } else {
            return getPlayerByName(name);
        }
    }
    //Used by loginPlayer to check the playerList, for player objects with a corresponding name.
    private Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public void savePlayer(Player player) throws SQLException {
            playerRepository.savePlayerAndInventory(player);
    }

    public void deletePlayer(String username) throws PlayerNameProblem, SQLException {
        Player p = getPlayerByName(username);
        if(p == null) {
            throw new PlayerNameProblem(username + " doesn't exist");
        }
            playerRepository.deletePlayerAndInventory(username);
            players.remove(p);
    }
    //This layer of code in only to used to either create or login the player.
//------------------------------------------------Item Management-------------------------------------------------------
    public void addItem(Player p, String itemName) throws ItemNotFound, MaxWeightReached, MaxSlotsReached {
        Item findItem = getWorldItemByName(itemName);
        if (findItem == null) {
            throw new ItemNotFound(itemName + " not found");
        }
        checkInventory(p, findItem);

        p.getInventory().addItem(findItem);
    }

    public void removeItem(Player p, String itemName) throws ItemNotFound { // This method is to be called from the client class.
        Item findItem = getWorldItemByName(itemName);
        if (findItem == null) {
            throw new ItemNotFound(itemName + " not found");
        } else {
            p.getInventory().removeItem(findItem);
        }
    }
    //this one finds item objects in the world items list and returns the item object.
    private Item getWorldItemByName(String name) {
        for (Item i : worldItems) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }
    //this method checks the different atributes that make up the inventory restirctions.
    private void checkInventory(Player p, Item item) throws MaxWeightReached, MaxSlotsReached {
        int availablePlayerSlots = p.getInventory().getInventorySlots().getCurrentSlots();
        int usedPlayerSlots = p.getInventory().getInventorySlots().getUsedSlots();
        double usedPlayerWeight = p.getInventory().getCurrentPlayerCarryWeight() + item.getWeight();
        double maxPlayerWeight = p.getInventory().getMaxPlayerCarryWeight();

        if (usedPlayerSlots >= availablePlayerSlots) {
            throw new MaxSlotsReached("Slot limit reached.");
        } else if (usedPlayerWeight >= maxPlayerWeight) {
            throw new MaxWeightReached("Weight limit reached.");
        }
    }

    // This layer is for adding and removing items from the player. It assumes a player is logged in.
//---------------------------------------Slots Management---------------------------------------------------------------
    public void increasePlayerMaxSlots(Player p) {
        p.getInventory().getInventorySlots().addMoreSlots();
    }

    //------------------------------------------------Displays--------------------------------------------------------------
    public List<Item> getWorldItems() {
        return worldItems;
    }
    public List<Item> getSpecificWorldItemByName() {
        return specificWorldItems;
    }

    //------------------------------------------------Sorting and finding--------------------------------------------------
    public void sortItems(Player p, String criteria) {
        List<Item> items = p.getInventory().getPlayerInventoryItems();

        switch (criteria.toLowerCase()) {
            case "name":
                items.sort(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            case "weight":
                items.sort(Comparator.comparing(Item::getWeight));
                break;
            case "type":
                items.sort(Comparator.comparing(item -> item.getClass().getSimpleName(), String.CASE_INSENSITIVE_ORDER));
                break;
            case "damage":
                items.sort((i1, i2) -> {
                    if (i1 instanceof Weapon w1 && i2 instanceof Weapon w2) {
                        return Integer.compare(w2.getDamage(), w1.getDamage());
                    } else if (i1 instanceof Weapon) {
                        return -1;
                    } else if (i2 instanceof Weapon) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                break;
            case "resistance":
                items.sort((i1, i2) -> {
                    if (i1 instanceof Armor w1 && i2 instanceof Armor w2) {
                        return Integer.compare(w2.getResistance(), w1.getResistance());
                    } else if (i1 instanceof Armor) {
                        return -1;
                    } else if (i2 instanceof Armor) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
            break;
        }
    }
    public void findItems(String criteria) throws ItemNotFound {
        criteria = criteria.toLowerCase();
        specificWorldItems.clear();
        for (Item i : worldItems) {
            if (criteria.isEmpty()){
                throw new ItemNotFound("Write something");
            } else {
                if (criteria.contains("@")) {
                    switch (criteria) {
                        case "@weapon" -> {
                            if (i instanceof Weapon) {
                                specificWorldItems.add(i);
                            }
                        }
                        case "@armor" -> {
                            if (i instanceof Armor) {
                                specificWorldItems.add(i);
                            }
                        }
                        case "@consumable" -> {
                            if (i instanceof Consumable) {
                                specificWorldItems.add(i);
                            }
                        }
                    }
                } else if (i.getName().toLowerCase().contains(criteria)) {
                    specificWorldItems.add(i);
                }
            }
        }
        if (specificWorldItems.isEmpty()){
            throw new ItemNotFound("No items found");
        }
    }
}

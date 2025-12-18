package sqldatabase;

import models.Item;
import models.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerRepository {
    private final ItemRepository itemRepository = new ItemRepository();

    public PlayerRepository() {}

//-----------------------------------Player initialising-----------------------------------------

    //This method is used load in information about our player table in our sql server.
    public List<Player> initializePlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT id, name FROM players";

        try(Connection conn = DBConnect.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(); {

                while (rs.next()) {
                    int playerId = rs.getInt("id");
                    String name = rs.getString("name");
                    Player player = new Player(name);
                    initializePlayerInventory(conn, player, playerId);
                    players.add(player);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Could not fetch players from database");
        }return players;
    }
    //This method is used to load in the correspondent inventory for the player object.
    private void initializePlayerInventory(Connection conn, Player p, int playerId) throws SQLException {
        String sql = "SELECT item_id, quantity FROM playerInventory WHERE player_id = ? ORDER BY slot_index";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, playerId);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    int itemId = rs.getInt("item_id");
                    int quantity = rs.getInt("quantity");

                    Item item = itemRepository.findItemById(itemId);
                    if(item != null){
                        for(int i = 0; i< quantity; i++){
                            p.getInventory().addItem(item);
                        }
                    }
                }
            }
        }
    }
    //----------------------------Player and inventory saving-----------------------------------------------------
    //This method is used to save the player and the players inventory, to the sql server.
    public void savePlayerAndInventory(Player p) throws SQLException {
        try(Connection conn = DBConnect.getConnection()){
            conn.setAutoCommit(false);

            int playerId = getPlayerId(conn, p.getName());
            clearPlayerInventory(conn, playerId);
            saveToPlayerInventory(conn, playerId, p.getInventory().getPlayerInventoryItems());
            conn.commit();
        } catch (SQLException e){
            throw new RuntimeException("Save failed");
        }
    }
    //This method is used to clear the players inventory when we save to sql so we can save over it.
    private int getPlayerId(Connection conn, String playerName) throws SQLException {
        String selectSQL = "SELECT id FROM players WHERE name = ?";
        try(PreparedStatement stmt = conn.prepareStatement(selectSQL)){
            stmt.setString(1, playerName);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id");
                }
            }
        }

        String insertSQL = "INSERT INTO players (name) VALUES (?)";
        try(PreparedStatement stmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, playerName);
            stmt.executeUpdate();
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()){
                    return rs.getInt(1);
                }
                else {
                    throw new SQLException("Could not generate new player");
                }
            }
        }
    }
    //This method is for clearing the players inventory from sql when we delete a player.
    private void clearPlayerInventory(Connection conn, int playerId) throws SQLException {
        String deleteSQL = "DELETE FROM playerinventory WHERE player_id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(deleteSQL)){
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }
    //This method is used to save the inventory.
    private void saveToPlayerInventory(Connection conn, int playerId,List<Item> items) throws SQLException {
        String insertSQL = "INSERT INTO playerinventory (player_id, item_id, quantity, slot_index) VALUES (?, ?, ?, ?)";

        Map<String, Integer> count = new HashMap<>();
        for(Item item : items){
            count.merge(item.getName(), 1, Integer::sum);
        }
        try(PreparedStatement stmt = conn.prepareStatement(insertSQL)){
            int slotIndex = 0;
            for(Map.Entry<String, Integer> entry : count.entrySet()){
                String itemName = entry.getKey();
                int quantity = entry.getValue();

                int itemId = itemRepository.findItemByName(conn, itemName);
                if(itemId != -1){
                    stmt.setInt(1, playerId);
                    stmt.setInt(2, itemId);
                    stmt.setInt(3, quantity);
                    stmt.setInt(4, slotIndex++);
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        }
    }
    //This method is used to delete the players inventory when he deletes the player.
    public void deletePlayerAndInventory(String playerName) throws SQLException {
        try(Connection conn = DBConnect.getConnection()){
            conn.setAutoCommit(false);

            String deleteInventory = "DELETE FROM playerinventory WHERE player_id = (SELECT id FROM players WHERE name = ?)";
            try(PreparedStatement deleteStmt = conn.prepareStatement(deleteInventory)){
                deleteStmt.setString(1, playerName);
                int rowsAffected = deleteStmt.executeUpdate();
                if(rowsAffected == 0){
                    throw new SQLException("Could not delete player inventory");
                }
            }
            String deletePlayer = "DELETE FROM players WHERE name = ?";
            try(PreparedStatement deletePlayerStmt = conn.prepareStatement(deletePlayer)){
                deletePlayerStmt.setString(1, playerName);
                int rowsAffected = deletePlayerStmt.executeUpdate();
                if(rowsAffected == 0){
                    throw new SQLException("Could not delete the player");
                }
            } conn.commit();
        }
    }
//----------------------------------------------------------------------------------------------------------------------
}
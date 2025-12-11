package sqldatabase;

import models.Item;
import models.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    private final ItemRepository itemRepository = new ItemRepository();

    public PlayerRepository() {}

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
    private void initializePlayerInventory(Connection conn, Player p, int playerId) throws SQLException {
        String sql = "SELECT item_id, quantity FROM FROM PlayerInventory WHERE player_id = ? ORDER BY slot_index";

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
    private void clearPlayerInventory(Connection conn, int playerId) throws SQLException {
        String deleteSQL = "DELETE FROM PlayerInventory WHERE player_id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(deleteSQL)){
            stmt.setInt(1, playerId);
            stmt.executeUpdate();
        }
    }
    private void saveToPlayerInventory(Connection conn, int PlayerId, List<Item> items ) throws SQLException {
        String insertSQL = "INSERT INTO PlayerInventory (player_id, item_id, quantity, slot_index) VALUES (?, ?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(insertSQL)){
            int slotIndex = 0;
            for (Item item : items) {
                int itemId = itemRepository.findItemByName(conn, item.getName());
                if(itemId == -1){
                    stmt.setInt(1, PlayerId);
                    stmt.setInt(2, itemId);
                    stmt.setInt(3, 1);
                    stmt.setInt(4, slotIndex++);
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        }
    }


}

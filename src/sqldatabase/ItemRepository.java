package sqldatabase;

import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import models.enums.ArmorType;
import models.enums.ConsumableType;
import models.enums.WeaponType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class  ItemRepository {

    public List<Item> initializeWorldItems() throws SQLException {
        List<Item> items = new ArrayList<Item>();
        String sql = "SELECT * FROM Items";

        try(Connection conn = DBConnect.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Item item = createItemFromResultSet(rs);
                if(item != null) {
                    items.add(item);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("oh no in items");
        }
        return items;
    }
    private Item createItemFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String type = rs.getString("type");
        double weight = rs.getDouble("weight");

        return switch(type){
            case "Weapon" -> {
                int damage = rs.getInt("damage");
                String weaponTypeStr = rs.getString("weapon_type");
                WeaponType weaponType = WeaponType.valueOf(weaponTypeStr);
                yield new Weapon(name, weight, damage, weaponType);
            }
            case "Armor" -> {
                int armorValue = rs.getInt("armor_value");
                String armorTypeStr = rs.getString("armor_type");
                ArmorType armorType = ArmorType.valueOf(armorTypeStr);
                yield new Armor(name, weight, armorValue, armorType);
            }
            case "Consumable" -> {
                boolean stackable = rs.getBoolean("is_stackable");
                String consumableTypeStr = rs.getString("consumable_type");
                ConsumableType consumableType = ConsumableType.valueOf(consumableTypeStr);
                yield new Consumable(name, weight, stackable, consumableType);
            }
            default -> throw new SQLException("Something went wrong");
        };
    }

    public Item findItemById(int itemId) {
        String sql = "SELECT * FROM Item WHERE id = ?";
        try(Connection conn = DBConnect.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql); {
                stmt.setInt(1, itemId);
                try(ResultSet rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        return createItemFromResultSet(rs);
                    }
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("oh no in items");
        }
        return null;
    }

    public int findItemByName(Connection conn, String itemName) throws SQLException{
        String sql = "SELECT id FROM Items WHERE name = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, itemName);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int id = rs.getInt("id");
                }
            }
        }
        return -1;
    }
}



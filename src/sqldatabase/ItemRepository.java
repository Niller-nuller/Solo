package sqldatabase;

import com.sun.jdi.connect.spi.Connection;
import exceptions.ItemNotFound;
import models.Item;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRepository {
//    private String url;
//    private String username;
//    private String password;
//
//    public ItemRepository(String url, String user, String password) {
//        this.url = url;
//        this.username = user;
//        this.password = password;
//    }
//    public Item findByid(String id) throws ItemNotFound {
//        String sql = ("item id");
//
//        try (Connection conn = DriverManager.getConnection(url, username, password);
//             PreparedStatment stmt = conn.preperedStatment(sql)) {
//            stmt.setString(1, id);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if(rs.next()) {
//                    String itemId = rs.getString("id");
//                    return new item(itemId);
//                }else {
//                    throw new  ItemNotFound("Item with id " + id + " not found");
//                }
//            }
//        }catch (SQLException e) {
//            throw new RuntimeException("Database connection error");
//        }
//
//    }



}



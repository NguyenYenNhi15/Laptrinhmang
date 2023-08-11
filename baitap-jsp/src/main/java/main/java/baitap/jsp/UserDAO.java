/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package main.java.baitap.jsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Minh ngo
 */
public class UserDAO {
    Connection conn = null;
    public UserDAO() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/ltm", "xiaozhu", "alune@1509");
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public boolean checkLogin(User user){
        String query = "Select * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch(SQLException e) {
            System.out.println(e);
        } 
        return false;
    }
    
    public User searchUserByUsernameAndPassword(String username, String password) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }
}

package dao;

import java.sql.*;

import model.Account;

public class AccountDAO {
    public Account getAccount(String username, String password) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Account account = new Account();
                account.setUsername(result.getString("username"));
                account.setPassword(result.getString("password"));
                account.setID(result.getString("ID"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccount(String ID){
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM Account WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Account account = new Account();
                account.setUsername(result.getString("username"));
                account.setPassword(result.getString("password"));
                account.setID(result.getString("ID"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserNameExist(String username) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertAccount(Account account) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "INSERT INTO Account (username, password, ID) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getID());
            statement.executeUpdate();
            System.out.println("Account inserted");
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }

    public void updateAccountByID(Account account) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "UPDATE Account SET username = ?, password = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getID());
            statement.executeUpdate();
            System.out.println("Account updated");
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }

    public void deleteAccountByID(String ID) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "DELETE FROM Account WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            statement.executeUpdate();
            System.out.println("Account deleted");
        } catch (SQLException e) {
            e.printStackTrace();    
        } 
    }
}


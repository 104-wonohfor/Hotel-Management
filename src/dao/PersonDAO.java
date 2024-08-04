package dao;

import types.AccountType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import dto.Account;
import dto.Person;



public class PersonDAO {

    public String generateId(AccountType accountType) {
        String prefix;
        switch (accountType) {
            case GUEST:
                prefix = "G";
                break;
            case RECEPTIONIST:
                prefix = "R";
                break;
            case MANAGER:
                prefix = "M";
                break;
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    
        String id = null;
        Connection conn = DAO.DAO_DB();
    
        try {
            // Query the database to find the highest ID for the account type
            // phần Cast sẽ tách phần số ra khỏi ID, sau đó chuyển về kiểu INT để so sánh
            String sqlId = "SELECT MAX(CAST(SUBSTRING(ID, 2, LEN(ID)) AS INT)) AS maxId FROM Person WHERE ID LIKE ?";
            PreparedStatement statementId = conn.prepareStatement(sqlId);
            statementId.setString(1, prefix + "%");
            ResultSet resultId = statementId.executeQuery();
        
            // Generate the next ID
            if (resultId.next()) {
                int maxId = resultId.getInt("maxId");
                if (maxId != 0) {
                    int nextId = maxId + 1;
                    id = prefix + nextId;
                } else {
                    id = prefix + "1";
                }
            }
        
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return id;
    }

    public void insertPerson(Person person) {
        Connection conn = DAO.DAO_DB();
    
        try {
            String sqlPerson = "INSERT INTO Person(ID, name, address, email, phone, accountType) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statementPerson = conn.prepareStatement(sqlPerson);
            statementPerson.setString(1, person.getID());
            statementPerson.setString(2, person.getName());
            statementPerson.setString(3, person.getAddress());
            statementPerson.setString(4, person.getEmail());
            statementPerson.setString(5, person.getPhone());
            statementPerson.setString(6, person.getAccountType().toString());
            statementPerson.executeUpdate();
            System.out.println("Person inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }


    public String findPersonIDByPhone(String phone) {
        String id = null;
        Connection conn = DAO.DAO_DB();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM Person WHERE phone = ?");
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                id = rs.getString("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return id;
    }

    public boolean isEmailExist(String email) {
        Connection conn = DAO.DAO_DB();
        try {
            String sql = "SELECT * FROM Person WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
    
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Person getPersonByID(String ID) {
        Person person = new Person();

        Account account = new AccountDAO().getAccount(ID);

        if (account == null) {
            return null;
        }

        person.setID(account.getID());
        person.setUsername(account.getUsername());
        person.setPassword(account.getPassword());

        Connection conn = DAO.DAO_DB();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Person WHERE ID = ?");
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                person.setID(rs.getString("ID"));
                person.setName(rs.getString("name"));
                person.setAddress(rs.getString("address"));
                person.setEmail(rs.getString("email"));
                person.setPhone(rs.getString("phone"));
                person.setAccountType(AccountType.fromString(rs.getString("accountType")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return person;
    }

    public Person getPersonByPhone(String phone) {
        Person person = new Person();
        Connection conn = DAO.DAO_DB();
    
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Person WHERE phone = ?");
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                person.setID(rs.getString("ID"));
                person.setName(rs.getString("name"));
                person.setAddress(rs.getString("address"));
                person.setEmail(rs.getString("email"));
                person.setPhone(rs.getString("phone"));
                person.setAccountType(AccountType.fromString(rs.getString("accountType")));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    // Where Name LIKE %name%
    public List<Person> getPersonByName(String name) {
        List<Person> persons = new ArrayList<>();
        Connection conn = DAO.DAO_DB();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Person WHERE name LIKE ?");
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person();
                person.setID(rs.getString("ID"));
                person.setName(rs.getString("name"));
                person.setAddress(rs.getString("address"));
                person.setEmail(rs.getString("email"));
                person.setPhone(rs.getString("phone"));
                person.setAccountType(AccountType.fromString(rs.getString("accountType")));
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    // If type is "All" -> SELECT * FROM Person. Otherwise, SELECT * FROM Person WHERE accountType = type
    public List<Person> getAllPersons(String Type){
        List<Person> persons = new ArrayList<>();
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "SELECT * FROM Person";
            if (!Type.equals("All")) {
                sql += " WHERE accountType = ?";
            }

            PreparedStatement statement = conn.prepareStatement(sql);

            if (!Type.equals("All")) {
                statement.setString(1, Type);
            }

            ResultSet result = statement.executeQuery();
    
            while (result.next()) {
                Person person = new Person();
                person.setID(result.getString("ID"));
                person.setName(result.getString("name"));
                person.setAddress(result.getString("address"));
                person.setEmail(result.getString("email"));
                person.setPhone(result.getString("phone"));
                person.setAccountType(AccountType.fromString(result.getString("accountType")));
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;        
    }


    public void updatePersonByID(Person person) {
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "UPDATE Person SET name = ?, address = ?, email = ?, phone = ? WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, person.getName());
            statement.setString(2, person.getAddress());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPhone());
            statement.setString(5, person.getID());
            statement.executeUpdate();
            System.out.println("Person updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePersonByID(String ID) {
        Connection conn = DAO.DAO_DB();
    
        try {
            String sql = "DELETE FROM Person WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, ID);
            statement.executeUpdate();
            System.out.println("Person deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

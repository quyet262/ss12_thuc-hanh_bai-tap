package com.example.ss12_usermanager.service;

import com.example.ss12_usermanager.model.User;
import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO implements IUserDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/demo_123?useSSL=false";
    private String username = "root";
    private String password = "quyet2621994!";


    private static final String INSERT_USER_SQL = "INSERT INTO users(name,email,country) VALUES(?,?,?)";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_ID = "SELECT id,name,email,country FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT*FROM users";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name = ?,email = ?,country = ? WHERE id = ?";
    private static final String SEARCH_COUNTRY_SQL = "SELECT id,name,email,country FROM users WHERE country = ?";
    private static final String SORT_SQL_BY_NAME = "SELECT id,name,email,country FROM users ORDER BY name";

    public UserDAO() {
    }

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
return connection;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                user = new User(name, email, country);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);){
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String country = rs.getString("country");

                    users.add(new User(id, name, email, country));
                }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)){
            preparedStatement.setInt(1,id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public List<User> searCountry(String country) {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_COUNTRY_SQL)){
            preparedStatement.setString(1,country);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email= rs.getString("email");
                String countryName = rs.getString("country");

                users.add(new User(id, name, email, countryName));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.setInt(4, user.getId());


            rowUpdate = preparedStatement.executeUpdate()>0;
        }
        return rowUpdate;
    }

    @Override
    public List<User> sortAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SORT_SQL_BY_NAME)){

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                users.add(new User(id, name, email, country));

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

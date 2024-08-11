package org.example.repository;


import org.example.entity.Role;
import org.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepository implements CRUDRepository<User, Integer> {
    @Override
    public User save(User user) {
        if (!isUsernameUnique(user.getUsername())) {
            throw new IllegalArgumentException("Такой пользователь существует");
        }
        String insertSQL = "INSERT INTO car_shop.user (username, password, role) VALUES (?, ?, ?) RETURNING id";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int newId = resultSet.getInt("id");
                user.setId(newId);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось создать пользователя");
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, password, role FROM car_shop.user";

        try (Connection connection = DataBaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        String query = "SELECT id, username, password, role FROM car_shop.user WHERE id = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        String updateSQL = "UPDATE car_shop.user SET username = ?, password = ?, role = ? WHERE id = ? RETURNING id";
        List<User> userList = findByUsername(user.getUsername());
        for (User u : userList) {
            if (!user.getId().equals(u.getId())) {
                throw new RuntimeException("Не удалось обновить пользователя");
            }
        }
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateSQL)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.setInt(4, user.getId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int newId = resultSet.getInt("id");
                user.setId(newId);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private List<User> findByUsername(String username) {
        List<User> users = new ArrayList<>();
        String findByUsernameSQL = "SELECT * FROM car_shop.user WHERE username = ?";

        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(findByUsernameSQL)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM car_shop.user WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Integer getUserByUsernameAndPassword(String username, String password) {
        String authorizationSQL = "SELECT id FROM car_shop.user WHERE username = ? AND password = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(authorizationSQL)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");  // Return the user's ID if found
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no matching user is found
    }

    public void deleteByUsername(String username) {
        String deleteSQL = "DELETE FROM car_shop.user WHERE username = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {
            statement.setString(1, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameUnique(String username) {
        String query = "SELECT COUNT(*) FROM car_shop.user WHERE username = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

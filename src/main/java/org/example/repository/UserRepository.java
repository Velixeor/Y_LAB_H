package org.example.repository;


import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean deleteUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public Optional<User> getUserById(Integer id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public Optional<User> getUserByUsernameAndPassword(String username,String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean updateUser(User updatedUser) {
        Optional<User> userOpt = getUserById(updatedUser.getId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return true;
        }
        return false;
    }


}

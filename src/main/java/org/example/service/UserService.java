package org.example.service;


import org.example.entity.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public class UserService {
    private UserRepository userRepository;
    private int id;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        id=0;
    }

    public void registerUser(User user) {
        user.setId(id);
        id++;
        userRepository.addUser(user);

    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.getUserByUsernameAndPassword(username,password);
        if (userOpt.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean updateUser(User updatedUser) {
        return userRepository.updateUser(updatedUser);
    }

    public boolean deleteUser(String username) {
        return userRepository.deleteUser(username);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}

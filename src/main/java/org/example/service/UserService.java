package org.example.service;


import org.example.entity.User;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void registerUser(User user) {

        userRepository.save(user);

    }

    public Integer authenticateUser(String username, String password) {
        return userRepository.getUserByUsernameAndPassword(username,password);
    }

    public User updateUser(User updatedUser) {
        return userRepository.update(updatedUser);
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

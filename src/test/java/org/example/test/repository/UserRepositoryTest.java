package org.example.test.repository;


import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testAddUser() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        List<User> users = userRepository.getAllUsers();
        assertThat(users).contains(user);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User(1, "testuser1", "password123", Role.client);
        User user2 = new User(2, "testuser2", "password456", Role.administrator);
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        List<User> users = userRepository.getAllUsers();
        assertThat(users).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    public void testDeleteUser() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        boolean deleted = userRepository.deleteUser("testuser");
        List<User> users = userRepository.getAllUsers();

        assertThat(deleted).isTrue();
        assertThat(users).doesNotContain(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        boolean deleted = userRepository.deleteUser("nonexistentuser");
        assertThat(deleted).isFalse();
    }

    @Test
    public void testGetUserById() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.getUserById(1);
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void testGetUserByIdNotFound() {
        Optional<User> foundUser = userRepository.getUserById(1);
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void testGetUserByUsernameAndPassword() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.getUserByUsernameAndPassword("testuser", "password123");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void testGetUserByUsernameAndPasswordNotFound() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        Optional<User> foundUser = userRepository.getUserByUsernameAndPassword("testuser", "wrongpassword");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void testUpdateUser() {
        User user = new User(1, "testuser", "password123", Role.client);
        userRepository.addUser(user);

        User updatedUser = new User(1, "testuser", "newpassword", Role.administrator);
        boolean updated = userRepository.updateUser(updatedUser);

        assertThat(updated).isTrue();
        assertThat(userRepository.getUserById(1)).isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u.getPassword()).isEqualTo("newpassword");
                    assertThat(u.getRole()).isEqualTo(Role.administrator);
                });
    }

    @Test
    public void testUpdateUserNotFound() {
        User updatedUser = new User(1, "testuser", "newpassword", Role.administrator);
        boolean updated = userRepository.updateUser(updatedUser);

        assertThat(updated).isFalse();
    }
}

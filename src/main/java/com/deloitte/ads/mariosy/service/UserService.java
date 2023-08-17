package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosyDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.entity.UserDTO;
import com.deloitte.ads.mariosy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {

    }

    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();
        this.userRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUserByExternalId(UUID externalId) {
        return this.userRepository.findByExternalId(externalId).orElseThrow(
                () -> new NoSuchElementException("User does not exist")
        );
    }

    public MariosyDTO getUsersReceivedMariosy(UUID externalId) {
        Optional<User> userOptional = this.userRepository.findByExternalId(externalId);

        if (!userOptional.isPresent())
            throw new NoSuchElementException("User does not exist");

        User user = userOptional.get();

        Set<Marios> receivedMarios = user.getReceivedMarios();

        MariosyDTO mariosyDTO = new MariosyDTO(receivedMarios);

        return mariosyDTO;
    }

    public MariosyDTO getUsersGivenMariosy(UUID externalId) {
        Optional<User> userOptional = this.userRepository.findByExternalId(externalId);

        if (!userOptional.isPresent())
            throw new NoSuchElementException("User does not exist");

        User user = userOptional.get();

        Set<Marios> givenMarios = user.getGivenMarios();

        MariosyDTO mariosyDTO = new MariosyDTO(givenMarios);

        return mariosyDTO;
    }

    public synchronized User addUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), userDTO.getEmail(), UUID.randomUUID());

        Optional<User> userWithSameEmail = userRepository.findByEmail(userDTO.getEmail());
        Optional<User> userWithSameUsername = userRepository.findByUsername(userDTO.getUsername());

        if (userWithSameEmail.isPresent() || userWithSameUsername.isPresent())
            throw new IllegalArgumentException("Such user already exists");

        this.userRepository.save(user);

        return user;
    }

    public synchronized User addUser(String username, String email, UUID externalId) {
        User user = new User(username, email, externalId);

        Optional<User> userWithSameEmail = userRepository.findByEmail(email);
        Optional<User> userWithSameUsername = userRepository.findByUsername(username);
        Optional<User> userWithSameExternalId = userRepository.findByExternalId(externalId);


        if (userWithSameEmail.isPresent() || userWithSameUsername.isPresent() || userWithSameExternalId.isPresent())
            throw new IllegalArgumentException("Such user already exists");

        this.userRepository.save(user);

        return user;
    }

    public void deleteUser(UUID externalId) {
        User user = this.userRepository.findByExternalId(externalId).orElseThrow(
                () -> new NoSuchElementException("User does not exist")
        );

        this.userRepository.deleteById(user.getId());
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User does not exist")
        );
    }
}

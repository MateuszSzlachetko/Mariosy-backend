package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
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
        return this.userRepository.findByExternalId(externalId).orElseThrow();
    }

    public Set<Marios> getUsersReceivedMariosy(UUID externalId) {
        Optional<User> userOptional = this.userRepository.findByExternalId(externalId);

        if (!userOptional.isPresent())
            throw new NoSuchElementException("User does not exist");

        User user = userOptional.get();

        Set<Marios> receivedMarios = user.getReceivedMarios();

        return receivedMarios;
    }

    public Set<Marios> getUsersGivenMariosy(UUID externalId) {
        Optional<User> userOptional = this.userRepository.findByExternalId(externalId);

        if (!userOptional.isPresent())
            throw new NoSuchElementException("User does not exist");

        User user = userOptional.get();

        Set<Marios> givenMarios = user.getGivenMarios();

        return givenMarios;
    }

    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername(), userDTO.getEmail());

        this.userRepository.save(user);

        return user;
    }

    public void deleteUser(UUID externalId) {
        User user = this.userRepository.findByExternalId(externalId).orElseThrow();

        this.userRepository.deleteById(user.getId());
    }
}

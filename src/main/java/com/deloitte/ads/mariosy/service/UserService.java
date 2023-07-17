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

    public User getUserById(long id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    public Set<Marios> getUsersReceivedMariosy(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (!userOptional.isPresent())
            throw new NoSuchElementException();

        User user = userOptional.get();

        Set<Marios> receivedMarios = user.getReceivedMarios();

        return receivedMarios;
    }

    public Set<Marios> getUsersGivenMariosy(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);

        if (!userOptional.isPresent())
            throw new NoSuchElementException();

        User user = userOptional.get();

        Set<Marios> givenMarios = user.getGivenMarios();

        return givenMarios;
    }

    public void addUser(UserDTO userDTO) {
        User user = new User(userDTO.getUserName(), userDTO.getEmail());

        this.userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();

        this.userRepository.deleteById(id);
    }
}

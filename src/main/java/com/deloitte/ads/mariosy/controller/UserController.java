package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.entity.UserDTO;
import com.deloitte.ads.mariosy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Set<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/marios/received")
    public Set<Marios> getUsersReceivedMariosy(@PathVariable long id) {
        return userService.getUsersReceivedMariosy(id);
    }

    @GetMapping("/{id}/marios/given")
    public Set<Marios> getUsersGivenMariosy(@PathVariable long id) {
        return userService.getUsersGivenMariosy(id);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserDTO userDTO) {
        this.userService.addUser(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }
}

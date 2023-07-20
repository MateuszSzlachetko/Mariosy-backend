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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Set<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{externalId}")
    public User getUserById(@PathVariable UUID externalId) {
        return userService.getUserByExternalId(externalId);
    }

    @GetMapping("/{externalId}/marios/received")
    public Set<Marios> getUsersReceivedMariosy(@PathVariable UUID externalId) {
        return userService.getUsersReceivedMariosy(externalId);
    }

    @GetMapping("/{externalId}/marios/given")
    public Set<Marios> getUsersGivenMariosy(@PathVariable UUID externalId) {
        return userService.getUsersGivenMariosy(externalId);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserDTO userDTO) {
        return this.userService.addUser(userDTO);
    }

    @DeleteMapping("/delete/{externalId}")
    public void deleteUser(@PathVariable UUID externalId) {
        this.userService.deleteUser(externalId);
    }
}

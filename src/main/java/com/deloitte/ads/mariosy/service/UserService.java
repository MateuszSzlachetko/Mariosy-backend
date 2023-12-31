package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.repository.User;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private Set<User> users;

    public UserService() {
        User u1 = new User(1, "Mateusz");
        User u2 = new User(2, "Bartek");
        User u3 = new User(3, "Marek");
        User u4 = new User(4, "Artur");
        User u5 = new User(5, "Dawid");

        this.users = Sets.newHashSet(u1, u2, u3, u4, u5);
    }

    public Set<User> getUsers() {
        return users;
    }

    public User getUserById(long id) throws NoSuchElementException {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        if (user.isPresent())
            return user.get();

        throw new NoSuchElementException("User with such id does not exist");
    }

    public Set<User> getUsersByIds(Set<Long> Ids) {
        Set<User> usersByIds;

        usersByIds = this.users.stream().filter(u -> Ids.contains(u.getId())).collect(Collectors.toSet());

        return usersByIds;
    }
}

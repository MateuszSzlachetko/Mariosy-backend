package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosyDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.entity.UserDTO;
import com.deloitte.ads.mariosy.repository.UserRepository;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers() {
        // given
        User user1 = new User("Mateusz", "mateusz@gmail.com");
        User user2 = new User("Bartosz", "bartek@gmail.com");
        User user3 = new User("Kamil", "kamil@gmail.com");

        Set<User> users = Sets.newHashSet();
        Set<User> usersEmpty = Sets.newHashSet();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        // when
        when(userRepository.findAll()).thenReturn(users);
        Set<User> usersFromService = userService.getUsers();

        when(userRepository.findAll()).thenReturn(usersEmpty);
        Set<User> usersFromServiceEmpty = userService.getUsers();

        // then
        assertEquals(users, usersFromService);
        assertEquals(3, usersFromService.size());

        assertEquals(0, usersFromServiceEmpty.size());
    }

    @Test
    public void shouldGetUserByExternalId() {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        UUID uuid = user.getExternalId();

        // when
        when(userRepository.findByExternalId(uuid)).thenReturn(Optional.of(user));
        User userFromService = userService.getUserByExternalId(uuid);

        //then
        assertEquals(user.getId(), userFromService.getId());
        assertEquals(user, userFromService);
    }

    @Test
    public void shouldGetUserByUsername() {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        UUID uuid = user.getExternalId();

        // when
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User userFromService = userService.getUserByUsername(user.getUsername());

        //then
        assertEquals(user.getId(), userFromService.getId());
        assertEquals(user, userFromService);
    }

    @Test
    public void shouldThrowWhenUserDoesNotExist() {
        // given
        UUID uuid = UUID.randomUUID();

        // when
        NoSuchElementException thrown1 = Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getUserByExternalId(uuid));
        NoSuchElementException thrown2 = Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getUsersGivenMariosy(uuid));
        NoSuchElementException thrown3 = Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getUsersReceivedMariosy(uuid));
        NoSuchElementException thrown4 = Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getUserByUsername("-"));

        //then
        assertEquals("User does not exist", thrown1.getMessage());
        assertEquals("User does not exist", thrown2.getMessage());
        assertEquals("User does not exist", thrown3.getMessage());
    }

    @Test
    public void shouldGetUsersReceivedMariosy() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User receiver = new User("Bartosz", "bartek@gmail.com");
        Marios marios1 = new Marios("Mario", "Good job!", author, "Greetings");
        Marios marios2 = new Marios("Luigi", "Excellent!", author, "Greetings");
        UUID receiverExternalId = receiver.getExternalId();

        // when
        receiver.addMarios(marios1);
        receiver.addMarios(marios2);
        when(userRepository.findByExternalId(receiverExternalId)).thenReturn(Optional.of(receiver));
        MariosyDTO userReceivedMariosy = userService.getUsersReceivedMariosy(receiverExternalId);

        //then
        assertTrue(userReceivedMariosy.getMariosy().contains(marios1));
        assertTrue(userReceivedMariosy.getMariosy().contains(marios2));
        assertEquals(2, userReceivedMariosy.getCount());
    }

    @Test
    public void shouldGetUsersGivenMariosy() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User receiver = new User("Bartosz", "bartek@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author, "Greetings");
        UUID authorExternalId = author.getExternalId();

        // when
        receiver.addMarios(marios);

        when(userRepository.findByExternalId(authorExternalId)).thenReturn(Optional.of(author));
        MariosyDTO userGivenMariosy = userService.getUsersGivenMariosy(authorExternalId);
        System.out.println(userGivenMariosy);
        //then
        assertTrue(userGivenMariosy.getMariosy().contains(marios));
        assertEquals(1, userGivenMariosy.getCount());
    }

    @Test
    public void shouldAddUser() {
        // given
        UserDTO userDTO = new UserDTO("Mateusz", "mateusz@gmail.com");

        // when
        User addedUser = userService.addUser(userDTO);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        verify(userRepository, times(1)).save(any());

        //then
        User capturedUser = userArgumentCaptor.getValue();

        assertEquals(userDTO.getUsername(), capturedUser.getUsername());
        assertEquals(userDTO.getEmail(), capturedUser.getEmail());
        assertEquals(addedUser, capturedUser);
    }

    @Test
    public void shouldThrowWhenUserWithGivenEmailAlreadyExists() {
        // given
        UserDTO userDTO = new UserDTO("Mateusz", "mateusz@gmail.com");
        User existingUser = new User("Mateusz", "mateusz@gmail.com");

        // when
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(userDTO));

        //then
        assertEquals("Such user already exists", thrown.getMessage());
    }

    @Test
    public void shouldThrowWhenUserWithGivenUsernameAlreadyExists() {
        // given
        UserDTO userDTO = new UserDTO("Mateusz", "mateusz@gmail.com");
        User existingUser = new User("Mateusz", "mateusz@gmail.com");

        // when
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(existingUser));
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(userDTO));

        //then
        assertEquals("Such user already exists", thrown.getMessage());
    }

    @Test
    public void shouldDeleteUser() {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        UUID uuid = user.getExternalId();

        // when
        when(userRepository.findByExternalId(uuid)).thenReturn(Optional.of(user));
        userService.deleteUser(uuid);

        //then
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void shouldThrowWhenDeletingUserWhoDoesNotExist() {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        UUID uuid = user.getExternalId();

        // when
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.deleteUser(uuid));

        //then
        assertEquals("User does not exist", thrown.getMessage());
        verify(userRepository, times(0)).deleteById(user.getId());
    }
}

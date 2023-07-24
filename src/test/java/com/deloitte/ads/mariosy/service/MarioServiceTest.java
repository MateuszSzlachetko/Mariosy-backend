package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.repository.MariosRepository;
import com.deloitte.ads.mariosy.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarioServiceTest {

    @InjectMocks
    private MarioService marioService;

    @Mock
    private MariosRepository mariosRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldAddMarios() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User receiver1 = new User("Bartosz", "bartek@gmail.com");
        User receiver2 = new User("Kamil", "kamil@gmail.com");

        Set<UUID> receiversExternalId = Set.of(receiver1.getExternalId(), receiver2.getExternalId());
        MariosDTO mariosDTO = new MariosDTO("Mario", "Good job!", author.getExternalId(), receiversExternalId);

        // when
        when(userRepository.findByExternalId(author.getExternalId())).thenReturn(Optional.of(author));
        when(userRepository.findByExternalId(receiver1.getExternalId())).thenReturn(Optional.of(receiver1));
        when(userRepository.findByExternalId(receiver2.getExternalId())).thenReturn(Optional.of(receiver2));
        Marios addedMarios = marioService.addMarios(mariosDTO);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);


        //then
        verify(userRepository).save(argumentCaptor.capture());
        verify(userRepository, times(3)).findByExternalId(any());

        User authorFromCaptor = argumentCaptor.getValue();

        assertFalse(author.getGivenMarios().isEmpty());
        assertEquals(authorFromCaptor, author);
        assertTrue(author.getGivenMarios().contains(addedMarios));
        assertTrue(receiver1.getReceivedMarios().contains(addedMarios));
        assertTrue(receiver2.getReceivedMarios().contains(addedMarios));
    }

    @Test
    public void shouldThrowWhenAuthorIsReceiver() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");

        Set<UUID> receiversExternalId = Set.of(author.getExternalId());
        MariosDTO mariosDTO = new MariosDTO("Mario", "Good job!", author.getExternalId(), receiversExternalId);

        // when
        when(userRepository.findByExternalId(author.getExternalId())).thenReturn(Optional.of(author));
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> marioService.addMarios(mariosDTO));

        //then
        assertEquals("You can not give marios to yourself", thrown.getMessage());
    }

    @Test
    public void shouldThrowWhenUsersDoesNotExist() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        UUID randomUUID = UUID.randomUUID();

        Set<UUID> receiversExternalId = Set.of(randomUUID);
        MariosDTO mariosDTO = new MariosDTO("Mario", "Good job!", author.getExternalId(), receiversExternalId);

        // when
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class,
                () -> marioService.addMarios(mariosDTO));

        //then
        assertEquals("User does not exist", thrown.getMessage());
    }

    @Test
    public void shouldDeleteMarios() {
        // given
        User user = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", user);

        // when
        when(userRepository.findByExternalId(user.getExternalId())).thenReturn(Optional.of(user));
        when(mariosRepository.findByExternalId(marios.getExternalId())).thenReturn(Optional.of(marios));
        marioService.deleteMarios(marios.getExternalId(), user.getExternalId());

        //then
        verify(mariosRepository, times(1)).deleteById(marios.getId());
    }

    @Test
    public void shouldThrowWhenUserDidNotCreateThatMarios() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User notAuthor = new User("Mateusz2", "mateusz2@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author);

        // when
        when(userRepository.findByExternalId(notAuthor.getExternalId())).thenReturn(Optional.of(notAuthor));
        when(mariosRepository.findByExternalId(marios.getExternalId())).thenReturn(Optional.of(marios));
        IllegalCallerException thrown = Assertions.assertThrows(IllegalCallerException.class,
                () -> marioService.deleteMarios(marios.getExternalId(), notAuthor.getExternalId()));

        //then
        assertEquals("You have not sent that marios", thrown.getMessage());
        verify(mariosRepository, times(0)).deleteById(marios.getId());
    }

    @Test
    public void shouldThrowWhenUserDoesNotExistWhenDeletingMarios() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User notExistingUser = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author);

        // when
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class,
                () -> marioService.deleteMarios(marios.getExternalId(), notExistingUser.getExternalId()));

        //then
        assertEquals("User does not exist", thrown.getMessage());
        verify(mariosRepository, times(0)).deleteById(marios.getId());
    }

    @Test
    public void shouldThrowWhenMariosDoesNotExistWhenDeletingMarios() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        Marios marios = new Marios("Mario", "Good job!", author);

        // when
        when(userRepository.findByExternalId(author.getExternalId())).thenReturn(Optional.of(author));
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class,
                () -> marioService.deleteMarios(marios.getExternalId(), author.getExternalId()));

        //then
        assertEquals("Marios does not exist", thrown.getMessage());
        verify(mariosRepository, times(0)).deleteById(marios.getId());
    }

    @Test
    public void shouldThrowWhenReceiverDoesNotExist() {
        // given
        User author = new User("Mateusz", "mateusz@gmail.com");
        User receiver1 = new User("Bartosz", "bartek@gmail.com");
        User receiver2 = new User("Kamil", "kamil@gmail.com");


        Set<UUID> receiversExternalId = Set.of(receiver1.getExternalId(), receiver2.getExternalId());
        MariosDTO mariosDTO = new MariosDTO("Mario", "Good job!", author.getExternalId(), receiversExternalId);

        // when
        when(userRepository.findByExternalId(author.getExternalId())).thenReturn(Optional.of(author));
        NoSuchElementException thrown = Assertions.assertThrows(NoSuchElementException.class,
                () -> marioService.addMarios(mariosDTO));

        //then
        assertEquals("Receiver does not exist", thrown.getMessage());
    }
}

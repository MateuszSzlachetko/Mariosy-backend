package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.repository.MariosRepository;
import com.deloitte.ads.mariosy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MarioService {

    @Autowired
    private MariosRepository mariosRepository;

    @Autowired
    private UserRepository userRepository;

    public MarioService() {
    }

    public Marios addMarios(MariosDTO mariosDTO) throws NoSuchElementException, IllegalArgumentException {
        User author = this.userRepository.findByExternalId(mariosDTO.getAuthorId()).orElseThrow();
        Marios marios = new Marios(mariosDTO.getCharacterName(), mariosDTO.getComment(), author);

        mariosDTO.getReceiversIds().forEach(id -> {
            User receiver = this.userRepository.findByExternalId(id).orElseThrow();

            if (receiver.getExternalId() == author.getExternalId())
                throw new IllegalArgumentException("You can not give marios to yourself");

            receiver.addMarios(marios);
        });

        userRepository.save(author);

        return marios;
    }

    public void deleteMarios(UUID mariosId, UUID userId) {
        User author = this.userRepository.findByExternalId(userId).orElseThrow();
        Marios marios = this.mariosRepository.findByExternalId(mariosId).orElseThrow();

        if (author.getExternalId() != marios.getAuthor().getExternalId())
            throw new IllegalCallerException("You have not sent that marios");

        this.mariosRepository.deleteById(marios.getId());
    }
}

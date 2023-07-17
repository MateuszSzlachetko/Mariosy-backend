package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.entity.Marios;
import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.entity.User;
import com.deloitte.ads.mariosy.repository.MariosRepository;
import com.deloitte.ads.mariosy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarioService {

    @Autowired
    private MariosRepository mariosRepository;

    @Autowired
    private UserRepository userRepository;

    public MarioService() {
    }

    public void addMarios(MariosDTO mariosDTO) throws NullPointerException, IllegalArgumentException {
        User author = this.userRepository.findById(mariosDTO.getAuthorId()).orElseThrow();
        Marios marios = new Marios(mariosDTO.getCharacterName(), mariosDTO.getComment(), author);

        mariosDTO.getReceiversIds().forEach(id -> {
            User receiver = this.userRepository.findById(id).orElseThrow();

            if (receiver.getId() == author.getId())
                throw new IllegalArgumentException("You can not give marios to yourself");

            receiver.addMarios(marios);
        });

        userRepository.save(author);
    }

    public void deleteMarios(Long mariosId, Long userId) {
        User author = this.userRepository.findById(userId).orElseThrow();
        Marios marios = this.mariosRepository.findById(mariosId).orElseThrow();

        if (author.getId() != marios.getAuthor().getId())
            throw new IllegalCallerException("You have not send that marios");

        this.mariosRepository.deleteById(mariosId);
    }
}

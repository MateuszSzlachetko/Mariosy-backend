package com.deloitte.ads.mariosy.service;

import com.deloitte.ads.mariosy.repository.Marios;
import com.deloitte.ads.mariosy.repository.User;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MarioService {

    private Set<Marios> mariosy;

    public MarioService() {
        mariosy = Sets.newHashSet();
    }

    public Set<Marios> getMariosy() {
        return mariosy;
    }

    public void addMarios(Marios marios, User author, Set<User> receivers) throws NullPointerException, IllegalArgumentException {
        if (receivers == null)
            throw new NullPointerException("No users provided");

        if (receivers.contains(author))
            throw new IllegalArgumentException("You can not give marios to yourself");

        author.giveMarios(marios);

        for (User r : receivers) {
            r.addMarios(marios);
        }

        mariosy.add(marios);
    }
}

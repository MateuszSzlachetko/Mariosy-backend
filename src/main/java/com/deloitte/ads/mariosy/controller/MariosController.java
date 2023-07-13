package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.repository.Marios;
import com.deloitte.ads.mariosy.repository.User;
import com.deloitte.ads.mariosy.service.MarioService;
import com.deloitte.ads.mariosy.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/marios/")
public class MariosController {

    @Autowired
    private MarioService marioService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public Set<Marios> getMarios() {
        return marioService.getMariosy();
    }

    @GetMapping("/reactions")
    public Set<Marios> getAvailableMarios() {
        return marioService.getMariosy();
    }

    @PostMapping("add")
    public void addMarios(@RequestBody MariosDTO mariosDTO) {
        User author = userService.getUserById(mariosDTO.getAuthorId());
        Set<User> receivers = userService.getUsersByIds(mariosDTO.getReceiversIds());
        Marios marios = new Marios(mariosDTO.getName(), mariosDTO.getComment(), author.getId());

        marioService.addMarios(marios, author, receivers);
    }
}

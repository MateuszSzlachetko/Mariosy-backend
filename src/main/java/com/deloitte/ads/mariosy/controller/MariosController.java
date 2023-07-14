package com.deloitte.ads.mariosy.controller;

import com.deloitte.ads.mariosy.entity.MariosDTO;
import com.deloitte.ads.mariosy.service.MarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marios")
public class MariosController {

    @Autowired
    private MarioService marioService;

    @PostMapping("/add")
    public void addMarios(@RequestBody MariosDTO mariosDTO) {
        marioService.addMarios(mariosDTO);
    }
}

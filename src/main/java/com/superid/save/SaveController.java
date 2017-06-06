package com.superid.save;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zzt on 17/6/6.
 */
@RestController
@RequestMapping("/save")
public class SaveController {

    @PutMapping("/user")
    public boolean addUser() {
        return false;
    }
}

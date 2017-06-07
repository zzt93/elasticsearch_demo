package com.superid.save;

import com.superid.query.chat.Chat;
import com.superid.query.chat.ChatRepo;
import com.superid.query.user.User;
import com.superid.query.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zzt on 17/6/6.
 */
@RestController
@RequestMapping("/query")
public class QueryController {

    private final UserRepo userRepo;
    private final ChatRepo chatRepo;

    @Autowired
    public QueryController(UserRepo userRepo, ChatRepo chatRepo) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
    }

    @PutMapping("/user")
    public boolean queryUser(@RequestBody User user) {
        return false;
    }

    @PutMapping("/chat")
    public boolean queryChat(@RequestBody Chat chat) {

        return false;
    }
}

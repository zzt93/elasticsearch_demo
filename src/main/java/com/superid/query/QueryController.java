package com.superid.query;

import com.superid.query.dynamic.chat.Chat;
import com.superid.query.dynamic.chat.ChatRepo;
import com.superid.query.precreate.file.FileRepo;
import com.superid.query.precreate.role.Role;
import com.superid.query.precreate.user.User;
import com.superid.query.precreate.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zzt on 17/6/6.
 */
@RestController
@RequestMapping("/query")
public class QueryController {

    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private final FileRepo fileRepo;

    @Autowired
    public QueryController(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.fileRepo = fileRepo;
    }

    @PostMapping("/publish")
    public Slice<Publish> query(@RequestBody Publish publish) {
        return null;
    }

    @PostMapping("/user")
    public boolean queryUser(@RequestBody User user) {
        return false;
    }

    @PostMapping("/chat")
    public boolean queryChat(@RequestBody Chat chat) {

        return false;
    }

    @PostMapping("role")
    public boolean queryRole(@RequestBody Role role) {
        return false;
    }
}

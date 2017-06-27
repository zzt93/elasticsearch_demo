package com.superid.query;

import com.superid.query.time.chat.Chat;
import com.superid.query.time.chat.ChatRepo;
import com.superid.query.user.file.FileRepo;
import com.superid.query.user.role.Role;
import com.superid.query.user.user.User;
import com.superid.query.user.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
    private final ElasticsearchTemplate template;

    @Autowired
    public QueryController(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, ElasticsearchTemplate template) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.fileRepo = fileRepo;
        this.template = template;
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

    @PostMapping("/role/alliance")
    public boolean queryAllianceRole(@RequestBody Role role) {
        return false;
    }

    @PostMapping("/role/all")
    public boolean queryAllRole(@RequestBody Role role) {
        return false;
    }
}

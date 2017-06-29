package com.superid.save;

import com.superid.query.time.announcement.Announcement;
import com.superid.query.time.chat.Chat;
import com.superid.query.time.task.Task;
import com.superid.query.user.affair.Affair;
import com.superid.query.user.file.File;
import com.superid.query.user.role.Role;
import com.superid.query.user.user.User;
import com.superid.query.user.warehouse.Material;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzt on 17/6/28.
 */
public enum SaveType {

    ANNOUNCEMENT(Announcement.class) {
        @Override
        public String indexSuffix(Object data) {
            return new SimpleDateFormat("yyyy-MM").format(new Date());
        }
    }, CHAT(Chat.class) {
        @Override
        public String indexSuffix(Object data) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        }
    }, TASK(Task.class) {
        @Override
        public String indexSuffix(Object data) {
            return new SimpleDateFormat("yyyy").format(new Date());
        }
    }, AFFAIR(Affair.class), FILE(File.class), ROLE(Role.class), USER(User.class), MATERIAL(Material.class);

    private final Class<?> aClass;

    SaveType(Class<?> aClass) {
        this.aClass = aClass;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    String indexSuffix(Object data) {
        return data.toString();
    }

    public String indexName(Object extra) {
        return name() + indexSuffix(extra);
    }
}

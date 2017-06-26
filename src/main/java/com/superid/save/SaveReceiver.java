package com.superid.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.superid.MessageFormat;
import com.superid.MessageFormatReceiver;
import com.superid.query.dynamic.announcement.AnnouncementRepo;
import com.superid.query.precreate.file.File;
import com.superid.query.precreate.file.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zzt on 17/6/7.
 */
public class SaveReceiver extends MessageFormatReceiver {
    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private AnnouncementRepo announcementRepo;

    @Override
    public void receiveMessage(MessageFormat messageFormat) {
        System.out.println(messageFormat);

        File file = gson.fromJson((String) messageFormat.getPayload("file"), File.class);
        fileRepo.save(file);
    }
}

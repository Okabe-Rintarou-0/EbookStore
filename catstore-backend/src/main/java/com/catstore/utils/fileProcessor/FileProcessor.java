package com.catstore.utils.fileProcessor;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class FileProcessor {

    private final StringBuilder filterPattern;

    private MultipartFile uploadFile = null;

    public FileProcessor() {
        filterPattern = new StringBuilder();
    }

    public static FileProcessor createFileProcessor() {
        return new FileProcessor();
    }

    public FileProcessor addUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
        return this;
    }

    public FileProcessor addFilter(String fileType) {
        if (filterPattern.length() > 0)
            filterPattern.append("|");
        String filter = ".*\\." + fileType;
        filterPattern.append(filter);
        return this;
    }

    public FileProcessor addFilters(String[] fileTypes) {
        for (String fileType : fileTypes) {
            if (filterPattern.length() > 0)
                filterPattern.append("|");
            String filter = ".*\\." + fileType;
            filterPattern.append(filter);
        }
        System.out.println("newFilter: " + filterPattern);
        return this;
    }

    public boolean isValid() {
        return uploadFile != null && Pattern.matches(filterPattern.toString(), uploadFile.getOriginalFilename());
    }

    @Async
    public FileProcessor saveUploadFileTo(String directoryPath) {
        if (uploadFile != null) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    File dest = new File(new File(directoryPath).getAbsolutePath() + "/" + uploadFile.getOriginalFilename());
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    InputStream inputStream = uploadFile.getInputStream();
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    inputStream.close();
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(dest));
                    dataOutputStream.write(bytes);
                    dataOutputStream.flush();
                    System.out.printf("%s : save file to %s%n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), dest.getPath());
                }
            }).start();
        }
        return this;
    }
}


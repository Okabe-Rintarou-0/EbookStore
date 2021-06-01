package com.catstore.controller;

import com.catstore.service.FileService;
import com.catstore.utils.messageUtils.Message;
import com.catstore.utils.messageUtils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    FileService fileService;

    @Autowired
    void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/upload")
    Message handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileService.handleFiles(file) ?
                MessageUtil.createMessage(MessageUtil.UPLOAD_SUCCESS_CODE, MessageUtil.UPLOAD_SUCCESS_MSG) :
                MessageUtil.createMessage(MessageUtil.WRONG_FILE_FORMAT_CODE, MessageUtil.WRONG_FILE_FORMAT_MSG);
    }
}

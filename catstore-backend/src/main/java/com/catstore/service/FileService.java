package com.catstore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    Boolean handleFiles(MultipartFile uploadFile) throws IOException;
}

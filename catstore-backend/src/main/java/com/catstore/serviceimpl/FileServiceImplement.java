package com.catstore.serviceimpl;

import com.catstore.service.FileService;
import com.catstore.utils.fileProcessor.CsvFileProcessor;
import com.catstore.utils.fileProcessor.FileProcessor;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImplement implements FileService {

    CsvFileProcessor csvFileProcessor;

    @Autowired
    void setCsvFileProcessor(CsvFileProcessor csvFileProcessor) {
        this.csvFileProcessor = csvFileProcessor;
    }

    @Override
    public Boolean handleFiles(MultipartFile uploadFile) throws IOException {
        if (FileProcessor.createFileProcessor()
                .addFilter("csv")
                .addUploadFile(uploadFile)
                .isValid()) {
            try {
                String content = csvFileProcessor.readContent(uploadFile);
                JSONArray parseResult = csvFileProcessor.parseContent(content);
                csvFileProcessor.parseAndSave(parseResult);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }
}

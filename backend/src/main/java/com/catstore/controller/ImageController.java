package com.catstore.controller;

import com.catstore.constants.Constant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/image")
public class ImageController {
    @GetMapping
    public void getImage(@RequestParam("target") String target, HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = null;
            if (target.equals("book")) file = new File(Constant.bookRankSavePath);
            else if (target.equals("consumption"))
                file = new File(Constant.consumptionRankSavePath);
            else return;
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

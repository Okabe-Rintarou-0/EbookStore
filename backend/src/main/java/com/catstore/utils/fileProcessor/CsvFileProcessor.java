package com.catstore.utils.fileProcessor;

import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;


@Component
public class CsvFileProcessor {

    BookDao bookDao;

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public boolean isCsvFile(MultipartFile file) {
        String pattern = ".+\\.csv";
        return Pattern.matches(pattern, file.getOriginalFilename());
    }

    public String readContent(MultipartFile multipartFile) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("/n");
        }
        System.out.println("content: " + content);
        return content.toString();
    }

    private ArrayList<String> readColumns(String row) {
        ArrayList<String> columns = new ArrayList<>();
        int rowLength = row.length();
        for (int i = 0; i < rowLength; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            char thisChar = row.charAt(i);
            if (thisChar == '\"') {
                while ((thisChar = row.charAt(++i)) != '\"') {
                    stringBuilder.append(thisChar);
                }
                ++i; //skip the ','
            } else
                while (thisChar != ',' && i != rowLength - 1) {
                    stringBuilder.append(thisChar);
                    thisChar = row.charAt(++i);
                }
            columns.add(stringBuilder.toString());
        }
        return columns;
    }

    public JSONArray parseContent(String content) {
        JSONArray parseResult = new JSONArray();
        String[] rows = content.split("/n");
        int rowNumber = rows.length;
        String[] columnNames = rows[0].split(",");
        int colNumber = columnNames.length;
        for (int i = 1; i < rowNumber; ++i) {
            ArrayList<String> cols = readColumns(rows[i]);
            JSONObject object = new JSONObject();
            int colRealSize = cols.size();
            for (int j = 0; j < colRealSize; ++j) {
                String col = cols.get(j);
                if (col.startsWith("\"") && col.startsWith("\""))
                    col = col.substring(1, col.length() - 2);
                object.put(columnNames[j], col);
            }
            for (int j = colRealSize; j < colNumber; ++j) {
                object.put(columnNames[j], null);
            }
            parseResult.add(object);
        }
        System.out.println("parseResult: " + parseResult.toString());
        return parseResult;
    }

    private Book parseJson(JSONObject bookJson) {
        String bookTitle = bookJson.getString("bookTitle");
        String bookTag = bookJson.getString("bookTag");
        String bookType = bookJson.getString("bookType");
        String bookCover = bookJson.getString("bookCover");
        String bookAuthor = bookJson.getString("bookAuthor");
        Integer bookStock = bookJson.getInt("bookStock");
        BigDecimal bookPrice = new BigDecimal(bookJson.getString("bookPrice"));
        String bookDescription = bookJson.getString("bookDescription");
        String bookDetails = bookJson.getString("bookDetails");
        String forSale_str = bookJson.getString("forSale").toLowerCase();
        Boolean forSale = forSale_str.equals("true");
        Integer sales = 0;
        Book book = new Book();
        book.setBookAuthor(bookAuthor);
        book.setBookDescription(bookDescription);
        book.setBookPrice(bookPrice);
        book.setBookDetails(bookDetails);
        book.setBookStock(bookStock);
        book.setBookCover(bookCover);
        book.setBookType(bookType);
        book.setBookTitle(bookTitle);
        book.setBookTag(bookTag);
        book.setForSale(forSale);
        book.setSales(sales);
        return book;
    }

    public Boolean parseAndSave(JSONArray content) {
        for (Object object : content) {
            JSONObject bookJson = JSONObject.fromObject(object);
            Book book = parseJson(bookJson);
            bookDao.saveBook(book);
            System.out.println(parseJson(bookJson).toString());
        }
        return true;
    }
}

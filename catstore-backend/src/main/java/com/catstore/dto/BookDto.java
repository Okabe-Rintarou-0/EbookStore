package com.catstore.dto;

import com.catstore.entity.Book;

import java.util.ArrayList;
import java.util.Date;

public interface BookDto {

    ArrayList<Book> getAllRankedBooks();

    ArrayList<Book> getRankedBooks(Date from, Date to);
}

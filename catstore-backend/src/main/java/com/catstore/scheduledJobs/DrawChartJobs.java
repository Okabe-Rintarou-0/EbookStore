package com.catstore.scheduledJobs;

import com.catstore.dao.BookDao;
import com.catstore.entity.Book;
import com.catstore.utils.Constant;
import com.catstore.utils.Drawer.BarChartDrawer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class DrawChartJobs {
    BarChartDrawer barChartDrawer;
    BookDao bookDao;

    @Autowired
    void setBarChartDrawer(BarChartDrawer barChartDrawer) {
        this.barChartDrawer = barChartDrawer;
    }

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Scheduled(fixedRate = 30000)
    void drawBookRankEveryHalfMinute() throws IOException {
        System.out.println("now start to draw");
        ArrayList<Book> rankedBooks = bookDao.getRankedBooks();
        int totalSize = rankedBooks.size();
        if (totalSize > 10) totalSize = 10;
        ArrayList<Integer> data = new ArrayList<>();
        ArrayList<String> alternative = new ArrayList<>();
        ArrayList<String> bottomLabels = new ArrayList<>();
        for (int i = 0; i < totalSize; ++i) {
            data.add(rankedBooks.get(i).getSales());
            alternative.add(i + 1 + "");
            bottomLabels.add(rankedBooks.get(i).getBookTitle());
        }
        barChartDrawer.drawBarChart(data, alternative, bottomLabels, "书籍销量排名", "书籍名称", "销量(元)", Constant.rankImgSavePath);
    }
}

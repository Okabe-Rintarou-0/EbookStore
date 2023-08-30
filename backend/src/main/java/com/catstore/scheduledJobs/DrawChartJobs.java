package com.catstore.scheduledJobs;

import com.catstore.dao.BookDao;
import com.catstore.dao.ConsumptionDao;
import com.catstore.dao.UserDao;
import com.catstore.entity.Book;
import com.catstore.entity.Consumption;
import com.catstore.entity.User;
import com.catstore.constants.Constant;
import com.catstore.utils.drawer.BarChartDrawer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class DrawChartJobs {
    BarChartDrawer barChartDrawer;
    BookDao bookDao;
    ConsumptionDao consumptionDao;
    UserDao userDao;

    @Autowired
    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    void setConsumptionDao(ConsumptionDao consumptionDao) {
        this.consumptionDao = consumptionDao;
    }

    @Autowired
    void setBarChartDrawer(BarChartDrawer barChartDrawer) {
        this.barChartDrawer = barChartDrawer;
    }

    @Autowired
    void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

//    @Scheduled(fixedRate = 30000)
    void drawBookRankEveryHalfMinute() throws IOException {
        System.out.println("now start to draw");
        ArrayList<Book> rankedBooks = bookDao.getAllRankedBooks();
        int totalSize = rankedBooks.size();
        if (totalSize > 10) totalSize = 10;
        ArrayList<Float> data = new ArrayList<>();
        ArrayList<String> alternative = new ArrayList<>();
        ArrayList<String> bottomLabels = new ArrayList<>();
        for (int i = 0; i < totalSize; ++i) {
            data.add(rankedBooks.get(i).getSales().floatValue());
            alternative.add(i + 1 + "");
            bottomLabels.add(rankedBooks.get(i).getBookTitle());
        }
        barChartDrawer.drawBarChart(data, alternative, bottomLabels, "书籍销量排名", "书籍名称", "销量(元)", Constant.bookRankSavePath);
    }

//    @Scheduled(fixedRate = 30000)
    void drawConsumptionRankEveryHalfMinute() throws IOException {
        System.out.println("now start to draw");
        List<User> users = userDao.getAllUsers();
        ArrayList<Pair<Integer, BigDecimal>> rankList = new ArrayList<>();
        for (User user : users) {
            Integer userId = user.getUserId();
            ArrayList<Consumption> consumptions = consumptionDao.getConsumptionsByUserId(userId);
            BigDecimal sum = BigDecimal.ZERO;
            for (Consumption consumption : consumptions)
                sum = consumption.getConsumptionNumber().add(sum);
            Pair<Integer, BigDecimal> rankItem = Pair.of(userId, sum);
            rankList.add(rankItem);
        }
        rankList.sort(new Comparator<Pair<Integer, BigDecimal>>() {
            @Override
            public int compare(Pair<Integer, BigDecimal> o1, Pair<Integer, BigDecimal> o2) {
                return -o1.getSecond().compareTo(o2.getSecond());
            }
        });
        System.out.println(rankList);
        int totalSize = rankList.size();
        if (totalSize > 10) totalSize = 10;
        ArrayList<Float> data = new ArrayList<>();
        ArrayList<String> alternative = new ArrayList<>();
        ArrayList<String> bottomLabels = new ArrayList<>();
        for (int i = 0; i < totalSize; ++i) {
            data.add(rankList.get(i).getSecond().floatValue());
            alternative.add(i + 1 + "");
            bottomLabels.add(rankList.get(i).getFirst().toString());
        }
        barChartDrawer.drawBarChart(data, alternative, bottomLabels, "用户消费排名", "用户id", "消费金额(元)", Constant.consumptionRankSavePath);
    }
}

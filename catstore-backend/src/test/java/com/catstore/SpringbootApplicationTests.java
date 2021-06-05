package com.catstore;

import com.catstore.utils.Drawer.BarChartDrawer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    BarChartDrawer barChartDrawer;

    @Test
    void contextLoads() throws IOException {
        ArrayList<Float> data = new ArrayList<>();
        data.add(3f);
        data.add(4f);
        ArrayList<String> alternative = new ArrayList<>();
        alternative.add("1");
        alternative.add("2");
        ArrayList<String> bottomLabels = new ArrayList<>();
        bottomLabels.add("1");
        bottomLabels.add("2");
        String title = "test";
        String xLabel = "11";
        String yLabel = "111";
        barChartDrawer.drawBarChart(data, alternative, bottomLabels, title, xLabel, yLabel, "src/main/resources/static/file/1.png");
    }

}

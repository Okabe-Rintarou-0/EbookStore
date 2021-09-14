package com.catstore.utils.drawer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@Component
public class BarChartDrawer {

    private Font uniformFont = new Font("宋体", Font.BOLD, 12);

    private DefaultCategoryDataset formDataset(ArrayList<Float> data, ArrayList<String> alternative, ArrayList<String> bottomLabels) {
        int totalSize = data.size();
        if (totalSize > 5) totalSize = 5;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < totalSize; ++i) {
            dataset.addValue(data.get(i), alternative.get(i), bottomLabels.get(i));
        }
        return dataset;
    }

    private JFreeChart buildChart(DefaultCategoryDataset dataset, String title, String xLabel, String yLabel) {
        JFreeChart chart = ChartFactory.createBarChart("", xLabel, yLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setTitle(new TextTitle(title, new Font("宋体", Font.BOLD, 12)));
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setLabelFont(uniformFont);
        valueAxis.setLowerBound(0.0);
        CategoryAxis categoryAxis = plot.getDomainAxis();//获得横坐标
        categoryAxis.setLabelFont(uniformFont);//设置横坐标字体
        categoryAxis.setTickLabelFont(uniformFont);
        return chart;
    }

    public void drawBarChart(ArrayList<Float> data, ArrayList<String> alternative, ArrayList<String> bottomLabels, String title,
                             String xLabel, String yLabel, String savePath) throws IOException {
        DefaultCategoryDataset dataset = formDataset(data, alternative, bottomLabels);
        JFreeChart chart = buildChart(dataset, title, xLabel, yLabel);
        LegendTitle legend = chart.getLegend(0);//设置Legend
        legend.setItemFont(uniformFont);
        OutputStream os = new FileOutputStream(savePath);//图片是文件格式的，故要用到FileOutputStream用来输出。
        ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
        os.close();//关闭输出流
    }
}

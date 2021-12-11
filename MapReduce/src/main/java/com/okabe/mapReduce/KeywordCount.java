package com.okabe.mapReduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.lib.FieldSelectionMapReduce;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.StringTokenizer;

public class KeywordCount {

    private static final HashSet<String> givenKeywords = new HashSet<>() {{
        add("c++");
        add("java");
        add("go");
        add("programming");
        add("family");
        add("science");
        add("love");
        add("mystery");
        add("history");
    }};

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private final Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String token = itr.nextToken().trim().toLowerCase();
                if (token.startsWith("(") || token.startsWith("\"") || token.startsWith("'"))
                    token = token.substring(1);
                if (token.endsWith(")") || token.endsWith(",") || token.endsWith("\"") || token.endsWith("'"))
                    token = token.substring(0, token.length() - 1);
                if (KeywordCount.givenKeywords.contains(token)) {
                    word.set(token);
                    context.write(word, one);
                }
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private final IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < Objects.requireNonNull(children).length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void main(String[] args) throws Exception {
        String[] inputs = {"src/main/resources/data/CS.txt", "src/main/resources/data/Fiction.txt", "src/main/resources/data/Horror.txt", "src/main/resources/data/Mystery.txt"};
        String output = "src/main/resources/output";

        deleteDir(new File(output));

        Date start = new Date();
        Configuration conf = new Configuration();
        conf.set("dfs.defaultFS", "hdfs://hadoop:9000");
        Job job = Job.getInstance(conf, "keyword count");
        job.setJarByClass(KeywordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        for (String input : inputs) {
            FileInputFormat.addInputPath(job, new Path(input));
        }
//        FileInputFormat.setMaxInputSplitSize(job, 20 * 1024L);
        FileOutputFormat.setOutputPath(job, new Path(output));
        boolean ret = job.waitForCompletion(true);
        Date end = new Date();
        System.out.println("Take " + (double) (end.getTime() - start.getTime()) / 1000 + " s in total.");
        System.exit(ret ? 0 : 1);
    }
}

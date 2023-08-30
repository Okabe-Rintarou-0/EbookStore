package com.catstore.statistic;

//在一些32位的处理器上,如果要求对64位数据的写操作具有原子性,开销会比较大.
//jvm不要求对64位long和double类型变量的写操作具有原子性.
//当jvm在这种处理器上运行时候,可能会把一个64位的long/double类型变量的写操作拆分为两个32位的写操作来执行.
//这两个操作可能会被分配到不同的总线事务中执行,此时对64位变量的写操作不具有原子性.
//1 可见性: 对一个volatile的读,总是能看到任意线程对这个volatile最后的写
//2 原子性: 对任意单个volatile变量的读/写具有原子性,但类似于volatile++这种复合操作不具有原子性.
public class GlobalStatistic {
    public static volatile long dailyVisit = 0L;

    public static synchronized long addVisit() {
        return ++dailyVisit;
    }
}

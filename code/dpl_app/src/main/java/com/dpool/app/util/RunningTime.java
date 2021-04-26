package com.dpool.app.util;


/**
 * 计算运行时间
 */
public class RunningTime {
    private long startTime;
    private long endTime;

    public RunningTime() {
        startTime=System.currentTimeMillis();
        endTime=System.currentTimeMillis();
    }
    public RunningTime reset() {
        startTime=System.currentTimeMillis();
        endTime=System.currentTimeMillis();
        return this;
    }
    public RunningTime end() {
        endTime=System.currentTimeMillis();
        return this;
    }
    /**
     * 
     * @return 耗时 ms
     *author maxinbo
     */
    public long getTakeUpTime() {
        return endTime-startTime;
    }

    @Override
    public String toString() {
        return ((endTime-startTime)/1000.0)+"'s";
    }
}

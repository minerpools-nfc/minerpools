package com.dpool.app.initializer;


import com.dpool.app.Initializer;
import com.dpool.app.cache.RequsetLimit;

/**
 * @Description 读取请求限制频率配置
 * @Author yyj
 * @Date 2021-04-07 14:04
 */
public class RequsetInitialzer implements Initializer {
    @Override
    public void init() {
        //读取限制请求次数配置
        RequsetLimit.init();
    }
}

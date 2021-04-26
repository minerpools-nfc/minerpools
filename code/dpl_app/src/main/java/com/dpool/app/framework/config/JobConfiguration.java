package com.dpool.app.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfiguration {
    @Value("${dpool.test.jobId:}")
    public String testJobid;
}

package com.dpool.app.framework.listenser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.dpool.app.Initializer;
import com.dpool.app.email.EmailSendSrv;
import com.dpool.app.initializer.RequsetInitialzer;
import com.dpool.app.tranfer.DrawCrontabSrv;
import com.dpool.app.tranfer.TransferApplyConfirmSrv;
import com.dpool.app.util.AgentSvcTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CollectdStarterApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
	private List<AgentSvcTask> svcs = new ArrayList<>();
	private List<Initializer> initializers = new ArrayList<>();
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            initServices();
            startServices();
        } catch (Exception e) {
            log.warn("onapplication exception. " + e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void initServices() throws Exception {
       initializers.add(new RequsetInitialzer());

       svcs.add(EmailSendSrv.getInstance());
       svcs.add(new CronbTimer());
       svcs.add(DrawCrontabSrv.getInstance());
       svcs.add(new TransferApplyConfirmSrv());
    }

    private void startServices() {
        for (Initializer initializer : initializers) {
            initializer.init();
        }
        for (AgentSvcTask svcTask : svcs) {
            svcTask.init_svc();
        }
        for (AgentSvcTask svcTask : svcs) {
            svcTask.start();
        }
    }

}


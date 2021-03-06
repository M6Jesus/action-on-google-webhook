package org.norsys.pamela.webhook;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

@SpringBootApplication
@EnableJpaRepositories("org.norsys.pamela.webhook.persistance")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class GoogleWebhookApplicationRunner extends AsyncConfigurerSupport{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(GoogleWebhookApplicationRunner.class, args);
	}
	
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("MsgReqService-");
        executor.initialize();
        return executor;
    }
	

}

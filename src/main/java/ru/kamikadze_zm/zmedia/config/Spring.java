package ru.kamikadze_zm.zmedia.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@PropertySource("/WEB-INF/app.properties")
@ComponentScan(basePackages = {
    "ru.kamikadze_zm.zmedia.repository",
    "ru.kamikadze_zm.zmedia.service",
    "ru.kamikadze_zm.zmedia.util"})
@Import(value = {Data.class, Security.class, Firebase.class})
@EnableAsync
public class Spring {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(50);
        executor.initialize();
        return executor;
    }
}

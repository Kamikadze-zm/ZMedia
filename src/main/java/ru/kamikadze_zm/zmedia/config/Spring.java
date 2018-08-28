package ru.kamikadze_zm.zmedia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/WEB-INF/app.properties")
@ComponentScan(basePackages = {
    "ru.kamikadze_zm.zmedia.repository",
    "ru.kamikadze_zm.zmedia.service",
    "ru.kamikadze_zm.zmedia.util"})
@Import(value = {Data.class, Security.class})
public class Spring {

}

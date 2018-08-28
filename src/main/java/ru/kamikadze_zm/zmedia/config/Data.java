package ru.kamikadze_zm.zmedia.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.kamikadze_zm.zmedia.repository")
public class Data {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndi = new JndiDataSourceLookup();
        return jndi.getDataSource(env.getProperty("jndi.poolName"));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ru.kamikadze_zm.zmedia.model.entity");
        emf.setJpaProperties(jpaProperties());
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(@Autowired EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    private Properties jpaProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.show_sql", "false");
        p.setProperty("hibernate.dialect", env.getProperty("jdbc.dialect"));
        p.setProperty("hibernate.connection.charSet", "UTF-8");
        return p;
    }
}

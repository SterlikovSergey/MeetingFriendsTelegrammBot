package by.st.meetingwithfriendsbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.Properties;

public class DatabaseConfig {
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        Properties hibernateProperties = new Properties();
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}

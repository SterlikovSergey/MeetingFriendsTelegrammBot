package by.st.meetingwithfriendsbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
@Configuration
public class DatabaseConfig {
        @Bean
        public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
            LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
            em.setDataSource(dataSource);
            em.setJpaVendorAdapter(jpaVendorAdapter);
            em.setPackagesToScan("by.st.meetingwithfriendsbot.model", "another.package.to.scan");
            return em;
        }
    }


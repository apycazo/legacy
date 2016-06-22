package com.github.apycazo.hi5.optional.h2db;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Andres Picazo
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = H2DBConfig.ACTIVATION_PROPERTY, havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(
        entityManagerFactoryRef = "h2dbEntityManagerFactory",
        transactionManagerRef = "h2dbTransactionManager"
)
public class H2DBConfig {

    public H2DBConfig() {
        log.info("-- Loaded {}", getClass().getSimpleName());
    }

    public static final String ACTIVATION_PROPERTY = "hi5.optional.h2db";

    @Value("${hi5.optional.h2db.embedded:false}")
    protected boolean IS_EMBEDDED;

    @Value("${hi5.optional.h2db.generateDdl:true}")
    protected boolean GENERATE_DDL;

    @Value("${hi5.optional.h2db.url:jdbc:h2:file:./hi5-h2db}")
    protected String DB_URL;

    // The following is to allow this repository to coexist with another database.

    @Bean
    PlatformTransactionManager h2dbTransactionManager() {
        return new JpaTransactionManager(h2dbEntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean h2dbEntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(GENERATE_DDL);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(h2dbDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(H2DBConfig.class.getPackage().getName());

        return factoryBean;
    }

    @Bean
    DataSource h2dbDataSource() {

        if (IS_EMBEDDED) {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
        else {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setUrl(DB_URL);

            return ds;
        }
    }
}

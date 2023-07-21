package io.codelex.flightplanner;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name = "datasource", havingValue = "postgres-database")
    public DataSource getDatabaseDatasource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/postgres")
                .username("postgres")
                .password("postgres")
                .build();
    }
}

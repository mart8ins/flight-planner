package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.admin.service.AdminFlightService;
import io.codelex.flightplanner.flights.admin.service.AdminFlightsServiceInMemory;
import io.codelex.flightplanner.flights.admin.service.AdminFlightsServicePostgresDB;
import io.codelex.flightplanner.flights.admin.service.AdminValidationsService;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsService;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsServiceInMemory;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsServicePostgresDB;
import io.codelex.flightplanner.flights.repository.databasePostgres.AirportsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.testing.service.TestingServiceInMemory;
import io.codelex.flightplanner.flights.testing.service.TestingServicePostgresDB;
import io.codelex.flightplanner.flights.testing.service.TestingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    // ADMIN
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public AdminFlightService getAdminServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory){
        return new AdminFlightsServiceInMemory(flightsRepositoryInMemory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public AdminFlightService getAdminServicePostgres(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB, AdminValidationsService adminValidationsService){
        return new AdminFlightsServicePostgresDB(flightsRepositoryPostgresDB, airportsRepositoryPostgresDB, adminValidationsService);
    }

    // CUSTOMER
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public CustomerFlightsService getCustomerServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory){
        return new CustomerFlightsServiceInMemory(flightsRepositoryInMemory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public CustomerFlightsService getCustomerServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB){
        return new CustomerFlightsServicePostgresDB(flightsRepositoryPostgresDB);
    }

    // TESTING SERVICE
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public TestingService getTestingServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory){
        return new TestingServiceInMemory(flightsRepositoryInMemory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public TestingService getTestingServicePostgresDatabase(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB){
        return new TestingServicePostgresDB(flightsRepositoryPostgresDB, airportsRepositoryPostgresDB);
    }

}

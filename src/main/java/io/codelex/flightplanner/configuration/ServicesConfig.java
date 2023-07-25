package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.flights.admin.service.AdminService;
import io.codelex.flightplanner.flights.admin.service.AdminServiceMemory;
import io.codelex.flightplanner.flights.admin.service.AdminServiceDatabase;
import io.codelex.flightplanner.flights.admin.validation.FlightReqValidationService;
import io.codelex.flightplanner.flights.customer.service.CustomerService;
import io.codelex.flightplanner.flights.customer.service.CustomerServiceMemory;
import io.codelex.flightplanner.flights.customer.service.CustomerServiceDatabase;
import io.codelex.flightplanner.flights.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
import io.codelex.flightplanner.flights.repository.database.FlightsRepositoryDatabase;
import io.codelex.flightplanner.flights.testing.service.TestingServiceMemory;
import io.codelex.flightplanner.flights.testing.service.TestingServiceDatabase;
import io.codelex.flightplanner.flights.testing.service.TestingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServicesConfig {

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public AdminService getAdminServiceInMemory(FlightsRepositoryMemory flightsRepositoryMemory){
        return new AdminServiceMemory(flightsRepositoryMemory);
    }
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public AdminService getAdminServicePostgres(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase, FlightReqValidationService flightReqValidationService){
        return new AdminServiceDatabase(flightsRepositoryDatabase, airportsRepositoryDatabase, flightReqValidationService);
    }
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public CustomerService getCustomerServiceInMemory(FlightsRepositoryMemory flightsRepositoryMemory){
        return new CustomerServiceMemory(flightsRepositoryMemory);
    }
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public CustomerService getCustomerServicePostgresDB(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase){
        return new CustomerServiceDatabase(flightsRepositoryDatabase, airportsRepositoryDatabase);
    }
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public TestingService getTestingServiceInMemory(FlightsRepositoryMemory flightsRepositoryMemory){
        return new TestingServiceMemory(flightsRepositoryMemory);
    }
    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "postgres-database")
    public TestingService getTestingServicePostgresDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase){
        return new TestingServiceDatabase(flightsRepositoryDatabase, airportsRepositoryDatabase);
    }
}

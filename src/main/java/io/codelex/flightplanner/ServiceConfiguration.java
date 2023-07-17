package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.admin.service.AdminFlightService;
import io.codelex.flightplanner.flights.admin.service.InMemoryAdminFlightsService;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsService;
import io.codelex.flightplanner.flights.customer.service.InMemoryCustomerFlightsService;
import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import io.codelex.flightplanner.flights.testing.service.InMemoryTestingService;
import io.codelex.flightplanner.flights.testing.service.TestingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public AdminFlightService getAdminServiceInMemory(InMemoryFlightsRepository inMemoryFlightsRepository){
        return new InMemoryAdminFlightsService(inMemoryFlightsRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public CustomerFlightsService getCustomerServiceInMemory(InMemoryFlightsRepository inMemoryFlightsRepository){
        return new InMemoryCustomerFlightsService(inMemoryFlightsRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "flightsPlanner", name="datasource", havingValue = "in-memory")
    public TestingService getTestingServiceInMemory(InMemoryFlightsRepository inMemoryFlightsRepository){
        return new InMemoryTestingService(inMemoryFlightsRepository);
    }

}

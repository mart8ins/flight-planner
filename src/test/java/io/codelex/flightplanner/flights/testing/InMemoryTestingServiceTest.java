package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import io.codelex.flightplanner.flights.testing.service.InMemoryTestingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InMemoryTestingServiceTest {

    @Mock
    InMemoryFlightsRepository inMemoryFlightsRepository;

    @InjectMocks
    InMemoryTestingService inMemoryTestingService;

    @Test
    void clearDatabase() {
        inMemoryTestingService.clearDatabase();
        Mockito.verify(inMemoryFlightsRepository).clearDatabase();
    }
}
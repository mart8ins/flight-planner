package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.testing.service.TestingServiceInMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestingServiceInMemoryTest {

    @Mock
    FlightsRepositoryInMemory flightsRepositoryInMemory;

    @InjectMocks
    TestingServiceInMemory testingServiceInmemory;

    @Test
    void clearDatabase() {
        testingServiceInmemory.clearDatabase();
        Mockito.verify(flightsRepositoryInMemory).clearDatabase();
    }
}
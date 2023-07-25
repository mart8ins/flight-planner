package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
import io.codelex.flightplanner.flights.testing.service.TestingServiceMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestingServiceMemoryTest {

    @Mock
    FlightsRepositoryMemory flightsRepositoryMemory;

    @InjectMocks
    TestingServiceMemory testingServiceInmemory;

    @Test
    void clearDatabase() {
        testingServiceInmemory.clearDatabase();
        Mockito.verify(flightsRepositoryMemory).clearDatabase();
    }
}
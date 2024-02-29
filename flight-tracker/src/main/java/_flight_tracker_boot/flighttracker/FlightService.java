package _flight_tracker_boot.flighttracker;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<List<Flight>> getFlightBySourceDestination(String source, String destination) {
        Query query = new Query();
        query.addCriteria(Criteria.where("source").is(source).and("destination").is(destination));
        List<Flight> flights = mongoTemplate.find(query, Flight.class);
        return Optional.ofNullable(flights);
    }
}

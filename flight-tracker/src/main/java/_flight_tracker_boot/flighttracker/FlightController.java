package _flight_tracker_boot.flighttracker;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
@RequestMapping("/api/flights")

public class FlightController {

    @Autowired
    private FlightService flightService;

    // Returns All Flights
    @GetMapping()
    public ResponseEntity<List<Flight>> allFlights() {
        return new ResponseEntity<List<Flight>>(flightService.getAllFlights(), HttpStatus.OK);
    }

    @GetMapping("/{source}&{destination}")
    public ResponseEntity<Optional<List<Flight>>> getSourceDestinationFlights(@PathVariable String source, @PathVariable String destination) {
        return new ResponseEntity<Optional<List<Flight>>>(flightService.getFlightBySourceDestination(source, destination), HttpStatus.OK);
    }
}

package _flight_tracker_boot.flighttracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@RestController
public class FlightTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightTrackerApplication.class, args);
	}

	@GetMapping("/")
	public String Root() {
		return "Hello World";
	}

}
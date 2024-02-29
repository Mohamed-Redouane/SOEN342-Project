package _flight_tracker_boot.flighttracker;

import java.util.*;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<User>(userService.createUser(payload.get("name"), payload.get("accountType"),
                payload.get("email"), payload.get("password")), HttpStatus.CREATED);
    }

    @GetMapping("/{email}&{password}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String email, @PathVariable String password) {
        return new ResponseEntity<Optional<User>>(userService.getUser(email, password), HttpStatus.OK);
    }
}

package _flight_tracker_boot.flighttracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public User createUser(String name, String accountType, String email, String password) {
        User user = userRepository.insert(new User(name, accountType, email, password));

        return user;
    }

    public Optional<User> getUser(String email, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email).and("password").is(password));
        User user = mongoTemplate.findOne(query, User.class);
        return Optional.ofNullable(user);
    }
}

package _flight_tracker_boot.flighttracker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId user_id;
    protected String name;
    protected String accountType;
    protected String email;
    private String password;

    public User(String name, String accountType, String email, String password) {
        this.name = name;
        this.accountType = accountType;
        this.email = email;
        this.password = password;
    }
}

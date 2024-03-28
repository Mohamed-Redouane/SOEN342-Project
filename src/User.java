public class User {
    private long user_id;
    protected String name;
    protected String accountType;
    protected String email;
    private String password;

    public User() {}
    public User(long user_id, String name, String accountType, String email, String password) {
        this.user_id = user_id;
        this.name = name;
        this.accountType = accountType;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return this.user_id;
    }
    public String getName() {
        return this.name;
    }
    public String getAccountType() {
        return this.accountType;
    }
    public String getEmail() {
        return this.email;
    }
    private String getPassword() {
        return this.password;
    }
}

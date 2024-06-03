package connection;

public class Credentials {
    private String url;
    private String username;
    private String password;

    public Credentials() {
        this.url = "jdbc:mysql://localhost:3306/officed1";
        this.username = "root";
        this.password = "MAVERIK27";
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

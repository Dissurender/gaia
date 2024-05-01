package dsd.cohort.application.user;

public class UserDataRequestDTO {

    private String email;
    private String id;

    public UserDataRequestDTO() {

    }

    public UserDataRequestDTO(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
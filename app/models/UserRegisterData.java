package models;

import service.DatabaseLayer;

/**
 * Created by Virgil Barcan on 27.05.2015.
 * This class will hold the data taken from the Login or Register form
 */
public class UserRegisterData {

    private String email;
    private String username;
    private String password;

    private int external;

    public UserRegisterData(){
        email = "";
        username = "";
        password = "";
    }

    public UserRegisterData(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExternal() {
        return external;
    }

    public void setExternal(int external) {
        this.external = external;
    }

    /**
     * This function is used to check if the data given by the user is valid:
     * - username/email exists in the DB
     * - correct password for the username/email
     * @return 0 = valid, 1 = invalid username, 2 = invalid email
     */
    public int isValid() {
        int isValid = 1;

        if (this.external == 0) {
            // check if the user is a valid one (if it exists in the DB)
            isValid = DatabaseLayer.isValidRegister(this);
        }
        else {
            // check if the user is a valid one (if the email or username exist in the DB)
            isValid = DatabaseLayer.isValidExternal(this);
        }
        return isValid;
    }

    @Override
    public String toString() {
        return "UserLoginRegisterData{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

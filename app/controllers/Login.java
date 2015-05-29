package controllers;

import models.UserLoginData;
import play.mvc.*;
import views.html.*;

import java.util.Map;

/**
 * Created by Virgil Barcan on 27.05.2015.
 *
 * This class is used to handle the login and register part
 */
public class Login extends Controller {

    private static String usernameEmail;

    /**
     * This method is used to render the login page
     * @return
     */
    public static Result loginRegister() {
        return ok(login.render());
    }

    public static Result login() {
        // Get the data from the form
        UserLoginData userLoginData = new UserLoginData();
        String email = "";
        String username = "";
        String password = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("email-login-input"))
            email = request.get("email-login-input")[0];
        if (request.containsKey("username-login"))
            username = request.get("username-login")[0];
        if (request.containsKey("password-login"))
            password = request.get("password-login")[0];

        userLoginData.setEmail(email);
        userLoginData.setUsername(username);
        userLoginData.setPassword(password);

        System.out.println(userLoginData.toString());

        usernameEmail = (email != "") ? email : username;

        //if (userLoginData.isValid()) {
            return redirect(controllers.routes.Home.index());
        //}
        //else{
        //    return redirect(controllers.routes.Login.retryLogin());
        //}
    }

    // This will implement AJAX, to update the page without reloading it
    public static Result retryLogin() {

        return ok();
    }


    public static String getUsernameEmail() {
        return usernameEmail;
    }
}

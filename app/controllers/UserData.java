package controllers;

import models.*;
import play.mvc.*;
import service.DatabaseLayer;
import views.html.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Virgil Barcan on 29.05.2015.
 */
public class UserData extends Controller {

    private static ArrayList<Address> addresses;

    public static Result viewUserData() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "hidden";
        String visibleView = "visible";

        UserInfo userInfo;
        userInfo = getAllUserInfoFromDB();

        System.out.println("viewEditData> userInfo: " + userInfo.toString());

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result editUserData() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = null;
        userInfo = getAllUserInfoFromDB();

        System.out.println("userEditData> userInfo: " + userInfo.toString());

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result retryEditUserData() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        session().clear();

        return ok(userData.render(visibleEdit, visibleView, null));
    }

    public static Result editUserInfo() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        UserInfo userInfo = new UserInfo();
        String firstName = "";
        String lastName = "";
        String birthdate = "";
        String gender = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("input-first-name"))
            firstName = request.get("input-first-name")[0];
        if (request.containsKey("input-last-name"))
            lastName = request.get("input-last-name")[0];
        if (request.containsKey("input-birthdate"))
            birthdate = request.get("input-birthdate")[0];
        if (request.containsKey("radio-gender"))
            gender = request.get("radio-gender")[0];

        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setBirthdate(birthdate);
        userInfo.setGender(gender);

        // Add the received data to the session
        addToSession(userInfo);

        // Add to the database the information given by the user
        boolean addToDB = addUserInfoToDB(userInfo);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithUserInfo(userInfo);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserHometown() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        Address userHometown = new Address();
        String country = "";
        String state = "";
        String county = "";
        String locality = "";
        String streetName = "";
        String streetNumber = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        System.out.println("request in hometown: " + request);

        if (request.containsKey("input-hometown-country"))
            country = request.get("input-hometown-country")[0];
        if (request.containsKey("input-hometown-state"))
            state = request.get("input-hometown-state")[0];
        if (request.containsKey("input-hometown-county"))
            county = request.get("input-hometown-county")[0];
        if (request.containsKey("input-hometown-locality"))
            locality = request.get("input-hometown-locality")[0];
        if (request.containsKey("input-hometown-street-name"))
            streetName = request.get("input-hometown-street-name")[0];
        if (request.containsKey("input-hometown-street-number"))
            streetNumber = request.get("input-hometown-street-number")[0];

        userHometown.setCountry(country);
        userHometown.setState(state);
        userHometown.setCounty(county);
        userHometown.setLocality(locality);
        userHometown.setStreetName(streetName);
        userHometown.setStreetNumber(streetNumber);

        // Add the received data to the session
        //addUserHometownToSession(userHometown);

        // Add to the database the information given by the user
        boolean addToDB = addUserHometownToDB(userHometown);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithHometown(userHometown);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserCurrentAddress() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        Address currentAddress = new Address();
        String country = "";
        String state = "";
        String county = "";
        String locality = "";
        String streetName = "";
        String streetNumber = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        System.out.println("request in current: " + request);

        if (request.containsKey("input-current-country"))
            country = request.get("input-current-country")[0];
        if (request.containsKey("input-current-state"))
            state = request.get("input-current-state")[0];
        if (request.containsKey("input-current-county"))
            county = request.get("input-current-county")[0];
        if (request.containsKey("input-current-locality"))
            locality = request.get("input-current-locality")[0];
        if (request.containsKey("input-current-street-name"))
            streetName = request.get("input-current-street-name")[0];
        if (request.containsKey("input-current-street-number"))
            streetNumber = request.get("input-current-street-number")[0];

        currentAddress.setCountry(country);
        currentAddress.setState(state);
        currentAddress.setCounty(county);
        currentAddress.setLocality(locality);
        currentAddress.setStreetName(streetName);
        currentAddress.setStreetNumber(streetNumber);

        // Add the received data to the session
        //addUserHometownToSession(userHometown);

        // Add to the database the information given by the user
        boolean addToDB = addUserCurrentAddressToDB(currentAddress);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return editUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithCurrentAddress(currentAddress);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserAirlinePreferences() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        AirlinePreferences userAirlinePreferences= new AirlinePreferences();

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        // Add to the database the information given by the user
        boolean addToDB = false;
        //TODO addToDB = addUserAirlinePreferencesToDB(userAirlinePreferences);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            //return showWithAirlinePreferences(userAirlinePreferences);
            return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserFlightPreferences() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        FlightPreferences userFlightPreferences  = new FlightPreferences();

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        // Flight Preferences
        String nightFlight = null;
        String stopowerFlight = null;
        if (request.containsKey("radio-night-flight"))
            nightFlight = request.get("radio-night-flight")[0];
        if (request.containsKey("radio-stopover-flight"))
            stopowerFlight = request.get("radio-stopover-flight")[0];

        userFlightPreferences.setNightFlight(nightFlight);
        userFlightPreferences.setStopowersFlight(stopowerFlight);

        //!!! As a TRICK, we could add this data to the session, to have it in a somewhat persistent state
        // Add to the database the information given by the user
        boolean addToDB = false;
        //TODO addToDB = addUserFlightPreferencesToDB(userFlightPreferences);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithFlightPreferences(userFlightPreferences);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserRoutePreferences() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        RoutePreferences userRoutePreferences = new RoutePreferences();

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        // Route Preferences
        String cheapestRoute = null;
        String shortestRoute = null;
        String mostFriendsSeenRoute = null;
        if (request.containsKey("radio-cheapest-route"))
            cheapestRoute = request.get("radio-cheapest-route")[0];
        if (request.containsKey("radio-shortest-route"))
            shortestRoute = request.get("radio-shortest-route")[0];
        if (request.containsKey("radio-most-friends-route"))
            mostFriendsSeenRoute = request.get("radio-most-friends-route")[0];

        userRoutePreferences.setCheapestRoute(cheapestRoute);
        userRoutePreferences.setShortestRoute(shortestRoute);
        userRoutePreferences.setMostFriendsSeenRoute(mostFriendsSeenRoute);

        //!!! As a TRICK, we could add this data to the session, to have it in a somewhat persistent state
        // Add to the database the information given by the user
        boolean addToDB = false;
        //TODO addToDB = addUserRoutePreferencesToDB(userFlightPreferences);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithRoutePreferences(userRoutePreferences);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }


    private static Result showWithUserInfo(UserInfo userInfo) {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    /**
     * This page is used to reload the edit user data page with the hometown added
     * @param userHometown
     * @return
     */
    private static Result showWithHometown(Address userHometown) {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = getUserInfoFromDB();
        userInfo.setHometown(userHometown);

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }


    private static Result showWithCurrentAddress(Address currentAddress) {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = getUserInfoFromDB();
        userInfo.setCurrentAddress(currentAddress);

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result showWithFlightPreferences(FlightPreferences flightPreferences) {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = getUserInfoFromDB();
        // TODO userInfo.setFlightPreferences(flightPreferences);

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result showWithRoutePreferences(RoutePreferences routePreferences) {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = getUserInfoFromDB();
        // TODO userInfo.setRoutePreferences(route0Preferences);

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    /**
     * This method is used to add the user data to the session, for fast access
     * @param userInfo user data
     */
    private static void addToSession(UserInfo userInfo) {
        session("userFirstName", userInfo.getFirstName());
        session("userLastName", userInfo.getLastName());
        session("userBirthdate", userInfo.getBirthdate());
        session("userGender", userInfo.getGender());
    }

    private static void addUserHometownToSession(Address userHometown) {
        session("userHometownCountry", userHometown.getCountry());
        session("userHometownState", userHometown.getState());
        session("userHometownCounty", userHometown.getCounty());
        session("userHometownLocality", userHometown.getLocality());
        session("userHometownStreetName", userHometown.getStreetName());
        session("userHometownStreetNumber", userHometown.getStreetNumber());
    }

    public static UserInfo getUserInfoFromSession() {
        String firstName = session("userFirstName");
        String lastName = session("userLastName");
        String birthdate = session("userBirthdate");
        String gender = session("userGender");

        UserInfo userInfo = null;

        if (firstName != null && lastName != null && birthdate != null && gender != null) {
            userInfo = new UserInfo();
            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfo.setBirthdate(birthdate);
            userInfo.setGender(gender);
        }

        return userInfo;
    }

    /**
     * This method is used to add the user info to the DB
     * @param userInfo the userInfo (firstName, lastName, birthdate, gender)
     * @return true if the DB was successfully updated, false otherwise
     */
    private static boolean addUserInfoToDB(UserInfo userInfo) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = (email != null ? email : username);

        // add user info to the DB
        result = DatabaseLayer.addUserInfoToDB(userInfo, userIdentifier);

        return result;
    }


    private static boolean addUserHometownToDB(Address userHometown) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        // add user info to the DB
        result = DatabaseLayer.addUserHometownToDB(userHometown, userIdentifier);

        return result;
    }

    private static boolean addUserCurrentAddressToDB(Address userHometown) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        // add user info to the DB
        result = DatabaseLayer.addUserCurrentAddressToDB(userHometown, userIdentifier);

        return result;
    }

    public static UserInfo getUserInfoFromDB() {
        UserInfo userInfo = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        if (email != null && username != null) {

            String userIdentifier = email;
            if (email.equals("")) userIdentifier = username;

            //get user info from the DB
            userInfo = DatabaseLayer.getUserInfoFromDB(userIdentifier);
        }

        return userInfo;
    }

    private static Address getUserHometownFromDB() {
        Address userHometown = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        //get user info from the DB
        userHometown = DatabaseLayer.getUserHometownFromDB(userIdentifier);

        return userHometown;
    }

    private static Address getUserCurrentAddressFromDB() {
        Address userCurrentAddress = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        //get user info from the DB
        userCurrentAddress = DatabaseLayer.getUserCurrentAddressFromDB(userIdentifier);

        return userCurrentAddress;
    }

    private static AirlinePreferences getUserAirlinePreferencesFromDB() {
        AirlinePreferences userAirlinePreferences = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;

        if (email.equals("")) userIdentifier = username;

        //get the airline preferences from the DB
        userAirlinePreferences = DatabaseLayer.getUserAirlinePreferencesFromDB(userIdentifier);

        return userAirlinePreferences;
    }

    private static FlightPreferences getUserFlightPreferencesFromDB() {
        FlightPreferences userFlightPreferences = new FlightPreferences();

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;

        if (email.equals("")) userIdentifier = username;

        //get the flight preferences from the DB
        userFlightPreferences = DatabaseLayer.getUserFlightPreferencesFromDB(userIdentifier);

        return userFlightPreferences;
    }

    private static RoutePreferences getUserRoutePreferencesFromDB() {
        RoutePreferences userRoutePreferences = new RoutePreferences();

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;

        if (email.equals("")) userIdentifier = username;

        //get the route preferences from the DB
        userRoutePreferences = DatabaseLayer.getUserRoutePreferencesFromDB(userIdentifier);

        return userRoutePreferences;
    }

    private static UserInfo getAllUserInfoFromDB() {
        UserInfo userInfo = null;
        Address userHometown = null;
        Address userCurrentAddress = null;
        FlightPreferences userFlightPreferences = null;
        RoutePreferences userRoutePreferences = null;

        //look for more info about the user in the DB
        userInfo = getUserInfoFromDB();

        //get user's hometown
        userHometown = getUserHometownFromDB();
        userInfo.setHometown(userHometown);

        //get user's current address
        userCurrentAddress = getUserCurrentAddressFromDB();
        userInfo.setCurrentAddress(userCurrentAddress);

        //get user's flight preferences
        //userFlightPreferences = getUserFlightPreferencesFromDB();
        //userInfo.setFlightPreferences(userFlightPreferences);

        //get user's route preferences
        //userRoutePreferences = getUserRoutePreferencesFromDB();
        //userInfo.setRoutePreferences(userRoutePreferences);

        return userInfo;
    }

    public static Result searchAddresses() {
        addresses = new ArrayList<Address>();

        addresses = DatabaseLayer.getAddressesRecommendations();

        return searchAddressesResults();
    }

    public static Result searchAddressesResults() {

        String recommendationsStringHTML = createAddressDatalist(addresses);

        System.out.println(recommendationsStringHTML);

        return ok(recommendationsStringHTML);
    }

    private static String createAddressDatalist(ArrayList<Address> addresses) {
        String result = "";
        int n = addresses.size();

        if (n > 1) {
            result = "[{";

            for (int i = 0; i < n - 1; ++i) {
                result += addresses.get(i).toJSON() + "}, ";
            }
            result += "{" + addresses.get(n - 1).toJSON() + "}]";
        }
        else {
            if (n == 1) {
                result = "[{" + addresses.get(0).toJSON() + "}]";
            }
            else {
                result = "[{" + new Address().toJSON() + "}]";
            }
        }
        System.out.println("result: " + result);

        return result;
    }

    public static UserLoginData getUserCredentialsFromSession() {
        String email = session("email");
        String username = session("username");
        String password = session("password");

        UserLoginData userCredentials = new UserLoginData(email,username,password);

        System.out.println("getUserCredentialsFromSession> " + userCredentials);

        return userCredentials;
    }

}

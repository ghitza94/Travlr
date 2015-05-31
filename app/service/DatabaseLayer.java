package service;

/**
 * Created by Virgil Barcan on 30.05.2015.
 */

import models.UserInfo;
import models.UserLoginData;
import models.UserRegisterData;
import play.db.DB;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class is used to handle all the database work required by other classes (by the controllers, for example)
 */
public class DatabaseLayer {

    /**
     * This function is used to check if the data given by the user is valid:
     * - username/email exists in the DB
     * - correct password for the username/email
     * @param userLoginData the user login data (username, email, password)
     * @return 0 = valid, 1 = invalid
     */
    public static int isValidLogin(UserLoginData userLoginData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userLoginData.getEmail();
        String username = userLoginData.getUsername();
        String password = userLoginData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        // Send an request to the DB to find if the user exists
        // If the user is valid, 0 is returned
        // If the user is invalid, 1 is returned
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.IS_VALID_USER(?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, usernameEmail);
            statement.setString(3, password);
            statement.execute();

            // if the user exists and the password is correct, result will be 0, otherwise 1
            result = statement.getInt(1);

            System.out.println("isValidLogin result: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static int isValidExternal(UserLoginData userLoginData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userLoginData.getEmail();
        String username = userLoginData.getUsername();
        String password = userLoginData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        // Send an request to the DB to find if the user exists
        // If the user is valid, 0 is returned
        // If the user is invalid, 1 is returned
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.IS_VALID_USER_EXTERNAL(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, usernameEmail);
            statement.execute();

            // if the user exists and the password is correct, result will be 0, otherwise 1
            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * This function is used to check if the data given by the user is valid:
     * - the email isn't already used in the DB
     * - the username isn't already usen in the DB
     * @param userRegisterData the user register data (email, username, password)
     * @return 0 = valid, 1 = invalid username, 2 = invalid email
     */
    public static int isValidRegister(UserRegisterData userRegisterData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userRegisterData.getEmail();
        String username = userRegisterData.getUsername();
        String password = userRegisterData.getPassword();


         // Send an request to the DB to find if the user exists
         // NOT TESTED!!!
         String sqlQuery = "{? = call TRAVLR.IS_VALID_USER(?, ?, ?)}";
         Connection connection = DB.getConnection();
         CallableStatement statement = null;
         try {
             statement = connection.prepareCall(sqlQuery);
             statement.registerOutParameter(1, Types.INTEGER);
             statement.setString(2, email);
             statement.setString(3, username);
             statement.setString(4, password);
             statement.execute();

             // if the user exists and the password is correct, result will be 0, otherwise 1 or 2
             result = statement.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }

        return result;
    }

    /**
     * This function is used to insert a new user into the DB
     * @param userRegisterData the user register data (email, username, password)
     * @return 0 if the user was inserted successfully, 1 if the user was not inserted successfully, 2 if the user was already created
     */
    public static boolean addToDB(UserRegisterData userRegisterData) {
        int result = -1;

        String email = userRegisterData.getEmail();
        String username = userRegisterData.getUsername();
        String password = userRegisterData.getPassword();

        // Add the new user (if possible, that is, if the email or username aren't already in use)
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.ADD_USER(?, ?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.execute();

            // if the user was inserted successfully, 0 is returned
            // if the user was not inserted successfully, 1 is returned
            // if the user was already created, 2 is returned
            result = statement.getInt(1);
            System.out.println("result of execution: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result == 0;
    }

    /**
     * This function is used to insert/update the user info
     * @param userInfo the user info (firstName, lastName, birthdate, gender)
     * @return
     */
    public static boolean addToDB(UserInfo userInfo, String userIdentifier) {
        int result = -1;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String firstName = userInfo.getFirstName();
        String lastName = userInfo.getLastName();
        String birthdate = userInfo.getBirthdate();
        String gender = userInfo.getGender();

        System.out.println("addToDB: " + userInfo + " userIdentifier: " + userIdentifier);

        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
         String sqlQuery = "{? = call TRAVLR.ADD_USER_INFO(?, ?, ?, ?, ?)}";
         Connection connection = DB.getConnection();
         CallableStatement statement = null;
         try {
             statement = connection.prepareCall(sqlQuery);
             statement.registerOutParameter(1, Types.INTEGER);
             statement.setString(2, firstName);
             statement.setString(3, lastName);
             statement.setString(4, birthdate);
             statement.setString(5, gender);
             statement.setString(6, userIdentifier);
             statement.execute();

             result = statement.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }

        return result == 0;
    }

    /*
    public static boolean addToDB(Address userHometown) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_HOMETOWN(?, ?, ?, ?, ?)}";
    }

    public static boolean addToDB(Address userCurrentAddress) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_CURRENT_ADDRESS(?, ?, ?, ?, ?)}";
    }

    public static boolean addToDB(Address newVisitedPlace) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_VISITED_PLACE(?, ?, ?, ?, ?)}";
    }

    public static boolean addToDB(Preference newAirlinePreference) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_AIRLINE(?, ?, ?, ?, ?)}";
    }

    public static boolean addToDB(Preference flightPreferences) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_FLIGHT_PREFERENCES(?, ?, ?, ?, ?)}";
    }

    public static boolean addToDB(Preference routePreferences) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_ROUTE_PREFERENCES(?, ?, ?, ?, ?)}";
    }
    */

    public static boolean isTable(String tableName){
    	try {
        	String query = "? = Call TRAVLR.ADD_USER(?)";
        	Connection con = DB.getConnection();
			CallableStatement stmt = con.prepareCall(query);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.setString(2, tableName);
			stmt.execute();
			return stmt.getInt(1)>=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    public static boolean importSql(String username, String password, String path){
    	String env=null;
    	if (System.getProperty("os.name").toLowerCase().contains("windows")){
    		env = "cmd /c";
    	}
    	if (System.getProperty("os.name").toLowerCase().contains("linux")){
    		env = "sh -c";
    	}
    	
    	String cmd = "%s echo exit | sqlplus %s/%s @%s";
    	cmd = String.format(cmd, env, username, password, path);
    	
    	try{
    		Runtime rt = Runtime.getRuntime();
    		Process p = rt.exec(cmd);
    		p.waitFor();
    		return true;
    	} catch (InterruptedException e){
			e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
    }
}

package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.text.WordUtils;

public class Application extends Controller {
	//Application is run only once so the values remain the same
	//private static String controller = "home";
	//private static String method = "index";
	private final static String defaultControllerName = "controllers.Login";
	private final static String defaultMethodName = "loginRegister";
	
    private static String sanitizeUrl(String url){
        //$-_.+!*'(),{}|\\^~[]`"><#%;/?:@&=
    	StringBuilder sanitizedUrl = new StringBuilder();
    	
    	for (int i=0; i<url.length(); ++i){    		
    		char c = url.charAt(i);
    		
    		if (Character.isLetterOrDigit(c)){
    			sanitizedUrl.append(c);
    			continue;
    		}
    		if ("$-_.+!*'(),{}|\\^~[]`\"><#%;/?:@&=".indexOf(c)!=-1){
    			sanitizedUrl.append(c);
    			continue;
    		}
    	}
        return sanitizedUrl.toString();
    }
    
    private static ArrayList<String> parseUrl(String url){
    	String tokens[];
    	
    	url = sanitizeUrl(url);
    	if (url.contains("/")){
	    	tokens = url.split("/");
	    	return new ArrayList<String>(Arrays.asList(tokens));
    	}
    	else
    		return new ArrayList<String>();
    }


    public static Result index(String url) {
    	//return ok(debug.render(oracle.jdbc.OracleDriver.getDriverVersion()));
    	String controllerName = defaultControllerName;
    	String methodName = defaultMethodName;
    	ArrayList<String> urlTokens = parseUrl(url);
        
        
        String cwd = System.getProperty("user.dir");
        File appDir = new File(cwd, "app");
        File controllersDir = new File(appDir, "controllers");
        File viewsDir = new File(appDir, "views");
        
        if (urlTokens.size()>0){
        	if (new File(controllersDir.getAbsolutePath(), urlTokens.get(0) + ".java").exists()){
	        	//TODO: check if controller was compiled (get name from there - check case)
	        	controllerName = "controllers." + WordUtils.capitalize(urlTokens.get(0));
	        	urlTokens.remove(0);
        	}
        }
        ClassLoader classLoader = Application.class.getClassLoader();
        Class controllerClass = null;
        Method method = null;
        
        try{
        	//solve case problem
        	controllerClass = classLoader.loadClass(controllerName);
        }
        catch (ClassNotFoundException e){
        	return ok(index.render("Class not found"));
        	//render 404 not found
        }
        
        try{
        	if (urlTokens.size()>0 && controllerClass.getMethod(urlTokens.get(0))!=null){
            	methodName = urlTokens.get(0);
        	}
        	method = controllerClass.getMethod(methodName);

        	//return ok(debug.render(new File(viewsDir.getAbsolutePath(), urlTokens.get(0) + "scala.html").getAbsolutePath()));/*
        	try{
        		return (Result)method.invoke(null);
        	}
        	catch (InvocationTargetException e){
                return ok(debug.render("InvocationTargetException"));
        	}
        }
        catch (Exception e){ //NoSuchMethodException or SecurityException
            return ok(debug.render(e.toString()));
            //render 404 not found
        }

        //return ok(index.render(controllerName));
        //return ok(index.render("Your new application is ready."));*/
    }
}

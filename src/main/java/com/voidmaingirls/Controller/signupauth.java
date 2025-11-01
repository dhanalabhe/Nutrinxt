package com.voidmaingirls.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class signupauth {

     public static boolean loginStatus(String email,String password) {
try{
        URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyCk3sGJQj8N4WPm4Bm8aWWKyIxjXLLjVhU");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);
        String payload = String.format( "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",email,password);
        OutputStream os = null;
        os = connection.getOutputStream();
        os.write(payload.getBytes());
        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            //Login Successful;
            return true;
        }else{
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                errorResponse.append(line);
            }
            System.out.println("signup failed"+errorResponse.toString());
            // Optionally log or handle errorResponse.toString()
        }
        
    }
    catch(Exception e){

    }
return false;
        

    }
    
}

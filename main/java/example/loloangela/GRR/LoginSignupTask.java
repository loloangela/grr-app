package example.loloangela.GRR;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lo on 3/25/2016.
 */
public class LoginSignupTask extends AsyncTask<String, Void, String>{
    protected Context context;
    protected int decider;
    static User me;
    String username;
    UserData userData;

    public LoginSignupTask(SigninActivity validator, int i){
        context = validator;
        decider = i;
        userData = new UserData(validator);
    }
    public LoginSignupTask(ValidateActivity validator, int i){
        context = validator;
        decider = i;
        userData = new UserData(validator);
    }
    @Override
    protected String doInBackground(String... params) {
        if(decider == 1){
            // Login validate username and password
            // Use testLogin.php
            String link = "http://lorioliver.net/grr_login.php";
            username = params[0];
            String password = params[1];
            try {
                // Create Connection
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Write data to output stream
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bw.write(data);
                // Close output stream
                bw.flush();
                bw.close();
                outputStream.close();

                // Read data from input stream
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                String response = "";
                while ((line = br.readLine()) != null){
                    response += line;
                }
                // Close input stream and disconnect
                br.close();
                inputStream.close();
                httpURLConnection.disconnect();
                // Send server response back to user
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(decider == 2){
            // Signup - create new user account
            // Use grr_signup.php
            String link = "http://lorioliver.net/grr_signup.php";
            String name = params[0];
            String email = params[1];
            username = params[2];
            String password = params[3];

            try {
                // Create Connection
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Write data to output stream
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8")
                        + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bw.write(data);
                // Close output stream
                bw.flush();
                bw.close();
                outputStream.close();

                // Read data from input stream
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = "";
                String response = "";
                while ((line = br.readLine()) != null){
                    response += line;
                }
                // Close input stream and disconnect
                br.close();
                inputStream.close();
                httpURLConnection.disconnect();
                // Send server response back to user
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // Here is where we retrieve data sent from the database
        // If the result is not -1 send the user to the map activity else pop up
        if(!result.equalsIgnoreCase("-1")){

            if(decider == 1){
                // Login
                String[] split = result.split("\\s+");
                me = new User(Integer.parseInt(split[0]), split[1]);
                userData.storeUserData(me);
                Toast.makeText(context, "Welcome, " + me.getUsername() + "!", Toast.LENGTH_LONG).show();
                userData.setUserLoggedIn(true);
                // Get recommendations for user
                new RecommenderTask(context).execute(String.valueOf(me.getUser_id()));
                // Go to map activity
//                context.startActivity(new Intent(context, MapActivity.class));
            }else if(decider == 2){
                // New User
                me = new User(Integer.parseInt(result), username);
                userData.storeUserData(me);
                Toast.makeText(context, "Welcome, " + me.getUsername() + "!", Toast.LENGTH_LONG).show();
                // User will not be considered logged in until profile is created
                // userData.setUserLoggedIn(true);
                // Create default rating for  new user
                new RatingTask((SigninActivity) context, 3).execute(String.valueOf(me.getUser_id()));
                // Send to Baseline
                context.startActivity(new Intent(context, BaselineActivity.class));
            }

        }
        else{
            Toast.makeText(context, "Unable to Login!", Toast.LENGTH_LONG).show();
        }
    }
}

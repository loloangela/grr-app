package example.loloangela.GRR;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.RatingBar;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lo on 4/1/2016.
 */
public class RatingTask extends AsyncTask<String, Void, String> {
    Context context;
    UserData userData;
    StationData stationData;
    Station curStation;
    int decider;
    RatingBar rbar;

    public RatingTask(RadioActivity mainActivity, int ig) {
        context = mainActivity;
        decider = ig;
        userData = new UserData(context);
        stationData = new StationData(context);
        curStation = stationData.getCurrentStation();
    }

    public RatingTask(RadioActivity mainActivity, int ig, RatingBar rb) {
        context = mainActivity;
        decider = ig;
        userData = new UserData(context);
        stationData = new StationData(context);
        curStation = stationData.getCurrentStation();
        rbar = rb;
    }

    public RatingTask(SigninActivity mainActivity, int ig) {
        context = mainActivity;
        decider = ig;
        userData = new UserData(context);
        stationData = new StationData(context);
        curStation = stationData.getCurrentStation();
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("-1")){
            Toast.makeText(context, "Unable to update rating!", Toast.LENGTH_LONG).show();
        }else{
            if(decider == 2){
                rbar.setRating(Float.parseFloat(result));
            }
        }


    }

    @Override
    protected String doInBackground(String... params) {
        String link = curStation.getHome();
        String user_id = params[0];



        try {
            // Update entry in rating table use grr_rating.php
            if(decider == 1){
                // Create Connection
                link += "grr_rating.php";
                String station_id = params[1];
                String rating = params[2];
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Write data to output stream
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")
                        + "&" + URLEncoder.encode("station_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(station_id), "UTF-8")
                        + "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(rating), "UTF-8");
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
            } else if(decider == 2){
                // Get the station rating for the logged in user
                // Create Connection
                String station_id = params[1];
                link += "grr_getRating.php";
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Write data to output stream
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")
                        + "&" + URLEncoder.encode("station_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(station_id), "UTF-8");
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

            } else if(decider == 3){
                // Create default rating preferences for new user
                link += "grr_initRating.php";
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Write data to output stream
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
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
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
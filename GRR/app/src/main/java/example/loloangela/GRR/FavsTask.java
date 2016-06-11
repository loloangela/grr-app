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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lo on 4/1/2016.
 */
public class FavsTask extends AsyncTask<String, Void, String> {
    Context context;
    int decider;
    UserData userData;
    User me;
    StationData stationData;
    Station curStation;
    public FavsTask(Context mainActivity, int id) {
        context = mainActivity;
        decider = id;
        userData = new UserData(context);
        me = userData.getLoggedIn();
        stationData = new StationData(context);
        curStation = stationData.getCurrentStation();
    }

    @Override
    protected void onPostExecute(String result) {
        if(decider == 1){
            // Retrieve UserFavs
            if (!result.equals("-1")){
                userData.setUserFavs(result);
                context.startActivity(new Intent(context, ListFavsActivity.class));
            }
        }
        else if(decider == 2){
            // Add Station to UserFavs
            if(!result.equals("-1")){
                Toast.makeText(context, "Station Added to Favorites",Toast.LENGTH_SHORT).show();
            }
        }else if(decider == 3){
            // Remove Station from UserFavs
            if(!result.equals("-1")){
                Toast.makeText(context, "Station Removed from Favorites",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String link = curStation.getHome();
        String user_id = params[0];
        if(decider == 1){
            // Get user's favs
            link += "grr_userFavs.php";
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(decider == 2){
            // Add a station to favs
            String station_id = params[1];
            link += "grr_favs.php";
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
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")
                        + "&" + URLEncoder.encode("station_id", "UTF-8") + "=" + URLEncoder.encode(station_id, "UTF-8");
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
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(decider == 3){
            // Remove a station from a user's favs
            String station_id = params[1];
            link += "grr_rmvFav.php";
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
                String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8")
                        + "&" + URLEncoder.encode("station_id", "UTF-8") + "=" + URLEncoder.encode(station_id, "UTF-8");
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
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

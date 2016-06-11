package example.loloangela.GRR;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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
 * Created by Lo on 3/28/2016.
 */
public class StationPickerTask extends AsyncTask<String, Void, String> {
    UserData userData;
    StationData stationData;
    Station curStation;
    int station_id;
    String name;
    Context context;
    static boolean stationInUse;

    public StationPickerTask(MapActivity mapActivity, String nm, int id) {
        context = mapActivity;
        name = nm;
        userData = new UserData(context);
        stationData = new StationData(context);
        station_id = id;
        stationInUse =false;
    }

    public StationPickerTask(ListAllActivity mapActivity, String nm, int id) {
        context = mapActivity;
        name = nm;
        userData = new UserData(context);
        stationData = new StationData(context);
        station_id = id;
        stationInUse =false;
    }
    public StationPickerTask(Context mapActivity, String nm, int id) {
        context = mapActivity;
        name = nm;
        userData = new UserData(context);
        stationData = new StationData(context);
        station_id = id;
        stationInUse =false;
    }

    @Override
    protected String doInBackground(String... params) {
        Station tempStation = new Station(name, station_id );
        String link = tempStation.getHome();
        link += "grr_stationlist.php";
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
            String data = URLEncoder.encode("station_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(station_id), "UTF-8");
            bw.write(data);
            // Close output stream
            bw.flush();
            bw.close();
            outputStream.close();

            // Just so it can save right
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

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // Result contains the songs in the station
        if(!result.equalsIgnoreCase("-1")){
           String[] split = result.split("\\s+");
            curStation = new Station(name, station_id, split);
            stationData.clearStationData();
            stationData.storeStationData(curStation);
            stationInUse = true;
            context.startActivity(new Intent(context, RadioActivity.class));
        }
    }
}

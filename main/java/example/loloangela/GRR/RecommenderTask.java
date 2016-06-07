package example.loloangela.GRR;

import android.app.Activity;
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
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lo on 4/22/2016.
 */
public class RecommenderTask extends AsyncTask<String, Void, String > {
    protected Context context;
    protected User me;
    protected UserData userData;

    public RecommenderTask(Context ctext) {
        context = ctext;
        userData = new UserData(context);
        me = userData.getLoggedIn();
    }

    @Override
    protected void onPostExecute(String s) {
//        Toast.makeText(context, "Recommendation has been set to: " + s,Toast.LENGTH_LONG).show();
        if(Integer.parseInt(s) > 0){
            // Set the recommendation
            userData.setRec(Integer.parseInt(s));
        }else{
            //Toast.makeText(context, "Recommendation could not be generated.",Toast.LENGTH_LONG).show();
            userData.setRec(2);
        }
        context.startActivity(new Intent(context, MapActivity.class));
        ((Activity) context).finish();
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        // access Recommender Algorithm to determine the recommendation for one user
        String link = "http://lorioliver.net/Java/getData.php";
        try {
            // Create Connection
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            // Write data to output stream
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8");
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
        return null;
    }
}

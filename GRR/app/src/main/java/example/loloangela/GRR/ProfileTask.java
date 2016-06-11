package example.loloangela.GRR;

import android.content.Context;
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
 * Created by Lo on 4/21/2016.
 */
public class ProfileTask extends AsyncTask<String, Void, String > {
    protected Context context;
    protected User me;
    protected UserData userData;

    public ProfileTask(Context baselineActivity){
        context = baselineActivity;
        userData = new UserData(baselineActivity);
        me = userData.getLoggedIn();
    }

    @Override
    protected String doInBackground(String... params) {
        // Accept user_id, like_most, like_least, send to php file
        String user_id = params[0];
        String like_most = params[1];
        String like_least = params[2];

        String link = "http://lorioliver.net/grr_profileInit.php";
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
                    + "&" + URLEncoder.encode("like_most", "UTF-8") + "=" + URLEncoder.encode(like_most, "UTF-8")
                    + "&" + URLEncoder.encode("like_least", "UTF-8") + "=" + URLEncoder.encode(like_least, "UTF-8");
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

    @Override
    protected void onPostExecute(String s) {
        // Call other task to generate recommendation and send to Map
        new RecommenderTask(context).execute(String.valueOf(me.getUser_id()));
        super.onPostExecute(s);
    }
}

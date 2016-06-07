package example.loloangela.GRR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFavsActivity extends AppCompatActivity {
    ListView listView;
    Context context;
    UserData userData;
    User me;
    StationData stationData;
    Toolbar toolbar;
    Station curStation;
    // Pass text to this Activity so it can construct and populate the list
    // The string you get will be broken into an array
    // A new array with same size will be created and the names from stations array below
    // In your program you will just save it to userData and retrieve it from there

    ArrayList stationFavs;
    public static String[] stations ={"Europe", "Africa", "North America", "South America", "Asia", "Australia", "Antarctica"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favs);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        userData = new UserData(this);
        me = userData.getLoggedIn();
        stationData = new StationData(this);

        // Get string from view and construct array;
        String stationIDs = userData.getUserFavs();
        String[] ids = stationIDs.split("\\s+");
        if(ids.length > 0){
            if(!ids[0].equals("")){
                String[] stationFavs = new String[ids.length];
               // ArrayList<String> stationFavs = new ArrayList<String>(ids.length);
                for (int i = 0; i < stationFavs.length; i++){
                    stationFavs[i] = stations[Integer.parseInt(ids[i]) - 1];
                }
                context = ListFavsActivity.this;
                listView = (ListView) findViewById(R.id.userFavsLV);
                listView.setAdapter(new CustomAdapter(ListFavsActivity.this, stationFavs, R.drawable.delete));
            }else{
                TextView title = (TextView) findViewById(R.id.titleTV);
                title.setText("You have no favorites");
                title.setVisibility(View.VISIBLE);
            }
        }else{
            TextView title = (TextView) findViewById(R.id.titleTV);
            title.setText("You have no favorites");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                // Turn off player
                if (RadioService.mediaPlayer != null)
                    stopService(new Intent(this, RadioService.class));
                // Logout and return to Login
                User temp = userData.getLoggedIn();
                userData.setUserLoggedIn(false);
                Toast.makeText(this, " Bye, " + temp.getUsername() + "!", Toast.LENGTH_LONG).show();
                userData.clearUserData();
                finish();
                //startActivity(new Intent(this, LoginActivity.class));
                break;
            case  R.id.home:
                finish();
                startActivity(new Intent(this, MapActivity.class));
                break;
            case  R.id.allStations:
                startActivity(new Intent(this, ListAllActivity.class));
                break;
            case R.id.favStations:
                new FavsTask(this, 1).execute(String.valueOf(me.getUser_id()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!userData.getUserLoggedIn())
            finish();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        if(!userData.getUserLoggedIn())
            finish();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        if(!userData.getUserLoggedIn())
            finish();
        super.onStop();
    }

}

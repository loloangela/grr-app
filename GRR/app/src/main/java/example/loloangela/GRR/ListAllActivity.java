package example.loloangela.GRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListAllActivity extends AppCompatActivity {
    UserData userData;
    User me;
    StationData stationData;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        userData = new UserData(this);
        me = userData.getLoggedIn();
        stationData = new StationData(this);

        String[] allStations = {"Europe", "Africa", "North America", "South America", "Asia", "Australia", "Antarctica" };
        ListAdapter stationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allStations);
        ListView stationListView = (ListView) findViewById(R.id.StationListAll);
        stationListView.setAdapter(stationAdapter);

        stationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (RadioService.first == false) {
                    if (RadioService.mediaPlayer != null) {
//                        if (RadioService.mediaPlayer.isPlaying()) {
                            Station temp = stationData.getCurrentStation();
                            if (temp.getStation_id() != (position + 1)) {
                                stopService(new Intent(ListAllActivity.this, RadioService.class));
                            }
//                        }

                    }
                }
                switch (position) {
                    case 0:
                        new StationPickerTask(ListAllActivity.this, String.valueOf(parent.getItemAtPosition(position)), position + 1).execute();
                        break;
                    case 1:
                        new StationPickerTask(ListAllActivity.this, String.valueOf(parent.getItemAtPosition(position)), position + 1).execute();
                        break;
                    case 2:
                         new StationPickerTask(ListAllActivity.this,  "nAmerica", position + 1).execute();
                        break;
                    case 3:
                        new StationPickerTask(ListAllActivity.this, "sAmerica", position + 1).execute();
                        break;
                    case 4:
                        new StationPickerTask(ListAllActivity.this, String.valueOf(parent.getItemAtPosition(position)), position + 1).execute();
                        break;
                    case 5:
                        new StationPickerTask(ListAllActivity.this, String.valueOf(parent.getItemAtPosition(position)), position + 1).execute();
                        break;
                    case 6:
                        new StationPickerTask(ListAllActivity.this, String.valueOf(parent.getItemAtPosition(position)), position + 1).execute();
                        break;
                }
            }
        });
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

package example.loloangela.GRR;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MapActivity extends AppCompatActivity {
    public final static String X_MESSAGE = " ";
    EditText msg;
    UserData userData;
    StationData stationData;
    User me;
    String[] allStations = {"Europe", "Africa", "North America", "South America", "Asia", "Australia", "Antarctica"};
    String[] allStations1 = {"Europe", "Africa", "NAmerica", "SAmerica", "Asia", "Australia", "Antarctica"};
    private Toolbar toolbar;
    // sendMsg calls the page to play radio
    // vw is the view that was clicked
    public void sendMsg(View vw){
        // In our test we will sent it to radio activity
        // First create an Intent
        Intent intent = new Intent(this, RadioActivity.class);
       // msg = (EditText) findViewById(R.id.editMsg);
        me = userData.getLoggedIn();
        String message = me.getUsername();
        intent.putExtra(X_MESSAGE, message);
        startActivity(intent);
    }

    public void getStation(View view, int id, String name){

        // Station station = new Station(name, id);
        // stationData.storeStationData(station);
        // If the mediaPlayer is playing
        if(RadioService.first == false){
            if(RadioService.mediaPlayer != null){
//                if(RadioService.mediaPlayer.isPlaying()){
                    Station temp = stationData.getCurrentStation();
                    if(temp.getStation_id() != id){
                        stopService(new Intent(this, RadioService.class));
                    }
//                }

            }
        }

        new StationPickerTask(this, name, id).execute();
       // msg = (EditText) findViewById(R.id.editMsg);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        userData = new UserData(this);
        me = userData.getLoggedIn();
        stationData = new StationData(this);
        if(userData.getRec() == -1)
            userData.setRec(3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, me.getUsername() + ", your recommended station is " + allStations[(userData.getRec() - 1)], Snackbar.LENGTH_LONG).setAction("Play", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getStation(view, userData.getRec(), allStations1[(userData.getRec() - 1)]);
                    }
                }).setActionTextColor(Color.parseColor("#870015")).show();

            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#870015")));

        Button africaButton = (Button) findViewById(R.id.africa);
        Button antarcticaButton = (Button) findViewById(R.id.antarctica);
        Button asiaButton = (Button) findViewById(R.id.asia);
        Button australiaButton = (Button) findViewById(R.id.australia);
        Button europeButton = (Button) findViewById(R.id.europe);
        Button nAmericaButton = (Button) findViewById(R.id.nAmerica);
        Button sAmericaButton = (Button) findViewById(R.id.sAmerica);

        //msg.setText(me.getUsername());

        europeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 1, "Europe");
            }
        });
        africaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 2, "Africa");
            }
        });
        nAmericaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 3, "nAmerica");
            }
        });
        sAmericaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 4, "sAmerica");
            }
        });
        asiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 5, "Asia");
            }
        });
        australiaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 6, "Australia");
            }
        });
        antarcticaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStation(v, 7, "Antarctica");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(final Menu menu){
//       getMenuInflater().inflate(R.menu.menu_map, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
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
//        moveTaskToBack(true);
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

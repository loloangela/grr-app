package example.loloangela.GRR;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RadioActivity extends AppCompatActivity implements View.OnClickListener{



    //final String song = "http://lorioliver.net/music/BlackHippy.mp3";
    private int songCount = 0;
    static MediaPlayer mediaPlayer;
    private boolean paused = false;
    private boolean stopped = false;
    UserData userData;
    User me;
    StationData stationData;
    Station curStation;
    TextView stationName;
    private Toolbar toolbar;
    RatingBar stationRateBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        // Storage
        userData = new UserData(this);
        me = userData.getLoggedIn();
        stationData = new StationData(this);
        // Start Station Service
        curStation = stationData.getCurrentStation();
        startService(new Intent(this, RadioService.class));
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // Objects - Buttons and RatingBar
        Button playButton = (Button) findViewById(R.id.play);
        Button stopButton = (Button) findViewById(R.id.stop);
        Button pauseButton = (Button) findViewById(R.id.pause);
        Button favButton = (Button) findViewById(R.id.fav);
//        Button prevButton = (Button) findViewById(R.id.prev);
//        Button nextButton = (Button) findViewById(R.id.next);
        stationRateBar = (RatingBar) findViewById(R.id.ratingBar);
        new RatingTask(RadioActivity.this, 2, stationRateBar).execute(String.valueOf(me.getUser_id()), String.valueOf(curStation.getStation_id()));

        // Set station name - Account for name shortcuts
        stationName = (TextView) findViewById(R.id.stationTV);
        if((curStation.getStation_id() != 3) && (curStation.getStation_id() != 4))
            stationName.setText(curStation.getName() + " Station");
        else if(curStation.getStation_id() == 3)
            stationName.setText("North America Station");
        else if(curStation.getStation_id() == 4)
            stationName.setText("South America Station");

        // OnClickListeners
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        favButton.setOnClickListener(this);
//        prevButton.setOnClickListener(this);
//        nextButton.setOnClickListener(this);
        stationRateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Update record in table for this user and this station
                new RatingTask(RadioActivity.this,1).execute(String.valueOf(me.getUser_id()), String.valueOf(curStation.getStation_id()), String.valueOf(rating));
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                // Need to stop player if playing
                startService(new Intent(this, RadioService.class));
                break;
            case R.id.stop:
                stopService(new Intent(this, RadioService.class));
                break;
            case R.id.pause:
                RadioService.pausePlayer();
                break;
            case R.id.fav:
                new FavsTask(this, 2).execute(String.valueOf(me.getUser_id()), String.valueOf(curStation.getStation_id()));
                break;
//            case R.id.next:
//                RadioService.nextSong();
//                break;
//            case R.id.prev:
//                RadioService.prevSong();
//                break;
        }
    }

//    public static class PlaceholderFragment extends Fragment {
//        public PlaceholderFragment() { }
//
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//            View rootView = inflater.inflate(R.layout.fragment_display_message, container, false);
//            return rootView;
//        }
//    }

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

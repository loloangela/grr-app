package example.loloangela.GRR;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Lo on 3/31/2016.
 */
public class RadioService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    UserData userData;
    StationData stationData;
    static Station curStation;
    static MediaPlayer mediaPlayer;
    static boolean first = true;

    @Override
    public void onCreate() {
        userData = new UserData(this);
        stationData = new StationData(this);
        curStation = stationData.getCurrentStation();
        Toast.makeText(this, "The media player for " + curStation.getName() + " is being created", Toast.LENGTH_LONG).show();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        initMediaPlayer();

        mediaPlayer.setOnCompletionListener(this);
    }

    public static void initMediaPlayer(){
        try {
            mediaPlayer.setDataSource(curStation.songs[curStation.getCurSong()]);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        first = true;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!first){
            mediaPlayer.start();
        }
        first = false;
        return super.onStartCommand(intent, flags, startId);
    }

    public static void nextSong(){
        mediaPlayer.reset();
        curStation.nextSong();
        initMediaPlayer();
    }

    public static void pausePlayer(){
        mediaPlayer.pause();
    }
    public static void prevSong(){
        mediaPlayer.reset();
        curStation.prevSong();
        initMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
       mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextSong();
    }
}

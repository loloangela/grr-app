package example.loloangela.GRR;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lo on 3/28/2016.
 */
public class StationData {
    public static final String SP_NAME = "stationDetails";
    SharedPreferences stationLocalData;

    public StationData(Context context) {
        stationLocalData = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeStationData(Station station){
        SharedPreferences.Editor spEditor = stationLocalData.edit();
        spEditor.putString("station_name", station.getName());
        spEditor.putInt("station_id", station.getStation_id());
        spEditor.putString("songs", station.getSongs());
        spEditor.putInt("curSong", station.getCurSong());
        spEditor.commit();
    }

    public Station getCurrentStation(){
        String station_name = stationLocalData.getString("station_name", "");
        int station_id = stationLocalData.getInt("station_id", 0);
        String songs = stationLocalData.getString("songs", "");
        Station curStation = new Station(station_name, station_id, songs);
        return curStation;
    }

    public void setStationInUse(boolean inUse){
        SharedPreferences.Editor  spEditor = stationLocalData.edit();
        spEditor.putBoolean("inUse", inUse);
        spEditor.commit();
    }

    public boolean isInUse(){
        if(stationLocalData.getBoolean("inUse", false) == true)
            return true;
        return false;
    }

    public  void clearStationData(){
        SharedPreferences.Editor spEditor = stationLocalData.edit();
        spEditor.clear();
        spEditor.commit();
    }
}

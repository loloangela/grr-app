package example.loloangela.GRR;

/**
 * Created by Lo on 3/28/2016.
 */
public class Station {
    String name;
    final String home = "http://lorioliver.net/";
    String path = "http://lorioliver.net/music/";
    int station_id;
    String[] songs;
    int curSong = 0;

    public String getHome() {
        return home;
    }

    public Station(String nm, int station_id) {
        this.name = nm;
        this.station_id = station_id;
        this.path += nm.toLowerCase() + "/";
    }

    public Station(String nm, int station_id, String sl) {
        this.name = nm;
        this.station_id = station_id;
        this.path += nm.toLowerCase() + "/";
        this.setSongs(sl);
    }

    public Station(String nm, int station_id, String[] sl) {
        this.name = nm;
        this.station_id = station_id;
        this.path += nm.toLowerCase() + "/";
        this.songs = sl;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getStation_id() {
        return station_id;
    }

    public int nextSong(){
        if(curSong < songs.length)
            curSong++;
        else
            curSong = 0;
        return curSong;
    }

    public int prevSong(){
        if(curSong > 0)
            curSong--;
        else
            curSong = songs.length - 1;
        return curSong;
    }

    public int getCurSong() {
        return curSong;
    }

    public String getSongs() {
        String msg = "";
        for(int i = 0; i < songs.length; i++){
            msg += songs[i] + " ";
        }
        return msg;
    }

    public void setSongs(String str){
        String[] split = str.split("\\s+");
        songs = new String[split.length];
        int i = 0;
        while(i < songs.length ){
            songs[i] = path + split[i];
            i++;
        }
    }


}

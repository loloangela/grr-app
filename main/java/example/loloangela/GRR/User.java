package example.loloangela.GRR;

/**
 * Created by Lo on 3/25/2016.
 */
public class User {
    private int user_id, user_rec;
    private String username;
    private  User instance;
    private int prefCount;
    private UserPrefs[] userFavs = new UserPrefs[10];

    public User(int user_id, String username) {
        this.user_id = user_id;
        this.username = username;
        this.instance = this;
        this.prefCount = 0;
    }
    public void setRec(int r){
        this.user_rec = r;
    }

    public int getRec() {
        return user_rec;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public User instance(){
        return instance;
    }

    public void addUserPref(int id, String name, String path){
        boolean found = false;
        for (int i = 0; i < prefCount; i++){
            if(id == userFavs[i].getStation_id())
                found = true;
        }
        if(found == false){
            UserPrefs temp = new UserPrefs(id, name, path);
            userFavs[prefCount++] = temp;
        }
    }

    public String sendUserPrefIds(){
        String ids = "";
        for (int i = 0; i < prefCount; i++){
            ids += userFavs[i].getStation_id() + " ";
        }

        return ids;
    }


    protected class UserPrefs{
        public UserPrefs(int id, String name, String path){
            station_id = id;
            stationName = name;
            stationPath = path;
        }
        String stationName, stationPath;
        int station_id;

        public String getStationName() {
            return stationName;
        }

        public String getStationPath() {
            return stationPath;
        }

        public int getStation_id() {
            return station_id;
        }
    }
}

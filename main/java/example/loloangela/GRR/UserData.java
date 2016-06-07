package example.loloangela.GRR;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lo on 3/25/2016.
 */
public class UserData {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalData;

    public UserData(Context context) {
        userLocalData = context.getSharedPreferences(SP_NAME, 0);
    }
    public void storeUserData(User user){

        SharedPreferences.Editor spEditor = userLocalData.edit();
        spEditor.putString("username", "Lori");
        spEditor.putInt("user_id", 1);
        spEditor.apply();

//        spEditor.putString("username", user.getUsername());
//        spEditor.putInt("user_id", user.getUser_id());
//        spEditor.apply();
    }

    public User getLoggedIn(){

        String username = userLocalData.getString("username", "");
        int user_id = userLocalData.getInt("user_id", -1);
        return new User(user_id,username);
    }

    public boolean getUserLoggedIn(){
        return userLocalData.getBoolean("loggedIn", false);
    }
    public void setUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor  spEditor = userLocalData.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public void setRec(int r){
        SharedPreferences.Editor  spEditor = userLocalData.edit();
        spEditor.putInt("user_rec", r);
        spEditor.apply();
    }

    public int getRec(){
        return userLocalData.getInt("user_rec", -1);
    }
    public void setUserFavs(String favs){
        SharedPreferences.Editor  spEditor = userLocalData.edit();
        spEditor.putString("user_favs", favs);
        spEditor.apply();
    }

    public String getUserFavs(){
        return userLocalData.getString("user_favs", "");
    }
    public  void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalData.edit();
        spEditor.clear();
        spEditor.apply();
    }
}

package example.loloangela.GRR;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

public class BaselineActivity extends AppCompatActivity {
    UserData userData;
    static User me;
    String  like_least;
    String like_most;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userData = new UserData(this);
        me = userData.getLoggedIn();
        like_least = "";
        like_most = "";

        final RadioGroup like_leastGroup = (RadioGroup) findViewById(R.id.LikeLeast);
        final RadioGroup like_mostGroup = (RadioGroup) findViewById(R.id.LikeMost);
        Button submitButton = (Button) findViewById(R.id.submitBaseline);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((like_leastGroup.getCheckedRadioButtonId() == -1) || (like_mostGroup.getCheckedRadioButtonId() == -1)){
                    Toast.makeText(v.getContext(), "You must select an option for each!", Toast.LENGTH_SHORT).show();
                }else{
                    onRadioButtonClicked(like_leastGroup.getCheckedRadioButtonId());
                    onRadioButtonClicked(like_mostGroup.getCheckedRadioButtonId());
                    //Toast.makeText(v.getContext(), "Like_Most: " + like_most + " Like_Least: " + like_least, Toast.LENGTH_LONG).show();
                    if(like_least != like_most){
                        // Send to loading page???
                        // Send these numbers to background task to develop profile
                        new ProfileTask(v.getContext()).execute(String.valueOf(me.getUser_id()), like_most, like_least);
                        pb.setVisibility(View.VISIBLE);
                        //finish();
                    }else{
                        Toast.makeText(v.getContext(), "They must be different!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void onRadioButtonClicked(int baseVal){
        switch (baseVal){
            case R.id.hiphopLL:
                like_least = "1";
                break;
            case R.id.hiphopLM:
                like_most = "1";
                break;
            case R.id.gangstaLL:
                like_least = "2";
                break;
            case R.id.gangstaLM:
                like_most = "2";
                break;
            case R.id.rnbLL:
                like_least = "3";
                break;
            case R.id.rnbLM:
                like_most = "3";
                break;
            case R.id.lyricLL:
                like_least = "4";
                break;
            case R.id.lyricLM:
                like_most = "4";
                break;
            case R.id.trapLL:
                like_least = "5";
                break;
            case R.id.trapLM:
                like_most = "5";
                break;
            case  R.id.indieLL:
                like_least = "6";
                break;
            case R.id.indieLM:
                like_most = "6";
                break;
            case R.id.darkLL:
                like_least = "7";
                break;
            case R.id.darkLM:
                like_most = "7";
                break;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

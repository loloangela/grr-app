package example.loloangela.GRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

// Decider 1 - SignIn, 2 - Baseline
public class LoadingActivity extends AppCompatActivity {
    String data[];
    int decider = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.loadingTv);
        TextView textView1 = (TextView) findViewById(R.id.unamTv);
        Button button = (Button) findViewById(R.id.closeBtn);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Grab info from intent depending on the info returned we navigate to appropriate Tas
        Intent intentExtras = getIntent();
        if(intentExtras.getStringArrayExtra("userInfo") != null){
            data = intentExtras.getStringArrayExtra("userInfo");

//            Toast.makeText(this, "Name: " + data[0] + " Email: " + data[1] + " Username: " + data[2] + " Password: " + data[3], Toast.LENGTH_LONG).show();

            // Call LoginSignupTask to determine if insert is successful
            new LoginSignupTask(this, 2, textView, button, progressBar).execute(data[0], data[1], data[2], data[3]);
        }else if(intentExtras.getStringArrayExtra("userBaseline") != null){
            data = intentExtras.getStringArrayExtra("userBaseline");

        }

    }
    public void exitPg(View view){
        finish();
    }


}

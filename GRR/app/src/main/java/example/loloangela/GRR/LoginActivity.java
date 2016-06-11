package example.loloangela.GRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    UserData userData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userData = new UserData(this);
        User cur = userData.getLoggedIn();
        Toast.makeText(this, "The user in userData is " + cur.getUsername(),Toast.LENGTH_SHORT).show();
        if(userData.getUserLoggedIn())
        {
            startActivity(new Intent(this, MapActivity.class));
        }else {
            userData.clearUserData();
        }
        Button login_button = (Button) findViewById(R.id.login);
        Button signUp_button = (Button) findViewById(R.id.signUp);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ValidateActivity.class));
            }
        });

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        if(userData.getUserLoggedIn())
        {
            startActivity(new Intent(this, MapActivity.class));
        }else {
            userData.clearUserData();
        }
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, RadioService.class));
        super.onDestroy();
    }
}

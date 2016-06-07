package example.loloangela.GRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    UserData userData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userData = new UserData(this);
        if(userData.getUserLoggedIn())
        {
            startActivity(new Intent(this, MapActivity.class));
        }
        userData.clearUserData();
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
    protected void onDestroy() {
        stopService(new Intent(this, RadioService.class));
        super.onDestroy();
    }
}

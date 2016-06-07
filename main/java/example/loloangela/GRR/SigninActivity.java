package example.loloangela.GRR;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class SigninActivity extends AppCompatActivity {
    EditText nm, em, unm, pswd;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        userData = new UserData(this);

        nm = (EditText) findViewById(R.id.nameET);
        em = (EditText) findViewById(R.id.emailET);
        unm = (EditText) findViewById(R.id.usernameET);
        pswd = (EditText) findViewById(R.id.passwordET);
    }

    public void signUp(View view){
        String name = nm.getText().toString();
        String email = em.getText().toString();
        String username = unm.getText().toString();
        String password = pswd.getText().toString();
        // Add functionality to force user to have name (>3 char), email, username (must be unique), password
        new LoginSignupTask(this, 2).execute(name, email, username, password);
        finish();
    }


}

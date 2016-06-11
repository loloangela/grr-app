package example.loloangela.GRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        if(userData.getUserLoggedIn()){
            startActivity(new Intent(this, MapActivity.class));
            finish();
        }
    }

    public void signUp(View view){
        // Retrieve user info from EditText fields
        String name = nm.getText().toString();
        String email = em.getText().toString();
        String username = unm.getText().toString();
        String password = pswd.getText().toString();

        // Add functionality to force user to have name (>3 char), email, username (must be unique), password
        String msg = "", errorMsg1 = "Name needs at least 4 characters.\n", errorMsg2 = "Email must be filled in.\n",
                errorMsg3 = "Username needs at least 4 characters\n", errorMsg4 = "Password must be atleast 4 characters\n";
        boolean invalid = false;
        if(name.length() < 2){
            msg += errorMsg1;
            invalid = true;
        }if (email.length() == 0){
            msg += errorMsg2;
            invalid = true;
        }if(username.length() < 4){
            msg += errorMsg3;
            invalid = true;
        }
        if(password.length() < 4){
            msg += errorMsg4;
            invalid = true;
        }
        if(invalid){
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }else {
            // First we will call LoadingActivity (passing user info) and it will initiate the correct AsyncTask and display error
            String info[] = {name, email, username, password};
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtra("userInfo", info);
            startActivity(intent);

//            new LoginSignupTask(this, 2).execute(name, email, username, password);
            // This will be removed once I figure activity lifecycle, callbacks shit.
//            finish();
        }

    }


}

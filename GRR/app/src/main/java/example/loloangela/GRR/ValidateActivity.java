package example.loloangela.GRR;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class ValidateActivity extends AppCompatActivity {
    EditText uname, pass;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        userData = new UserData(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int)(height * .6));

        uname = (EditText) findViewById(R.id.usernameET);
        pass = (EditText) findViewById(R.id.passwordET);

    }

    public void login(View view){
        String username = uname.getText().toString();
        String password = pass.getText().toString();
        new LoginSignupTask(this, 1).execute(username, password);
        finish();
    }



}

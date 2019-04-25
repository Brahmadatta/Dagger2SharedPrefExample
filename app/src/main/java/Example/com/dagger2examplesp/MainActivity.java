package Example.com.dagger2examplesp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName,number;
    Button save,getValue;
    private MyComponent myComponent;
    TextView userNameGet,numberGet;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.username);
        number = findViewById(R.id.number);
        save = findViewById(R.id.save);
        getValue = findViewById(R.id.getValue);
        userNameGet = findViewById(R.id.userNameGet);
        numberGet = findViewById(R.id.numberGet);

        save.setOnClickListener(this);
        getValue.setOnClickListener(this);

        myComponent = DaggerMyComponent.builder().sharedPrefModule(new SharedPrefModule(this)).build();
        myComponent.inject(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.getValue:
                userNameGet.setText(sharedPreferences.getString("username","default"));
                numberGet.setText(sharedPreferences.getString("number","12345"));
                break;

            case R.id.save:
                if (isVerified()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userName.getText().toString().trim());
                    editor.putString("number", number.getText().toString().trim());
                    editor.apply();
                    userName.setText("");
                    number.setText("");
                }
                break;
        }
    }

    private boolean isVerified() {

        if (userName.getText().toString().isEmpty()){
            userName.setError("please Enter Username");
            userName.requestFocus();
            return false;
        }

        if (number.getText().toString().isEmpty()){
            number.setError("Please enter Number");
            number.requestFocus();
            return false;
        }

        return true;
    }
}

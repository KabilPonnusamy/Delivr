package sg.delivr.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import sg.delivr.R;

public class Activity_HelpSupport extends AppCompatActivity implements View.OnClickListener {
    EditText input_name, input_email, input_mobile, input_message;
    LinearLayout submit_help;
    ImageView help_back;
    String strName = "", strEmail = "", strMobile = "", strMessage = "";
    String str_emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);
        initView();
    }

    private void initView() {
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_mobile = findViewById(R.id.input_mobile);
        input_message = findViewById(R.id.input_message);
        help_back = findViewById(R.id.help_back);
        help_back.setOnClickListener(this);
        submit_help = findViewById(R.id.submit_help);
        submit_help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_help:
                strName = input_name.getText().toString().trim();
                strEmail = input_email.getText().toString().trim();
                strMobile = input_mobile.getText().toString().trim();
                strMessage = input_message.getText().toString().trim();

                if(strName.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_HelpSupport.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strEmail.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_HelpSupport.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strMobile.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_HelpSupport.this, "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(strMessage.equalsIgnoreCase("")) {
                    Toast.makeText(Activity_HelpSupport.this, "Please enter your Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!strEmail.trim().matches(str_emailPattern)) {
                    Toast.makeText(Activity_HelpSupport.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Activity_HelpSupport.this, "Help & Support will impletent Later.", Toast.LENGTH_SHORT).show();

                break;
            case R.id.help_back:
                finish();
                break;
        }
    }
}

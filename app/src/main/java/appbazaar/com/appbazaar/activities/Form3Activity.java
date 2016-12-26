package appbazaar.com.appbazaar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.util.Common;
import appbazaar.com.appbazaar.util.Constants;
import appbazaar.com.appbazaar.util.CustomApplication;

public class Form3Activity extends AppCompatActivity {



    private Resources resources;
    private RelativeLayout relativeLayout;
    private Intent intent;
    private Context context;
    private EditText form3Mobile_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form3);
        context=this;
        resources=getResources();

        final TextInputLayout form3Input = (TextInputLayout) findViewById(R.id.form3Input);
        form3Input.setHint(resources.getString(R.string.mobile_number_hint));

        form3Mobile_et=(EditText) findViewById(R.id.form3Mobile_et);
        String ph= CustomApplication.getFormatedMobileNumber();
        form3Mobile_et.setText(TextUtils.isEmpty(ph)?"":ph);
        form3Mobile_et.setSelection(form3Mobile_et.getText().length());

        relativeLayout=(RelativeLayout)findViewById(R.id.form3);

        Button form3Next_btn=(Button) findViewById(R.id.form3Next_btn);

        form3Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ph=form3Mobile_et.getText()!=null?form3Mobile_et.getText().toString():"";
                Snackbar.make(relativeLayout, "Next Clicked", Snackbar.LENGTH_LONG).show();

                intent = new Intent(context, MainActivity.class);
                Bundle extras=new Bundle();
                extras.putString(Constants.USER_SELF_NUMBER, ph);
                intent.putExtras(extras);

                startActivity(intent);
                finish();
            }
        });
    }
}

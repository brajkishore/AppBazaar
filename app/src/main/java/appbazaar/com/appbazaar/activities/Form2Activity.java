package appbazaar.com.appbazaar.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;

public class Form2Activity extends AppCompatActivity {


    private LinearLayout linearLayout;
    private Intent intent;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        context=this;

        linearLayout=(LinearLayout)findViewById(R.id.form2);

        Button form2Student_btn=(Button) findViewById(R.id.form2Student_btn);
        Button form2Working_btn=(Button) findViewById(R.id.form2Working_btn);
        Button form2Next_btn=(Button) findViewById(R.id.form2Next_btn);
        TextView form1Ret_tv=(TextView) findViewById(R.id.form1Ret_tv);

        form2Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(linearLayout, "Next Clicked", Snackbar.LENGTH_LONG).show();
                intent = new Intent(context, Form3Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

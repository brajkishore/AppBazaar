package appbazaar.com.appbazaar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;

public class Form1Activity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Intent intent;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1);
        context=this;

        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.form1);
        Button form1Male_btn=(Button) findViewById(R.id.form1Male_btn);
        Button form1Female_btn=(Button) findViewById(R.id.form1Female_btn);
        TextView form1Ret_tv=(TextView) findViewById(R.id.form1Ret_tv);

        form1Male_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(coordinatorLayout,"Male Clicked",Snackbar.LENGTH_LONG).show();
                intent=new Intent(context,Form2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        form1Female_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Snackbar.make(coordinatorLayout,"Female Clicked",Snackbar.LENGTH_LONG).show();

                Snackbar.make(coordinatorLayout,"Male Clicked",Snackbar.LENGTH_LONG).show();
                intent=new Intent(context,Form2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        form1Ret_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Snackbar.make(coordinatorLayout,"Retailer Clicked",Snackbar.LENGTH_LONG).show();
            }
        });
    }

}

package appbazaar.com.appbazaar.activities;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.adapters.InstructionAdapter;
import appbazaar.com.appbazaar.model.AppOffer;
import appbazaar.com.appbazaar.model.Instruction;
import appbazaar.com.appbazaar.util.Constants;
import appbazaar.com.appbazaar.util.CustomApplication;

public class AppInstallActivity extends AppCompatActivity {

    private AppOffer appOffer;
    private LinearLayout linearLayout;
    private Snackbar snackbar;



   protected void onStart(){
       super.onStart();
   }

    protected void onStop(){
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_install);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        linearLayout=(LinearLayout)findViewById(R.id.activityAppInstall);

        appOffer=(AppOffer)getIntent().getSerializableExtra(Constants.USER_SELECTED_OFFER);


        TextView promoPrice_tv=(TextView)findViewById(R.id.promoPrice_tv);
        TextView promoName_tv=(TextView)findViewById(R.id.promoName_tv);
        TextView promoNameSh_tv=(TextView)findViewById(R.id.promoNameSh_tv);
        promoPrice_tv.setText(appOffer.getPromo_price());
        promoName_tv.setText(appOffer.getPromo_name());
        promoNameSh_tv.setText(appOffer.getPromo_short_info());


        if(!TextUtils.isEmpty(appOffer.getDownloadInstructions().getHeader())){
            TextView promoInstructionHeader=(TextView)findViewById(R.id.promoInstructionHeader);
            promoInstructionHeader.setVisibility(View.VISIBLE);
            promoInstructionHeader.setText(appOffer.getDownloadInstructions().getHeader());
            promoInstructionHeader.setTextColor(CustomApplication.resources.getColor(R.color.defaultInstructionHeadColor));

        }

        if(appOffer.getDownloadInstructions()!=null &&
                appOffer.getDownloadInstructions().getInstructions()!=null &&
                appOffer.getDownloadInstructions().getInstructions().size()>0) {



            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.promoInstructions);
            linearLayout.setVisibility(View.VISIBLE);
            TextView tv=null;
            View divider1=findViewById(R.id.divider1);
            divider1.setVisibility(View.VISIBLE);

            for(Instruction instruction:appOffer.getDownloadInstructions().getInstructions()){
                tv=(TextView)inflater.inflate(R.layout.instruction_layout, null);
                tv.setText(instruction.getMessage());

                if(TextUtils.isEmpty(instruction.getColorCode()))
                    tv.setTextColor(CustomApplication.resources.getColor(R.color.defaultInstructionTxtColor));
                linearLayout.addView(tv);
            }
        }

/*

        InstructionAdapter adapter=new InstructionAdapter(this,R.layout.instruction_layout,
                appOffer.getDownloadInstructions().getInstructions());

        ListView listView=(ListView)findViewById(R.id.promoInstructions);
        listView.setAdapter(adapter);
*/

        System.out.println("Inside onCreate activity "+appOffer.getTncInstructions().getInstructions());

        if(!TextUtils.isEmpty(appOffer.getTncInstructions().getHeader())){
            TextView promoTnCHeader=(TextView)findViewById(R.id.promoTnCHeader);
            promoTnCHeader.setVisibility(View.VISIBLE);
            promoTnCHeader.setText(appOffer.getTncInstructions().getHeader());
            promoTnCHeader.setTextColor(CustomApplication.resources.getColor(R.color.defaultInstructionHeadColor));

        }


        if(appOffer.getTncInstructions()!=null &&
                appOffer.getTncInstructions().getInstructions()!=null &&
                appOffer.getTncInstructions().getInstructions().size()>0) {

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.promoTnC);
            linearLayout.setVisibility(View.VISIBLE);
            TextView tv=null;
            View divider2=findViewById(R.id.divider2);
            divider2.setVisibility(View.VISIBLE);

            for(Instruction instruction:appOffer.getTncInstructions().getInstructions()){
                tv=(TextView)inflater.inflate(R.layout.instruction_layout, null);
                tv.setText(instruction.getMessage());

                if(TextUtils.isEmpty(instruction.getColorCode()))
                    tv.setTextColor(CustomApplication.resources.getColor(R.color.defaultInstructionTxtColor));

                linearLayout.addView(tv);
            }
        }

        switch (appOffer.getPromo_type()) {
            case Constants.PROMO_CLICK:
                snackbar=Snackbar.make(linearLayout,appOffer.getPromo_action_text(),Snackbar.LENGTH_INDEFINITE);
                break;
            case Constants.PROMO_INSTALL:
                snackbar=Snackbar.make(linearLayout,appOffer.getPromo_action_text(),Snackbar.LENGTH_INDEFINITE);
                break;
        }

        if(snackbar!=null){
          /*  snackbar.getView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    snackbar.getView().getViewTreeObserver().removeOnPreDrawListener(this);
                    ((CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams()).setBehavior(null);
                    return true;
                }
            });*/

            View view=snackbar.getView();
            view.setBackgroundColor(CustomApplication.resources.getColor(R.color.colorPrimaryDark));
            TextView text=(TextView)view.findViewById(android.support.design.R.id.snackbar_text);
            text.setTextColor(CustomApplication.resources.getColor(R.color.white));
            text.setAllCaps(false);
            text.setGravity(Gravity.CENTER_HORIZONTAL);

            snackbar.show();
        }

        System.out.println("Inside onCreate activity");
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

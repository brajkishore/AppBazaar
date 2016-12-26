package appbazaar.com.appbazaar.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.model.AppOffer;
import appbazaar.com.appbazaar.model.Instruction;
import appbazaar.com.appbazaar.util.Constants;
import appbazaar.com.appbazaar.util.CustomApplication;

public class AppFeaturedActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_featured);

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


        if(!TextUtils.isEmpty(appOffer.getPromo_detail())){
            CardView contentView=(CardView)findViewById(R.id.contentView);
            contentView.setVisibility(View.VISIBLE);
            TextView contentView_tv=(TextView)findViewById(R.id.contentView_tv);
            contentView_tv.setText(Html.fromHtml("<b>Content header</b><br/>1.statment 1<br/>2.statement 2"));
        }


        switch (appOffer.getPromo_type()) {
            case Constants.PROMO_CLICK:
                snackbar=Snackbar.make(linearLayout,appOffer.getPromo_action_text(),Snackbar.LENGTH_INDEFINITE);
                break;
            case Constants.PROMO_INSTALL:
                snackbar=Snackbar.make(linearLayout,appOffer.getPromo_action_text(),Snackbar.LENGTH_INDEFINITE);
                break;
            case Constants.PROMO_REGISTER:
                snackbar=Snackbar.make(linearLayout,appOffer.getPromo_action_text(),Snackbar.LENGTH_INDEFINITE);
                break;
        }

        if(snackbar!=null){

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

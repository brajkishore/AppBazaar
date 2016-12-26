package appbazaar.com.appbazaar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import appbazaar.com.appbazaar.R;

public class AddAccountPayeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_payee);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView sendMoneyWarn_tv=(TextView)findViewById(R.id.sendMoneyWarn_tv);
        sendMoneyWarn_tv.setText(Html.fromHtml("<b>Please Note:</b> Minimu amount to transfer is Rs.50.We will charge 2%"));


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

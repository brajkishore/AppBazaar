package appbazaar.com.appbazaar.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;

import java.util.concurrent.ConcurrentHashMap;

import appbazaar.com.appbazaar.R;
import de.greenrobot.event.EventBus;

/**
 * Created by braj.kishore on 4/21/2016.
 */
public class CustomApplication extends Application {

    private static TelephonyManager telephonyManager;
    private static AccountManager accountManager;
    public static Resources resources;
    private static ConcurrentHashMap<String,String> countryZipcodeMap;



    @Override
    public void onCreate()
    {
        super.onCreate();
        telephonyManager= (TelephonyManager)getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        resources=getResources();

        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        countryZipcodeMap=new ConcurrentHashMap<>();
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");
            countryZipcodeMap.put(g[1],g[0]);
            }

        AccountManager accountManager =(AccountManager) getApplicationContext().getSystemService(getApplicationContext().ACCOUNT_SERVICE);
    }


    public static String getEmailId(){
        Account account = getAccount(accountManager);
        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {

        if(accountManager==null)
            return null;

        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public static String getCountryZipCode(){
        return countryZipcodeMap.get(telephonyManager.getSimCountryIso().toUpperCase());
    }

    public static String getMobileNumber(){

        return telephonyManager.getLine1Number();
    }

    public static String getFormatedMobileNumber(){

        return "+"+getCountryZipCode()+telephonyManager.getLine1Number();
    }


    public static void postOnEventBus(Object event){
        EventBus.getDefault().post(event);
    }

    public static void registerOnEventBus(Object listener){
        EventBus.getDefault().register(listener);
    }

    public static  void unregisterOnEventBus(Object listener){
        EventBus.getDefault().unregister(listener);
    }

    public static void postStickyOnEventBus(Object event){
        EventBus.getDefault().postSticky(event);
    }

    public static void registerStickyOnEventBus(Object listener){
        EventBus.getDefault().registerSticky(listener);
    }

    public static  void unregisterStickyOnEventBus(Object listener){
        EventBus.getDefault().unregister(listener);
    }



}



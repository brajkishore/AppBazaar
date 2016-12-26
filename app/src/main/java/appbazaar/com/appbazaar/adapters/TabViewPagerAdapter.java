package appbazaar.com.appbazaar.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import appbazaar.com.appbazaar.fragments.AppListFragment;
import appbazaar.com.appbazaar.fragments.FeaturedProductFragment;
import appbazaar.com.appbazaar.fragments.SelfPromoFragment;
import appbazaar.com.appbazaar.fragments.TransferToBankFragment;

/**
 * Created by braj.kishore on 4/26/2016.
 */
public class TabViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Self", "Offers", "Featured Offers","Transfer to Bank"};
    private Context context;

    public TabViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return SelfPromoFragment.newInstance();
            case 1:
                return AppListFragment.newInstance();
            case 2:
                return FeaturedProductFragment.newInstance();
            case 3:
                return TransferToBankFragment.newInstance();

        }
        return AppListFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}

package appbazaar.com.appbazaar.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.adapters.TabViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabHolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabHolderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static TabHolderFragment newInstance() {
        TabHolderFragment fragment = new TabHolderFragment();
        return fragment;
    }

    public TabHolderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_tabholder, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) rootView.findViewById(R.id.mainViewPager);
        TabViewPagerAdapter pagerAdapter =
                new TabViewPagerAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) rootView.findViewById(R.id.mainTabs);
       // tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;

    }


}

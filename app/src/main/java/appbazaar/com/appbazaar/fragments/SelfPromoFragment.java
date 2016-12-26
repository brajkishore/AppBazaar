package appbazaar.com.appbazaar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appbazaar.com.appbazaar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelfPromoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfPromoFragment extends Fragment {


    public static SelfPromoFragment newInstance() {
        SelfPromoFragment fragment = new SelfPromoFragment();
        return fragment;
    }

    public SelfPromoFragment() {
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
        return inflater.inflate(R.layout.fragment_self_promo, container, false);
    }


}

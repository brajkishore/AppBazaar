package appbazaar.com.appbazaar.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import appbazaar.com.appbazaar.R;
import appbazaar.com.appbazaar.activities.AddAccountPayeeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransferToBankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferToBankFragment extends Fragment {


    public static TransferToBankFragment newInstance() {
        TransferToBankFragment fragment = new TransferToBankFragment();
        return fragment;
    }

    public TransferToBankFragment() {
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
        View rootView= inflater.inflate(R.layout.fragment_transfer2bank, container, false);

        Button btn2T2BForm=(Button)rootView.findViewById(R.id.btn2T2BForm);
        btn2T2BForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddAccountPayeeActivity.class);
                getActivity().startActivity(intent);
            }
        });


return rootView;
    }


}
